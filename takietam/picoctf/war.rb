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
      sleep 0.5
      @server.puts "\x03" * 100
      loop do
        @server.puts gets.chomp
      end
    end
  end
end

Client.new("shell2017.picoctf.com", 4017)
