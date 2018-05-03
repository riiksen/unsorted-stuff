=begin
#<Block:0x00000003271c00
  @data="Start",
  @hash="0020073530c61f07843b1ba9d27ad69efff761cca728573d2d32bd3cd45070c3",
  @index=0,
  @nonce=308,
  @previous_hash="0",
  @timestamp=2018-01-18 11:25:29 +0100>,
#<Block:0x00000002f512f0
  @data="Data...",
  @hash="0073c42d12c42046e3de25db695848ce636a5ec4beab9ba6aeb1a87b20b144b6",
  @index=1,
  @nonce=92,
  @previous_hash="0020073530c61f07843b1ba9d27ad69efff761cca728573d2d32bd3cd45070c3",
	@timestamp=2018-01-18 11:25:29 +0100>,
#<Block:0x00000002f729c8
  @data="Data......",
  @hash="00488d7b82bbbda676368cff135686262f38c486facff0da9361a3ace6dbadeb",
  @index=2,
  @nonce=222,
  @previous_hash="0073c42d12c42046e3de25db695848ce636a5ec4beab9ba6aeb1a87b20b144b6",
  @timestamp=2018-01-18 11:25:29 +0100>,
#<Block:0x000000032943b8
  @data="More Data...",
  @hash="00a9aedeb50cbc621197e20c6f9f3afdac35bb3c129d96c5ec66625d1bc4e003",
  @index=3,
  @nonce=2,
  @previous_hash="00488d7b82bbbda676368cff135686262f38c486facff0da9361a3ace6dbadeb",
  @timestamp=2018-01-18 11:25:29 +0100>,
#<Block:0x00000003283b08
  @data="Hello World",
  @hash="002f9732bf3ebff3a21a242e698f1a1c504ab5466c80e8512c10e6a2572aa8ab",
  @index=4,
  @nonce=416,
  @previous_hash="00a9aedeb50cbc621197e20c6f9f3afdac35bb3c129d96c5ec66625d1bc4e003",
  @timestamp=2018-01-18 11:25:29 +0100>
=end

require "openssl"
require "pp"

class Block
	attr_reader :index
  attr_reader :timestamp
  attr_reader :previousHash
  attr_reader :hash

	def initialize(index, data, previousHash)
		@index = index
		@timestamp = Time.now
		@data = data
		@previousHash = previousHash
		@nonce, @hash = compute_hash
	end

	def compute_hash(difficulty = "00")
		nonce = 0
		loop do
			hash = calc_hash(nonce)
			if hash.start_with?(difficulty)
				return [nonce, hash]
			else
				nonce += 1
			end
		end
	end

	def calc_hash(nonce = 0)
		sha = OpenSSL::Digest::SHA256.new
		sha.update(nonce.to_s + @index.to_s + @timestamp.to_s + @data + @previousHash)
		sha.hexdigest
	end

	def self.first(data = "Start") # Create First Block
		Block.new(0, data, "0")
	end

	def self.next(previous, data = "Transaction Data...")
		Block.new(previous.index+1, data, previous.hash)
	end

	GENESIS = Block.first( "Genesis" )
end

b0 = Block.first( "Start" )
b1 = Block.next( b0, "Data..." )
b2 = Block.next( b1, "Data......" )
b3 = Block.next( b2, "More Data..." )
b4 = Block.next( b3, "Hello World" )

blockchain = [b0, b1, b2, b3, b4]




pp blockchain
=begin
class Chain
	
end
=end