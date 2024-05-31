# AI

The corrected way to use the full potential power of computer but got lost with LLM

## Why? The meanning behind all this notes

This containning the snapshot of my current knowleged (of mathematic or programming way of thing) that i don't want to get lost and rootten within the capability of my brain (which will eventually change over the time) so I deciding to write it here as soon as possible

- Chapter 1: Relation of posibility
- Chapter 2: The serialization of event
- Chapter 3: Complete of knowlege via example
- Chapter 4: The predictation - future

## Chapter 1: Relation of posibility

### The underline ideal

In my current mind

> I said that: Every possible outcome but one one of which will happend

Drawing one card from a desk of 52 different cards, they share the same possibility which one could be draw. But in the end the 1/52 change will happend.

> I also said that: The absolute trust - One of which will happend already being the trust/defined because of variability

Which variable could possible effect all the fair chance, and prove that the drawed card will be the 7 shade. I devide it to two source

1. Achievable variable
    - The drawer specifically choose the card by a rule (alway chossing first card of the desk)
    - The dealler force it to the drawer (7 shade on the top of the desk at beginning)

2. Un-achiveable variable
    - Higher dimention affection that will destermine the absolute outcome (God plan - The controler theories or just our reality is just a simulator)
    - The absolute outcome of the multiple possible reality (Multiver theories - which I believe in - Where we split in all possible reality and there is NO capable tools can get us to access those splited alternative)

When using compute power, we taking all of achiveable variable and make a "inteligent farseer".

Which way different from those who belived they can access the un-achiveable variable and "guessing" on the full 100% of variable (both achievable variable and un-achiveable variable)

When I said about "inteligent farseer", it can be call as any different name. They are everywhere through out the era: Knowledged, Interlectual property, Book, Movie, Religon, ... and one of those latest "hot name" AI - artificial intelligence

> In my current mind, organic creatured (and everything around us) is it self the result of "inteligent farseer"... which affected it-self to move/change in a predictable nature, causing us - human to "belive" it is predictable which it is not

### Math - or programing term?

> Let say it in "Math" term:

#### Define our problem

The [s]tate of reality - achiveable "farseer" variable problem:
- A state `S[0]` will change to next state `S[1]` regardless
- We only know `S[0]` but want to know `S[1]` even before it happend
- We have a set record of every possible occurrent that achiveable where state `S` is changing

The [p]ossible outcome
- We can call it `P`, which is the set of possible outcome of `S[0]`
    - This could came from all the record we found about `S`
    - Also could came from all the theories where `S` might end up
- We also assumming that `P` large enough contain `S[1]`
    ```
    P = {p[0], p[1], p[2] .. p[m]}
    S[1] "should be" in P
    ```

The [t]ime:
- We call every occurrent this change is a time tick `T`.
- We (assumming that we) know the time `T`, which is that how long state `S[0]` will be change to new state, is either
    ```
    T = 1 = a [c]onstant = C
    ```
- Or it a new achiveable variable problem it self, where we already solved
    ```
    T = {TS[0], TS[1], ..}
    ```

The describable [r]eality:
- We call a snapshot of all achievable [v]ariable `{v[0], v[1] .. v[n]}` or a vector form `V = {v[0], v[1], v[2], ..., v[n]}` in which state `S` is recorded. They are the capabilty of every tools we can use to describing and achiving from reality
- We can also see that `S[0]` and `S[1]` is a part of reality, so:
    ```
    S[0] in V[0]
    S[1] in V[1]
    ```

- And time it self is also a part of reality so
    ```
    T = C in V
    ```
    or
    ```
    TS[0] in V[0]
    TS[1] in V[1]
    ```

- Because `P` also withing our capability, so P also a part of achievable [v]ariable
    ```
    P in V
    ```

- Because I can't proved that [R]eality can be describe only with achivable [v]ariable so it is close but never equal to each other `R ~ V`. So we could say that (let borrowing the `...` symbol to indicate the rest of a set, and `~` symbol to indicate that it close, and hopefully equal, to)
    ```
    R ~ V = {v[0], v[1] .. v[n]}
    R ~ V = {S, P, T, v[3], v[4], .. v[n]}
    R ~ {S, P, T, ...V}
    ```
    or in the specific record when `S[0]` and `S[1]` happend
    ```
    R[0] ~ {S[0], P[0], TS[0], ...V[0]}
    R[1] ~ {S[1], P[1], TS[1], ...V[1]}
    ```
    
> What about the part, yes, we can also make it a part of archivable variable, read more on the Extra to know how to handle it
> ```
> R[0] ~ {S[0], P[0], TS[0], R[<0], ...V[0]}
> R[1] ~ {S[0], P[0], TS[0], R[<1], ...V[0]}
> ```


