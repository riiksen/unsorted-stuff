require "socket"
require "json"

#THIS IS NOT VALID RUBY SCRIPT
#THIS IS A EXAMPLE

class Server
  def initialize(port)
    @server = TCPServer.open(port)
    @db = #Handle Db connection
    run
  end

  def run
    loop do
      Thread.start(@server.accept) do |client|
        data = JSON.parse(client.gets.chomp)
        if data['cmd'] == "get_coins"
          if Time.now - 3600 >= @db.select("user/#{data['user']}/get_coins_last_time") #Select Last get coins time
            if data['captcha'] == @db.select("user/#{data['user']}/") #Select a valid captcha key
              @db.update("user/#{data['user']}/coins", @db.select("user/#{data['user']/coins}") + 100) #Update an entry in database
            end
          end
        end
        client.close
      end
    end
    .join
  end
end

Server.new(4654)
