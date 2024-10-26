# Build in

`Array#sort` will be used a lot. Some behavior that a bit ruby specific
- It return another array when call
    ```
    b = arr.sort
    # `arr` will not change
    ```
- This support `!`
    ```
    arr.sort!
    # `arr` will be replace with `arr.sort` result
    ```

Here is some context from the [Document](https://ruby-doc.org/docs/ruby-doc-bundle/UsersGuide/rg/backtoexamples.html). We can look at `?` a lot through out all of ruby code in this repo used in `if` logic statement

> Conventionally attach '!' or '?' to the end of certain method names. The exclamation point (`!`, sometimes pronounced aloud as "bang!") indicates something potentially destructive, that is to say, something that can change the value of what it touches... (While) method names that end in a question mark (`?`, sometimes pronounced aloud as "huh?"); this indicates a "predicate" method, one that can return either `true` or `false``

`Array#bsearch` not really
