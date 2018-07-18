#!/usr/bin/ruby
require "net/http"
# require "base64"

username = "natas2"
password = "ZluruAthQk7Q2MqmDeTiUij2ZvWy2mBi"

url = URI.parse "http://%s.natas.labs.overthewire.org/files/users.txt" % [username]
client = Net::HTTP.new url.host, url.port

req = Net::HTTP::Get.new url
req.basic_auth username, password

print (client.request req).body