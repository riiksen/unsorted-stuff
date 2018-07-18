#!/usr/bin/ruby
require "net/http"

username = "natas6"
password = "aGoY4q2Dc6MgDq4oL4YtoKtyAg9PeHa1"

url = URI.parse "http://%s.natas.labs.overthewire.org/includes/secret.inc" % [username]
client = Net::HTTP.new url.host, url.port

req = Net::HTTP::Get.new url
req.basic_auth username, password

print (client.request req).body