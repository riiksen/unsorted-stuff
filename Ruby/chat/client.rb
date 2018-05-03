require "socket"

class Client
  def initialize(ip, port)
    @server = TCPSocket.open(ip, port)
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
    @request = Thread.new do
      loop do
        msg = $stdin.gets.chomp
        @server.puts(msg)
      end
    end
  end
end

Client.new("localhost", 2000)
