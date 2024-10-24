#!/usr/bin/ruby
# frozen_string_literal: true

if ARGV.length != 1
  puts 'Useage: ./if.rb <number>'
  exit(1)
end

n = ARGV[0].to_i

# This is the same as
# if n % 2 == 0
if n.even?
  print n, ' is even', "\n"
else
  print n, ' is odd', "\n"
end

# one line if
a = 0
puts a if a.zero?

# one line it not (unless)
b = -1
puts b unless b.positive?
