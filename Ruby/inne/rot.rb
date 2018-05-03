#!/usr/bin/ruby

def usage
  puts "-- Caesar Cipher Bruteforce --"
  puts "ruby #{$0} <string>"
end

cipher_list = (97..122).map(&:chr)

if !ARGV[0]
  usage
else
  chars = ARGV[0].split('').map(&:downcase)
  for i in (1..26)
    chars.map! { |c|
      if cipher_list.index(c) == 25
        cipher_list[0]
      elsif cipher_list.index(c)
        c.next
      else
        c
      end
    }
    puts "ROT #{i}: #{chars.join}   |   #{chars.join.upcase}"
  end
end
