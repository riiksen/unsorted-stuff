#!/usr/bin/env ruby
require "net/http"
require "net/https"
require "uri"
require "json"
require "socket"
require "digest"
require "matrix"

load "config.rb"

begin
  require "colorize"
rescue LoadError
  puts "Error on initializing libraries"
  puts "Install dependencies before starting program"
  puts "Dependencies needed to run a program:"
  puts "colorize"
  Process.kill 9, Process.pid
end

$DEBUG = false

if ARGV.include? "--debug"
  $DEBUG = true
end

if $DEBUG == false and File.exist?("/usr/bin")
  Process.daemon #only for linux
end

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

  def index_votes() #TODO: Make some nice line indexing
    Thread.start do
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
      #TODO: Make some nice formatted output to file
      while i < votes.length
        if votes[i].include? "id 305" or include? "id 313"
          votes[i] = votes[i] + "<-------------------------------"
        end
        i += 1
      end
      findex = File.open("index.txt", 'w')

      findex.puts votes
      findex.close
    end
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
      return "Głos dodany >> Obecna ilość głosów #{res.body[834..836]}"
    else
      return "Wystąpił błąd podczas dodawania głosu"
    end
  end

  def log(text, file)
    File.open("#{file}", 'a') { |f| f.puts "#{Time.now}: #{text}"}
  end

  def save_config
    config = File.open("config.rb", 'w')
    config.puts "$login_username = \"#{$login_username}\""
    config.puts "$login_password = \"#{$login_password}\""
    config.puts "$votes_config = #{$votes_config}"
    config.close
  end
end


class Server
  def initialize(port)
    @server = TCPServer.open(port)
    $utils = Utils.new
    $time_status
    $set_id = 305
    $set_clicks = 185
    if $DEBUG == true
      puts "initialize"
    end
    run
  end

  def cmd_help(client, cmd)
    client.puts "Dostępne komendy:"
    client.puts "set"
    client.puts "start"
    client.puts "stop"
    client.puts "status"
    client.puts "help"
    client.puts "vote"
  end

  def cmd_set(client, cmd)
    case cmd[1]
    when "help"
      client.puts "Dostępne komendy"
      client.puts "interval"
      client.puts "clicks"
      client.puts "id"
      client.puts ""
      client.puts ""
    when "id"
      $set_id = cmd[2]
    when "clicks"
      $set_clicks = cmd[2]
    end
  end

  def cmd_start(client, cmd)
    Thread.start do
      $bot_status = "working".green
      $botthread = Thread.current
      while true
        $utils.log("#{Time.now} #{$utils.send_vote($set_id, $utils.gen_hash())}", "votes.log")
        sleep(86400/$set_clicks)
      end
    end
  end

  def cmd_vote(client, cmd)
    $utils.log("#{Time.now} #{$utils.send_vote($set_id, $utils.gen_hash())}", "votes.log")
  end

  def cmd_stop(client, cmd)
    if $bot_status == "working".green
      Thread.kill($botthread)
      $bot_status = "offline".red
      client.puts "Bot pomyślnie został zatrzymany".green
    else
      client.puts "Bot musi być włączony by móc go zatrzymać".red
    end
  end

  def cmd_status(client)
    client.puts $bot_status + " " + $time_status.blue
  end

  def run
    loop do
      if $DEBUG == true
        puts "run"
      end
      Thread.start(@server.accept) do |client|
        if $DEBUG == true
          puts "accepted connection"
        end
        if $first_run === true
          client.puts "Przeprowadź wstępną konfiguracje bota"
          client.puts "Wprowadź nazwę użytkownika którą będziesz używać do logowania"
          $login_username = client.gets.chomp
          client.puts "Teraz wprowadź hasło dla tego użytkownika"
          $login_password = Digest::MD5.hexdigest client.gets.chomp
          $utils.save_config
          listen_user_messages(client)
        else
          client.puts "Zaloguj się"
          client.puts "Podaj nazwę użytkownika"
          tmp_1 = client.gets.chomp
          client.puts "Podaj hasło"
          tmp_2 = Digest::MD5.hexdigest client.gets.chomp
          if tmp_1 == $login_username and tmp_2 == $login_password
            client.puts "Pomyślnie zalogowano".green
            listen_user_messages(client)
          else
            client.puts "Podany użytkownik lub hasło jest nieprawidłowe".red
          end
        end
      end
    end
  end

  def listen_user_messages(client)
    loop do
      cmd = client.gets.chomp.split
      case cmd[0]
      when "help"
        cmd_help(client, cmd)
      when "set"
        cmd_set(client, cmd)
      when "start"
        cmd_start(client, cmd)
      when "stop"
        cmd_stop(client, cmd)
      when "status"
        cmd_status(client, cmd)
      when "vote"
        cmd_vote(client, cmd)
      else
        client.puts "Podana komenda nie istnieje użyj komendy help by zobaczyć dostępne komendy".red
      end
    end
  end
end

if File.exist?("config.rb") or File.zero?("config.rb")
  $first_run = false
else
  $first_run = true
end

if $DEBUG == true
  puts "first_run: #{$first_run}".blue
end

Server.new(6882)
