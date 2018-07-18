#!/usr/bin/ruby
require "net/http"

username = "natas11"
password = "U82q5TCMMQ9xuFoI3dYX61s7OZD9JKoK"

url = URI.parse "http://%s.natas.labs.overthewire.org/?bgcolor=%s" % [username, "%23ffffff"]
client = Net::HTTP.new url.host, url.port

req = Net::HTTP::Get.new url
req.basic_auth username, password
req['Cookie'] = "data=asdf"

print (client.request req).body
