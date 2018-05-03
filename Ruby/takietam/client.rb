require "socket"

class Client
  def initialize(server)
    @server = server
    @request = nil
    @response = nil
    listen
    send
    @request.join
    @response.join
  end

  def listen
    @response = Thread.new do
      loop do
        msg = @server.gets.chomp
        puts "#{msg}"
      end
    end
  end

  def send
    puts "Wpisz swoją nazwe użytkownika"
    @request = Thread.new do
      loop do
        msg = $stdin.gets.chomp
        @server.puts(msg)
      end
    end
  end
end

server = TCPSocket.open("localhost", 2000)
Client.new(server)

=begin

require "socket"

hostname = 'localhost'
port = 2000

s = TCPSocket.open(hostname, port)
print "[i]Enter your username:"
s.puts gets.chomp
puts "[i]Connected to the chat, enter !help to list commands"

loop do
  x = gets.chomp
  case x
  when "!help"
    puts "[i]!help: Lists available commands"
    puts "[i]!quit: Exit the app"
    puts "[i]!users: Lists online users"
  when "!quit"
    break
  when "!users"
  else
    unless x.empty?
      s.puts x
    else
      puts "[w]You can't send empty message"
    end
  end
end

s.close

=end
