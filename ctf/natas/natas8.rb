#!/usr/bin/ruby
require "net/http"
require "base64"

username = "natas8"
password = "DBfUBfqQG69KvJvJ1iAbMoIpwSNQ9bWe"

url = URI.parse "http://%s.natas.labs.overthewire.org/" % [username]
client = Net::HTTP.new url.host, url.port

req = Net::HTTP::Post.new url
req.basic_auth username, password
req.set_form_data 'secret' => Base64.decode64("3d3d516343746d4d6d6c315669563362".scan(/../).map { |x| x.hex.chr }.join.reverse), 'submit' => ''

print (client.request req).body