# TODO: piramida w drógą strone


unless ARGV.size != 4 or [0, 1, 2].include? ARGV[2].to_i or [0, 1].include? ARGV[3].to_i
  print "usage [File]<STRING> [Length]<INT> [Type of piramide](1 only left, 2 only right, 0 both ways)<INT> [Type of piramide](0 up, 1 down)<INT>"
  exit
end

if ARGV[1].to_i % 2 != 0
  print "Lenght has to be even"
  exit
elsif ARGV[1].to_i == 0
  print "Lenght can't be 0"
  exit
elsif ARGV[1].to_i < 0
  print "Lenght can't be negative"
end

if File.file?(ARGV[0])
  file = File.open(ARGV[0], "w")
else
  file = File.new(ARGV[0], "w")
end

len = (ARGV[1].to_i) -2
len2 = len
type = ARGV[2].to_i
type2 = ARGV[3].to_i

if type2 == 0
  len3 = len2 + 2
  until len2 == 0
    len = len2
    unless len2 - 2 == len3
      file.print(" " * (len3 - len2))
    end
    if type2 == 0
      file.print("\\")
    else
      file.print("/")
    end
    until len == 0
      if type == 0
        file.print("\\/")
      elsif type == 1
        file.print(" \\")
      else
        file.print("/ ")
      end
      len -= 1
    end
    file.puts("/")
    len2 -= 1
  end
  file.print(" " * (len3 - len2))
  file.puts("\\/")
else
  len3 = 0
  file.print(" " * (len2 / 2))
  file.puts("/\\")
  until len2 == 0
    len = len2
    file.print(" " * (len2 / 2))
    if type2 == 0
      file.print("/")
    else
      file.print("\\")
    end
    until len3 == len
      if type == 0
        file.print("/\\")
      elsif type == 1
        file.print(" /")
      else
        file.print("\\ ")
      end
      len3 += 1
    end
    file.puts("\\")
    len2 -= 1
  end
end

file.close
