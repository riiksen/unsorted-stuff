#!/usr/bin/ruby
require "net/http"

username = "natas3"
password = "sJIJNW6ucpu6HPZ1ZAchaDtwd7oGrD14"

url = URI.parse "http://%s.natas.labs.overthewire.org/s3cr3t/users.txt" % [username]
client = Net::HTTP.new url.host, url.port

req = Net::HTTP::Get.new url
req.basic_auth username, password

print (client.request req).body