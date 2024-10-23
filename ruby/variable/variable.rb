# $ 	global variable
$global = 123

# @ 	instance variable
@instance = 'hello'

# [a-z] or _ 	local variable
local = 2

# [A-Z] 	constant
CONST = 'ruby'

puts local + $global
puts String($global) + ' ' + @instance
