#!/usr/bin/env ruby
require "socket" #For TCP Communication
require "digest" #For Hashing
require "time" #For Time

#check if all libraries are installed if not install them
begin
  require "colorize" #For colorized output
  require "selenium-webdriver" #For Selenium
rescue LoadError
  puts "Error on initializing libraries"
  puts "Installing dependences"
  system("gem install colorize selenium-webdriver")
  Process.kill 9, Process.pid
end

#TODO: Użyj selenium i phantomJS

$DEBUG = false

if ARGV[0] == "--debug"
  $DEBUG = true
end

if $DEBUG == false and File.exist?("/usr/bin")
  Process.daemon #only for linux
end

class Server
  def initialize(port)
    @server = TCPServer.open(port)
    $bot_status_csgowitch = "offline".red
    $bot_status_csgoatse = "offline".red
    $time_status
    if $DEBUG == true
      puts "initialize".blue
    end
    run
  end

  def cmd_help(client, cmd)
    client.puts "Dostępne komendy"
    client.puts "!config"
    client.puts "!start"
    client.puts "!stop"
    client.puts "!status"
    client.puts "!help"
  end

  def cmd_config(client, cmd)
    cmd = client.gets.chomp
    loop do
      if cmd == "!help"
        client.puts "Dostępne komendy"
        client.puts "!set"
        client.puts ""
        client.puts ""
        client.puts ""
        client.puts ""

    end
  end

  def cmd_start(client, cmd)
    case cmd[1]
    when "csgowitch"
      Thread.start do
        $bot_status_csgowitch = "online".green
        $botthread_csgowitch = Thread.current
        csgowitch_driver = Selenium::WebDriver.for:phantomjs
        csgowitch_driver.get "https://csgowitch.com"

      end
    when "csgoatse"
    else
      client.puts "You must specify a site".red
      client.puts "Available sites:"
      client.puts "csgowitch"
      client.puts "csgoatse"
    end
  end

  def cmd_stop(client, cmd)
    if $bot_status == "online".green
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
        puts "run".blue
      end
      Thread.start(@server.accept) do |client|
        if $DEBUG == true
          puts "accepted connection".blue
        end
        if $first_run === true
          client.puts "Przeprowadź wstępną konfiguracje bota"
          client.puts "Wprowadź nazwę użytkownika którą będziesz używać do logowania"
          username = client.gets.chomp
          client.puts "Teraz wprowadź hasło dla tego użytkownika"
          pass = Digest::MD5.hexdigest client.gets.chomp
          config = File.new("bot.conf", "w+")
          config.puts("username: #{username}")
          config.puts("password: #{pass}")
          config.close
          listen_user_messages(client)
        else
          client.puts "Zaloguj się"
          client.puts "Podaj nazwę użytkownika"
          username = client.gets.chomp
          client.puts "Podaj hasło"
          pass = Digest::MD5.hexdigest client.gets.chomp
          if File.read("bot.conf").include? "username: #{username}" and File.read("bot.conf").include? "password: #{pass}"
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
      cmd = client.gets.chomp
      case cmd
      when /!help/
        cmd = cmd.split
        cmd_help(client, cmd)
      when /!config/
        cmd = cmd.split
        cmd_config(client, cmd)
      when /!start"/
        cmd = cmd.split
        cmd_start(client, cmd)
      when /!stop/
        cmd = cmd.split
        cmd_stop(client, cmd)
      when /!status/
        cmd = cmd.split
        cmd_status(client, cmd)
      else
        client.puts "Podana komenda nie istnieje użyj komendy !help by zobaczyć dostępne komendy".red
      end
    end
  end
end

if File.exist?("bot.conf") or File.zero?("bot.conf")
  $first_run = false
else
  $first_run = true
end

if $DEBUG == true
  puts "first_run: #{$first_run}".blue
end

Server.new(6882)
