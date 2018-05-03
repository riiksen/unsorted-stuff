#!/usr/bin/crystal

require "socket"

spawn do
  loop do
    print "World\n"
    sleep 1.second
  end
end

loop do
  print "Hello "
  sleep 1.second
end


class Server
  def initialize(port)
    @server = TCPServer.open(port)
    @connections = {} of Sybol
    @conections = Hash.new
    @rooms = Hash.new
    @clients = Hash.new
    @connections[:server] = @server
    run
  end

  def run
    loop do
      @server.accept do |conn|
        spawn handle_conn(conn)
      end
    end
  end

  def handle_conn(conn)
    conn.puts "Podaj swoją nazwę użytkownika"
    username = client.gets.chomp
    @connections[:clients].each do |other_name, other_client|
      if username == other_name
        conn.puts "Ta nazwa użytkownika jest używana"
        sleep
      elsif username.empty?
        conn.puts "Nazwa użytkownika nie może być pusta"
        sleep
      end

    end
  end
end

class Server
  def initialize(port)
    @server = TCPServer.open(port)
    @rooms = [] of String => 
    @clients = [] of Socket => String
  end
end

Server.new(2000)