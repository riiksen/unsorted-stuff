raw = File.read("out.txt")
#puts raw
data = ""
raw.chars.each do |v0|

	if v0 != ':'

	if v0 != ':' && v0 != '0' && v0 != "\n"
		data += v0
	end
end
puts data