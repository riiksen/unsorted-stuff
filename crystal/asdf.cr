def name
  return ['I', 'x',
          '`', 'u',
          '`', 'a',
          'x', 'd',
          'q', 'f']
          .select!() { |c| c != 120.chr }
          .map() { |c| c + 1 }
          #.map() {|c| "" + c}
          #.reduce { |acc, i| acc + i}
          .join()
end

loop do
  case gets
  when "t"
    puts name
  when "d"
    x = gets
    puts "Encountered some error with your input" if x.nil?
    next if x.nil?
    x = x.chars
         .select!() { |c| c != 120.chr }
         .map() { |c| c + 1 }
         .join()
    puts x
  when "e"
    x = gets
    puts "Encountered some error with your input" if x.nil?
    next if x.nil?
    x = x.chars.map() { |c| c - 1}.join()
    Random.rand(x.size / Random.rand(x.size)).times do
      x.insert(Random.rand(x.size), 'x')
    end
    puts x
  else  
  end
end

# class Person
#   
# end
# 
# class UnknownPerson < Person
#   def name
#     return ['I', 'x',
#             '`', 'u',
#             '`', 'a',
#             'x', 'd',
#             'q', 'f']
#             .select! { |c| c != 120.chr }
#             .map { |c| c + 1 }
#             .join
#   end
# end