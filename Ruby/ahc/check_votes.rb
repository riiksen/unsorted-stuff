#!/usr/bin/env ruby
require "net/http"
require "net/https"
require "uri"
require "colorize"

headers = {
  'Content-Type' => 'application/x-www-form-urlencoded',
  'X-Requested-With' => 'XMLHttpRequest'
}

votes = []
id = 0
i = 0
uri = URI.parse("https://www.herosiprzedsiebiorczosci.pl/main/index/add-vote/")
req = Net::HTTP.new(uri.host, uri.port)
req.use_ssl = true

#Download the values

while id < 15
  res = req.post(uri.path, "id=#{id}", headers)
  if !res.body.include? '"votes":null'
    if res.body.include? '"votes":"'
      votes[i] = "id #{id}: #{res.body[50..res.body.length-3]}"
    else
      votes[i] = "id #{id}: #{res.body[49..res.body.length-2]}"
    end
    i += 1
  end
  id += 1
end

#Sort the values

i = 0
n = 0

while i < votes.length
  while n < votes.length-1
    if votes[n][votes[n].index(':')+2..votes[n].length-1].to_i > votes[n+1][votes[n+1].index(':')+2..votes[n+1].length-1].to_i
      votes[n], votes[n+1] = votes[n+1], votes[n]
    end
    n += 1
  end
  i += 1
end

#Mark id 305

i = 0

while i < votes.length
  if votes[i].include? "id 305" or include? "id 313"
    votes[i] = votes[i].green
  end
  i += 1
end

puts votes
