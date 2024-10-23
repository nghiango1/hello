#!/usr/bin/ruby

# This calculate the factorial of a number from user agrument, eg: 5!

def fact(num)
  if num == 0
    1
  else
    num * fact(num - 1)
  end
end

print fact(ARGV[0].to_i), "\n"
