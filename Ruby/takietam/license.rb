require "colorize"

puts "Enter the license".blue
x = gets.chomp

if x == "1234-asd-qwerty"
  puts "Correct license".green
else
  puts "NOPE".red
end
