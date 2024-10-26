#!/bin/ruby
# frozen_string_literal: true

arr = [1, 2, 3, 4, 10, 14, 20, 103, 123]

# binary search only work with
# - find first `true` in [false..false true .. true] array
# - find first `0` in [1..1 0 -1..-1] array

# find actuall value that match with the callback
find_result = arr.bsearch { |curr| curr >= 14 }
print find_result, ' '
puts

# Normal comparation give true/false
def callback(curr, target)
  puts "check #{curr}, which have #{target} >= #{curr} == #{target >= curr}"
  curr >= target
end

# only support 1 param
find_result = arr.bsearch { |curr| callback(curr, 14) }
print find_result
puts

# find using index
find_result = arr.bsearch_index { |curr| curr >= 14 }
print find_result
puts

def callback_2(curr, target)
  puts "check #{curr}"
  curr >= target
end

# only support 1 param
find_result = arr.bsearch_index { |curr| callback_2(curr, 14) }
print find_result, ' ', arr[find_result..]
puts

def callback_3(curr, target)
  puts "check #{curr}, which have #{target} <=> #{curr} == #{target <=> curr}"
  target <=> curr
end

# only support 1 param
find_result = arr.bsearch_index { |curr| callback_3(curr, 14) }
if find_result.nil?
  print "Can't find anything\n"
else
  print find_result, ' ', arr[find_result..]
end
puts
