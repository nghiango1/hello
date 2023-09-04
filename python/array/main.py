from array import array


a = array('l')
print(a)
a = array('u', 'hello \u2641')
print(a)
a = array('l', [1, 2, 3, 4, 5])
print(a)
a = array('d', [1.0, 2.0, 3.14])
a.append(1.3)
print(a)
