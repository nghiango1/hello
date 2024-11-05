#!/usr/bin/ruby
# frozen_string_literal: true

# We can use hash as param, where we can use alternative function call with the string name directly
def hash_param(params)
  print "Hello from hash param\n"

  # Rails support basic validator for hash fields
  # params.require(:article).permit(:title, :body)

  puts params[:article][:title]
  puts params[:article][:body]
end

# prefix with * and we can acess the params as an array
def param_array(params, *params2)
  print "Hello from param array\n"

  print "params[:article][:title] = #{params[:article][:title]}"
  print ", params[:article][:body] = #{params[:article][:body]}"
  print ", params2 = {length = #{params2.length}, values = #{params2}}\n"
end

# Normal call
params = { article: { title: 'hello', body: 'greeting from body' } }
hash_param(params)

# Shorten call
hash_param({ article: { title: 'hello 2', body: 'greeting from body 2' } })
hash_param(article: { title: 'hello 2', body: 'greeting from body 2' })
hash_param article: { title: 'hello 3', body: 'greeting from body 3' }

# Can't really do that incase of multiple params
param_array({ article: { title: 'hello 3', body: 'greeting from body 3' } }, { hello: 2 }, { hello: 3 })
