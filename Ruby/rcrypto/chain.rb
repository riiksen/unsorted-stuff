require "./block.rb"

class Chain
	attr_reader :blocks

	def initialize()
		@blocks = [Block::GENESIS]
	end

	def add_block(block)
		blocks << block if block.valid_after?(blocks.last)

		unless valid_chain?
			@blocks = @blocks[0..-2]
			return false
		end
	end

	def valid_chain?(blocks = @blocks)
		is_valid = true
		blocks.each.with_index(0) do |block, idx|
			is_valid = is_valid && (idx.zero? ?
				)
		end
	end
end