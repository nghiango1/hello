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

# ruby like to call dict ~ hash (https://ruby-doc.org/docs/ruby-doc-bundle/UsersGuide/rg/arrays.html)
dict = {}
dict[1] = 2
dict['a'] = 'b'
print dict, "\n"

# Put string format to good use?
print "dict[1] = #{dict[1]}\n"
print "dict['a'] = #{dict['a']}\n"

# Predefine dict value
dict = { 2 => 3, 'b' => 'c' }
print "Original dict #{dict}\n"

# Remove an variable
dict.delete(2)
print "After delete `2` key, we have remain dict #{dict}\n"
