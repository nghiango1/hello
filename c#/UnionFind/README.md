# UnionFind

> Example problem for testing [1970. Last Day Where You Can Still Cross
](https://leetcode.com/problems/last-day-where-you-can-still-cross/?envType=problem-list-v2&envId=union-find)

When implementing Path-finding for Stardew Valley, I found out that I can't really define when there is no path between two position. Here is the explaination:

- Real-time pathfinging: The game performing by game tick loop, which should run every thing in 1/60 second. Our program should be split between game tick and keeping it state through out the game time.
    - We working on a limit counter, when it reach, we hold back the program and wait until next tick call for update.
    - Other approach can use: Multi-thread. But this require a lot of state tranfered between thread that it may create even more difficulty to implement

- Let asumming the current game map have 1000x1000 tilte (`O(n^2)` limit).

- We perform a lot of path searching in the same map until the player wrap to a new location

- The map mostly static, players (we may need mutiplay support) can interact and changing passable terrant.
    - We could use monitoring, where every tile state change (cause by players) between passable/unpassable will be updateded to us
    - We use a simple trick that a ~k tiles around Player state will be manually update on every tick

We can ideallizing the game into these specific input
```js
H <game_map_height>
W <game_map_width>
H*W <passable_tile_state>
Q <total_find_path_query>
"c" (1,2) <change_tile_state_query>
"f" (1,2) (3,4) <find_path_query>
```

Now after all above, we focus more on update the game's tile map state. Specifically using Union find to acelerate the query to find if there is any connection path between two tile map or not.
