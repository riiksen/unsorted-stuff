require "selenium-webdriver"
require "phantomjs"

Selenium::WebDriver::PhantomJS.path = 'D:\\Programy\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe'

profile = Selenium::WebDriver::PhantomJS::Proffile.new
profile['']

driver = Selenium::WebDriver.for :phantomjs

driver.get "https://csgowitch.com"

driver.find_element(id: "btn-login").submit

username = gets.chomp
password = gets.chomp

driver.find_element(id: "steamAccountName").send_keys username
driver.find_element(id: "steamPassword").send_keys password

driver.find_element(id: "login_btn_signin").submit

two_factor = gets.chomp

driver.find_element(id: "twofactorcode_entry").send_keys two_factor

puts driver.page_source

driver.find_element(id: "twofactorcode_entry").send_keys :enter

password = nil

file = File.new("out.html", "w+")
file.puts driver.page_source
