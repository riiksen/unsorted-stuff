require "socket"

class Server
  def initialize(port)
    @server = TCPServer.open(port)
    @connections = Hash.new
    @rooms = Hash.new
    @clients = Hash.new
    @connections[:server] = @server
    @connections[:rooms] = @rooms
    @connections[:clients] = @clients
    run
  end

  def run
    loop do
      Thread.start(@server.accept) do |client|
        client.puts "Podaj swoją nazwę użytkownika"
        nick_name = client.gets.chomp.to_sym
        @connections[:clients].each do |other_name, other_client|
          if nick_name == other_name
            client.puts "Ta nazwa użytkowanika jest używana"
            Thread.kill self
          end
        elsif nick_name.empty?
          client.puts "Nazwa Użytkownika jest używana"
          Thread.kill self
        end
        puts "#{nick_name} #{client.addr}"
        @connections[:clients][nick_name] = client
        client.puts "Połączono z czatem"
        listen_user_messages(nick_name, client)
      end
    end
    .join
  end

  def listen_user_messages(username, client)
    loop do
      msg = client.gets.chomp
      @connections[:clients].each do |other_name, other_client|
        unless other_name == username
          other_client.puts "#{username.to_s}>>#{msg}"
        end
      end
    end
  end
end

Server.new(2000)
