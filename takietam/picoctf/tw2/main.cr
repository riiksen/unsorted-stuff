require "http/client"
require "file_utils"

FileUtils.cd("src")

def download(path : String, fullpath : String, dir : String = ".")
#  return if path.nil?
  if !Dir.exists?("#{dir}")
    FileUtils.mkdir_p(dir)
  end
  FileUtils.cd dir do
    res = HTTP::Client.get("http://shell2017.picoctf.com:28933/#{fullpath}")
    FileUtils.touch(path)
    File.write(path, res.body)
  end
end


#loop do
#  x = gets
#  download(x)
#end

while true
  x = gets
  if x.nil?
    break
  end
  if x.includes?('/')
    y = x.rpartition('/')
    download(y[2], x, y[0])
  else
    download(x, x)
  end
end