#!/usr/bin/ruby
require "net/http"

username = "natas1"
password = "gtVrDuiDfck831PqWsLEZy5gyDz1clto"

url = URI.parse "http://%s.natas.labs.overthewire.org/" % [username]
client = Net::HTTP.new url.host, url.port

req = Net::HTTP::Get.new url
req.basic_auth username, password

print (client.request req).body