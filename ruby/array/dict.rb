#!/bin/ruby
# frozen_string_literal: true

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

# non-recommended (before ruby 1.9 syntax)
hash = { :one => 1, :two => 2, :three => 3 }
print "Original hash <1.9 #{hash}\n"

# recommended
hash = { one: 1, two: 2, three: 3 }
print "Original hash >=1.9 #{hash}\n"

# Remove an variable
dict.delete(2)
print "After delete `2` key, we have remain dict #{dict}\n"
