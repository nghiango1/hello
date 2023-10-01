This cover ref: https://go.dev/tour/concurrency/8 

# White board

The ideal is using gorountine for Tree comparing

We are porvided with a Tree implement from source that:

The function `tree.New(k)` constructs a randomly-structured (but always sorted) binary tree holding the values k, 2k, 3k, ..., 10k.

```go
tree.New(k) = tree{ length = 10, tree_sequence = [k, 2k, 3k, ..., 10k]}
```

We try to use seperated gorountine for `Walk`, a tree travesal inorder (Lelf -> Node -> Right) function. Which have 10 elements (as tree length == 10) being push into a channel
The comparing process then comparing both channels for the different, if 10 elements is the same, thus both tree have the same sequence

# The final code

