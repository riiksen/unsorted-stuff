require "socket" #For TCP Communication
require "digest" #For Hashing
require "time" #For Time
require "colorize" #For colorized output
#require "daemons" #for daemon

#Daemons.daemonize
#Process.daemon 

if File.expand_path(__FILE__).include? ".rbw"
  $DEBUG = FALSE
else
  $DEBUG = TRUE
end

class Server
  def initialize(port)
    @server = TCPServer.open(port)
    $bot_status = "offline".red
    $time_status
    if $DEBUG == TRUE
      puts "initialize".blue
    end
    run
  end

  def cmd_help(client)
    client.puts "Dostępne komendy"
    client.puts "!start"
    client.puts "!stop"
    client.puts "!status"
    client.puts "!help"
  end

  def cmd_status(client)
    client.puts $bot_status + " " + $time_status.blue
  end

  def run
    loop do
      if $DEBUG == TRUE
        puts "run".blue
      end
      Thread.start(@server.accept) do |client|
        if $DEBUG == TRUE
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
      msg = client.gets.chomp
      case msg
        when "!help"
          cmd_help(client)
        when "!config"
          cmd_config(client)
        when "!start"
          cmd_start(client)
        when "!stop"
          cmd_stop(client)
        when "!status"
          cmd_status(client)
        else
          client.puts "Podana komenda nie istnieje użyj komendy !help by zobaczyć dostępne komendy".red
      end
    end
  end
end

if File.exist?("conf") or File.zero?("conf")
  $first_run = false
else
  $first_run = true
end

if $DEBUG == TRUE
  puts "first_run: #{$first_run}".blue
end

Server.new(6882)
