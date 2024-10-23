#!/usr/bin/ruby
# frozen_string_literal: true

# A string can be define with single qoute or double qoute
name = 'world'

# But double qoute allow character escapes
endline = "\n"

# and Embeded expression support into double quote string
print "Hello #{name}!", endline

# While single quote backslash is treat as literal
puts 'Trying extended \t\n\r'
