#!/usr/bin/env ruby
require "net/http"
require "net/https"
require "uri"

$clicks = 300

Process.daemon(true, true) #only for linux

class Utils
  def gen_hash
    hash = ""
    i = 0
    while i < 32
      hash += "0123456789abcdef".chars.shuffle.first
      i += 1
    end
    return hash
  end

  def send_vote(id, hash)
    headers = {
      'User-Agent' => File.readlines("useragents.txt").sample,
      'Content-Type' => 'application/x-www-form-urlencoded',
      'X-Requested-With' => 'XMLHttpRequest'
    }

    data = "id=#{id}&hash=#{hash}"

    uri = URI.parse("https://www.herosiprzedsiebiorczosci.pl/main/index/add-vote/")
    https = Net::HTTP.new(uri.host,uri.port)
    https.use_ssl = true

    res = https.post(uri.path, data, headers)

    if res.body.include? 'Dzi\u0119kujemy za oddanie g\u0142osu'
      return "Id: #{id} >> Głos dodany >> Obecna ilość głosów #{res.body[res.body.index('"votes":')+8..res.body.length-2]}"
    else
      return "Id: #{id} >> Wystąpił błąd podczas dodawania głosu"
    end
  end

  def log(text, file)
    File.open("#{file}", 'a') { |f| f.puts "#{Time.now}: #{text}"}
  end
end

$utils = Utils.new

while true
  $utils.log("#{$utils.send_vote(305, $utils.gen_hash)}", "votes.log")
  $utils.log("#{$utils.send_vote(313, $utils.gen_hash)}", "votes.log")
  sleep(86400/$clicks)
end
