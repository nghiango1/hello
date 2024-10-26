#!/bin/ruby
# frozen_string_literal: true

string = $stdin.gets

puts "Total of argument #{string}"

# Assumming we want to get number (for a coding chalenge)
arr = []
string.split.each do |element|
  arr.push(element.to_i)
end
puts "Got: #{arr}"

# we also have another string to number. But this throwing error
begin
  puts 'Try convert `abc` to Integer'
  puts "Got: #{Integer('abc')}"
rescue ArgumentError
  puts 'Aw, it got ArgumentError'
rescue StandardError # StandardError is the default
  puts 'Aw, it error'
end
