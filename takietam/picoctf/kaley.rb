#!/usr/bin/env ruby
require "net/http"
require "colorize"
require "json"

def request(tags)
  headers = {
    'Content-Type' => 'application/json'
  }

  data = JSON.generate({:tags => tags})

  uri = URI.parse("http://shell2017.picoctf.com/search")
  req = Net::HTTP.new(uri.host, 8080)

  res = req.post(uri.path, data, headers)

  puts res.code.red

  puts JSON.parse(res.body)
end

request(gets.chomp)
