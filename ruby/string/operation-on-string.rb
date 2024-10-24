#!/usr/bin/ruby
# frozen_string_literal: true

# A easier life like Go/Python/...
double = 'repeat' * 2
puts double

greetting = 'hello'
add = greetting + 'world'
puts add

# Extracting character
first = 'hello'[0]
puts first

# Reverted index
last = 'hello'[-1]
puts last

# String compare
puts greetting == 'hello'

# Regex find operation (or string find) first apprerance
long_string = 'hello every one'
first_find = long_string =~ /one$/
puts long_string[first_find..] unless first_find.nil?
