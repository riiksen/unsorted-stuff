["colorize", "net/http", "net/https", "uri"].map { |lib| require lib }

schools = []

File.open("schools.txt", "r") do |file|
  while (line = file.gets)
    schools << line
  end

  file.close
end

uri = URI.parse("https://slaskie.edu.com.pl/kandydat/app/statistics.html")
client = Net::HTTP.new uri.host, uri.port
client.use_ssl = true

headers = {
  # 'Host' => "slaskie.edu.com.pl",
  # 'Content-Type' => 'application/x-www-form-urlencoded',
  'Cookie' => "Cookie: JSESSIONID=C959ECFA6EBBC7D5516AA76C875364A8.tomcat-hva2; ARR_naborpg_edu.com.pl=765cc3faccca1f335d037bb3bcd4b1427a667dfd022ee5f7c9a7315d03fa55f8; ARR_3S_ARR213214=56e3138534810c6d38a919507a61da455e63604502a293a0102897c8dd10f48f; ARR_naborpg_kan_edu.com.pl=765cc3faccca1f335d037bb3bcd4b1427a667dfd022ee5f7c9a7315d03fa55f8; systemId=slaskie"
}

schools.size.times do |idx|
  data = "j_idt46=j_idt46&j_idt46%3AcitySelect=&j_idt46%3AschoolSelect=#{schools[idx]}&j_idt46%3Aj_idt58=Szukaj&javax.faces.ViewState=-672571632471860388%3A9028494755122392862"
  # headers['Content-Length'] = data.size.to_s

  res = client.post(uri.to_s, data, headers)
  require "debug"
  puts "#{schools[idx]}: #{res.code} -> #{res.body}"
end

gets