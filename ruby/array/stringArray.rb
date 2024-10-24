#!/bin/ruby
# frozen_string_literal: true

string = 'hello every one'
print string.split, "\n"
print string.split('e'), "\n"
print string.split('e').join, "\n"

arr = ['hello every', 'one']
print arr.join(' '), "\n"

# Get every single character as array we can use split
print string.split(''), "\n"
