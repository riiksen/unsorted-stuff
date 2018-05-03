require "granite_orm/adapter/sqlite"

class Pet < Granite::ORM::Base
  adapter sqlite
  table_name pets


  # id : Int64 primary key is created for you
  field name : String
  field breed : String
  field age : Int64
end
