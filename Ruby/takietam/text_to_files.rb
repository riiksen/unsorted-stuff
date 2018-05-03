require "socket"

server = TCPServer.new(7853)

client = server.accept
file = File.new("colors.h", "w+")

loop do
  file.puts client.gets.chomp
end
