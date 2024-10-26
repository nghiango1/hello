#!/bin/ruby
# frozen_string_literal: true

arr = [1, 2, 3, 4, 5, 6]
print arr, "\n"
# Array start at 0
print arr[1..3], "\n"

# Also have revert index
print arr[-1], "\n"

# But not have overflow index
print arr[7], "\n" unless arr[7].nil?
print arr[-7], "\n" unless arr[-7].nil?

# Array slice
print arr[1..3], "\n"

# Some of array member?
print arr[2, 5], "\n"

# Array is not bind with only one type
arr = [1, '2', '3', 4, '5', 6]
print "#{arr}\n"
