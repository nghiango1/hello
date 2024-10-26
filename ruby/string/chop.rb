#!/usr/bin/ruby
# frozen_string_literal: true

words = %w[hello how are you]
print 'A random secret word is chose from: '
print words.join(', '), "\n"

random = words[rand(4)]
# puts random

while (guess = $stdin.gets)
  # remove last new line
  guess.chop!
  if guess == random
    puts "#{guess} is correct!"
    break
  end
  puts "#{guess} is not the right secret word"
end
