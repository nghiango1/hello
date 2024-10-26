#!/bin/ruby
# frozen_string_literal: true

arr = [1, 2, 3, 4]

arr.each do |element|
  puts element
end

arr.each_with_index do |element, index|
  puts "#{index} #{element}"
end

for element in arr
  puts element
end
