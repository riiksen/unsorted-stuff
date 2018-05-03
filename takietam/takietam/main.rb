require "zlib"
require "openssl"

def lenb(data)
	v0 = "GET /%s HTTP 1.0\nCookies: phpsessid=supersecretstring123" % data
	v1 = Zlib::Deflate.deflate(v0)
	aes = OpenSSL::Cipher::AES.new(128, :ofb)

	aes.encrypt
	aes.key = "0123456789abcdef"
	iv = 12312

	puts encrypted = aes.update(v1) + aes.final
	puts encrypted.length / 16
end

lenb(ARGV)