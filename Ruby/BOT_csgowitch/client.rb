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
        puts @server.gets.chomp
      end
    end
  end

  def send
    @request = Thread.new do
      loop do
        @server.puts gets.chomp
      end
    end
  end
end

print "Podaj adres bota>>"
x = gets.chomp
Client.new(x, 6882)
