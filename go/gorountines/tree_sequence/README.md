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

`main.go` contain the final code. Here is note on how to debug it 

## Channels debug

> If you not use any debugger yet, maybe it time to look at `kickstart.nvim` where golang DAP plugin already inplace (you still need to enable it)

The `channels` is being used like a buffer to store tree traversal result, debuging break can be use on `Walk` function to access to channels runtime

Looking into channels variable on runtime `ch` is quite difficult, but:
- `c1.sendq.first` should contain the current value of the channels
- `c2.sendq.last` should contain the end of the channels

Code will jump to panic (?) in a weird file, I assumming that it know when to end the channel process when the program end.

## Loging

> Some will not use DAP and rely on `print` for debug, which seem like a better way to debug channel as what DAP provided isn't as clear at all.

```sh
go build .
./treeseq
```

I add some log along, channel working as a seperated process, which cause the log to be all over the place

Look at `test-2` where all value of the Tree is the same, the Walk log goes full the way
```stdout
channel: test-2-cTwo, value: 8
channel: test-2-cTwo, value: 1
channel: test-2-c1, value: 7
channel: test-2-c1, value: 3
channel: test-2-c1, value: 2
channel: test-2-c1, value: 1
channel: test-2-cTwo, value: 3
channel: test-2-cTwo, value: 2
channel: test-2-cTwo, value: 5
channel: test-2-cTwo, value: 4
channel: test-2-c1, value: 6
channel: test-2-c1, value: 5
channel: test-2-c1, value: 4
channel: test-2-cTwo, value: 6
channel: test-2-cTwo, value: 7
channel: test-2-c1, value: 10
channel: test-2-c1, value: 9
channel: test-2-c1, value: 8
channel: test-2-cTwo, value: 9
channel: test-2-cTwo, value: 10
true
```

While `test-3`, which have two difference tree, have way little log lines. Thus, the channel isn't finish `Walk` all the available tree node
```stdout
channel: test-3-cTwo, value: 18
channel: test-3-cTwo, value: 14
channel: test-3-cTwo, value: 8
channel: test-3-cTwo, value: 6
channel: test-3-cTwo, value: 4
channel: test-3-cTwo, value: 2
channel: test-3-c1, value: 7
channel: test-3-c1, value: 3
channel: test-3-c1, value: 2
channel: test-3-c1, value: 1
false
```

This could mean that, channel process will wait for some time until their infomation needed, and keep going where they leff off. If the program end before hand, all unfinished process (or channel) will be handled/ended by `golang`.

> This also confirm the panic handling code being run and catch by Debugger
