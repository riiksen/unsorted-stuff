#!/usr/bin/crystal

# Algorithm for cho1osing cheapest roads between cities

roads = [
   {road: 1, cities: [1, 2], cost: 10},
   {road: 2, cities: [2, 3], cost: 20},
   {road: 3, cities: [3, 4], cost: 5},
   {road: 4, cities: [4, 5], cost: 15},
   {road: 5, cities: [5, 6], cost: 20},
   {road: 6, cities: [6, 7], cost: 35},
   {road: 7, cities: [7, 8], cost: 5},
   {road: 8, cities: [8, 12], cost: 100},
   {road: 9, cities: [8, 6], cost: 44},
   {road: 10, cities: [6, 12], cost: 37},
   {road: 11, cities: [7, 9], cost: 22},
   {road: 12, cities: [9, 8], cost: 17},
   {road: 13, cities: [8, 10], cost: 24},
   {road: 14, cities: [9, 10], cost: 55},
   {road: 15, cities: [1, 9], cost: 16},
   {road: 16, cities: [10, 12], cost: 12},
   {road: 17, cities: [10, 11], cost: 18},
   {road: 18, cities: [11, 12], cost: 33},
   {road: 19, cities: [4, 12], cost: 22},
   {road: 20, cities: [4, 11], cost: 44},
   {road: 21, cities: [3, 11], cost: 7},
   {road: 22, cities: [2, 11], cost: 3},
   {road: 23, cities: [2, 10], cost: 9},
   {road: 24, cities: [1, 10], cost: 11},
   {road: 25, cities: [5, 12], cost: 31},
   {road: 26, cities: [5, 16], cost: 25},
   {road: 27, cities: [13, 14], cost: 248},
   {road: 28, cities: [7, 13], cost: 32},
   {road: 29, cities: [14, 15], cost: 32},
   {road: 30, cities: [15, 16], cost: 132}
]

puts typeof(roads)

puts roads.size

i = n = d = 0

# temp = [] of Hash(Int32, NamedTuple(cities: Array(Int32), cost: Int32))

temp = [] of typeof(roads[1])

while i < 16
  while n < roads.size
    if roads[n][:cities][0] == i || roads[n][:cities][1] == i
      temp << roads[n]
    end
    # while d > roads.size - 1

    # end
  end
end# 