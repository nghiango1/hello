#!/usr/bin/ruby
# frozen_string_literal: true

# This calculate the factorial of a number from user agrument, eg: 5!

def fact(num)
  # Same with
  # if num == 0
  if num.zero?
    1
  else
    num * fact(num - 1)
  end
end

print 'Got number: ', ARGV[0].to_i, "\n"
print fact(ARGV[0].to_i), "\n"
