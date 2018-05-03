def check(x)
  a = 0
  i = 0

  until i == x.size
    a += x[i]
    i += 1
  end
  return a
end

key = ""
i = 0
while true
  key += "qwwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM123456789-".chars.shuffle.first
  if i == 4 || i == 9
    key.insert(key.length, '-')
  end
  if key.length == 15
    s = check(key.bytes)
    if s == 1288
      puts "Found a valid license: #{key}"
      key = ""
      i = 0
    elsif s > 1288
      key = ""
      i = 0
    end
  end
  if key.length > 15
    key = ""
    i = 0
  end
  i += 1
end
