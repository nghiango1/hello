# function

This example of a function in ruby
```rb
def fact(num)
  if num.zero?
    1
  else
    num * fact(num - 1)
  end
end
```

We not need to said return, any expression will become return output of the function

Also this example show how to get the program argument into ruby script file. So I just create new [input](../input/) to capture this
```ruby
puts "Total of argument #{ARGF}"
puts "Argument value: #{ARGV}"
```