The "inteligent [f]arseer"
- We can call it `F`, where using F we can turn S[0] a result which really close to (and hopefully is equal to) S[1]
    ```
    F(S[0]) = Calculated_Result ~ S[1]
    ```
    I using `~` as meant close

- We take into account that we know so much on Reality, we push everything into our equaltion
    ```
    F(R[0]) ~ F(S[0], ...V[0]) = Calculated_result ~ {S[1]}
    ```
    or even better
    ```
    F(R[1]) ~ F(S[0], ...V[0]) = Calculated_result ~ {S[1], ...V[1]} = R[1]
    ```

The [a]bsolute trust
- We can call it `A`, 
- Which from a state can desterminding the true outcome, or just:
    ```
    A(S[0]) = S[1]
    ```
    or even better
    ```
    A(R[0]) = {S[1], ...V[1]} = R[1]
    ```

We can get a lot of this infomation, just by observing our reality.
```
R[0] = {S[0], P[0], TS[0], ...V[0]}
R[1] = {S[1], P[1], TS[1], ...V[1]}
...
R[n] = {S[n], P[n], TS[n], ...V[n]}
```

We can do all sort of thing and come up with `F`. But in this case, we can said we using Machine learning algorithm to find `F`. and then we call `F` artificial inteligent.

In the end, we just wanted all of this (which is all the same thing)

```
F ~ A
F(R) ~ A(R)
F(S, ...V) ~ A(S, ...V)
F(S, P, T ...V) ~ A(S, P, T ...V)
F(R[0]) ~ A(R[0]) = R[1]
F(V[0]) ~ A(V[0]) = V[1]
F({v[0][0], v[0][1], ... v[0][n]}) ~ A({v[1][0], v[1][1], ... v[1][n]})
F({S[0], P[0], TS[0], ...V[0]}) ~ A({S[1], P[1], TS[1], ...V[1]})
```

So we minimize the diferent of `F` and `A`, base on all the record we have, and within our current calculation tools which happend to be the Computer/GPU/TPU/NPU ... (any name). Lead to some extreme way to achive it like

- **Large language model - LLM** try to achive that by try to describle reality more closly, that we add every possible parameter (i said different way to call "achivable variable") - 80 bilions? Even more?
- **Deep neuron network - DNN, CNN, ect** try to create a so complex function (via matrix multiple calculator, log fucntion on neutralizing vector ..) that can aproximate any possible set of vector record into a function given enough memory to store all of those layer of layer of "weight"

Most Machine learning research try to:
- Someone could try to find a better way to describing reality via novel way (it mean "never/haven't been used yet" way - in reseach terminology)
- Using a novel "better" way to trial and error function to make the compute a bit quicker.
- While we "dumb" people try to use the same thing over and over on our own "interested" state `S` (which mostly for Porn/Waifu/Day dreamming generator, well at least I can create my vested pfp so it good enough) or some trash research like [this](https://github.com/nghiango1/WebDefaceDetection)

> In the end `F` is a function, it take in variable and give a output. But I haven't see any programmer try on to take this task yet.

#### Analyzing

I explaining every term were use in Machine learning here

##### [Loss function](https://en.wikipedia.org/wiki/Loss_function)

Every different we when comparing the record
- Delta `δ` or uppercase `Δ` (We are just too used to call them this name in math)
- Lost function is how we calculate `δ`, which can be whatever math function we can think of which make sense.
    ```
    δ = L(F(R[0]), R[1])
    δ = L(F(V[0]), V[1])
    ```

    > One of the most common used is euclid distance (in some special case where we use pure propability to represent reality then we will need more advanged distance function like Bayes' theorem function even), I will try to point these out later when we walk through specific a research paper.


The total of all different in our record: 
- Sum of all delta, let call it `∑(δ)`

Some time we have continous of record, we can us this symbol instead but both are the same thing
- Sum of all delta, let call it `∫(δ)`

Most appoard of Machine learning is that we try and find a way to minimized the Lost function by changing `F`

> Eh. I bore so let end here.

#### Extra

We can take the part snapshot into our calculation, but the total variable in equaltion is unbalance and kept increasing

```
R[0] ~ {S[0], P[0], TS[0], R[<0], ...V[0]}
R[1] ~ {S[1], P[1], TS[1], R[<1], ...V[1]}
```

This can be prevent by **Long term short memory - LTSM** where we take a subset of the part reailty, let say we only using total of `x` snapshot:
```
R[0] ~ {S[0], P[0], TS[0], R[0-x], R[0-(x-1)], ..., R[0 - 1], V[0]}
```

Making it the same problem
```
R[0] ~ {S[0], P[0], TS[0], R[0 - x:0], ...V[0]} = {S[0], P[0], TS[0], R[0 - x:0], ...V[0]}
R[1] ~ {S[1], P[1], TS[1], R[1 - x:1], ...V[1]} = {S[1], P[1], TS[1], R[1 - x:1], ...V[1]}
```
