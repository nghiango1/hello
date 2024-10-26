# Array

or any data structure that contain more than one value. Here is my nessesary must have list:
- Array
    - Simple array
    - Multi dimension array
- Dictionary/Map
- Set
- Queue
- Stack
- Heap

Which we will want to know how to:
- add a new member
- remove a member
- get a member (by index? key?)
- set a member (by index? key?)
- Loop through all member
- Reduce function?

While learnning ruby I really do a lot of comparasion with python:
- The syntax is quite similar with array and string, even dict/hash. So it is quite a quick experience at the moment
- Hanlding String feel great, the index thing is a bit different for slice though
    ```sh
    ruby -e 'puts "abcdefgh"[2..3]' # cd
    ruby -e 'puts "abcdefgh"[2..]' # cdefgh
    ```
- Format string is great for starting
    ```ruby
    puts "{x : #{x}}"
    ```

## Built-in

The array sort (which similar behavior to python `.sort()` ) we can use is
```ruby
arr = [-1, 3, 2, -2]
arr.sort!
```

Which call a `bang!` method. Normally `arr.sort` will return another array, but `!` state that result will replace `arr` value.
