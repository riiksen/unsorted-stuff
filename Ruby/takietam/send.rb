require "socket"

server = TCPSocket.new("192.168.0.101", 7853)

loop do
  server.puts gets.chomp
end
