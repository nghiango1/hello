# AI

The corrected way to use the full potential power of computer, but we got lost too much with just LLM

## Why? The meanning behind all this notes

This containning the snapshot of my current knowleged (which is heavily influence by mathematic or programming way of thinking) that i don't want to get lost and rootten within the capability of my brain. Thoughts will easily and eventually change over the time, so I deciding to write it here as soon as possible.

This is just planing for a serial of notes at the moment. I sure hope I can finish all of these. Before all of that, let set up a list to divide infomation I wanted to cover into a make sensed section here:

> All of these is subjected and should change-able though

- [Preface - What is AI](#preface---what-is-ai)
- Chapter 1: Relation of posibility
- Chapter 2: The serialization of event
- Chapter 3: Complete of knowlege via example
- Chapter 4: The predictation - future

### Chapter 1: Relation of posibility

1. What is a variable:
    - How data is represented in machine learning: Text, Image, Audio, Human, Our mind, Nature
    - Vector in machine learning
2. What is a posibility:
    - What is machine learning useage
    - Output of machine learning
3. How to representing relation of variable/posibility
    - Weight
    - Function
    - Lost function
    - Risk
    - Learning rate
    - Compute Operand used in machine learning

### Chapter 2: The serialization of event

- What is a event
- What is a serialization event
- How to representing them in form of variable/posibility
- How to push variable into machine learning model

### Chapter 3: Complete of knowlege via example

- What is knowlege
- What is example
- I use example to create knowleged

### Chapter 4: The predictation - future

- Creating Knowleged and predicting future is the same problem
- Pass data is the represenative of the future

## Preface - What is AI

Each person comsumming knowleged (research, study, learning, ...) in different way, link-ing and storing them in their mind diffirently, by that this preface try to set you into the view angle of mine.
- You certainly have a different view point, of which I didn't try to change.
- But try to put yourself into my view could make it easier to understanding why/how I can come up with all of these definition

> A word - By it self alone - can't express a full ideal behind of the person who talked/said it

A simplest example for this is:
- "Data analisys" let stop and think what it mean to you
- "Data analisys" for me - who tent to expand the meanning in a board general with a really lossen boundary: A act of which **variable** are group together to find possible **pattern**
 
> I highlight **variable**, and **pattern** words here because they can be expand, defined better. From this point, all of that bellow is my - the writer - view point.

### The underline ideal of Randomess, Pattern and Variable

**Randomness**: Drawing one card from a desk of 52 different cards, they share the same possibility which one could be draw. But in the end the 1/52 change will happened.

> (1) I said that: Every possible outcome but one one of which will happened

**Pattern**: Various variable could possible affect the fair chance of which one card can be favor to be draw, and prove that the drawed card will be **the 7 shade**.

> (2) I also said that: The absolute trust - One of which will happened - already being the defined

**Variable**: Let say the trust being already defined because of all variability. I then divide them into two source

1. Achievable variables
    - The drawer specifically choose the card by a rule (alway chossing first card of the desk)
    - The rule is writen right on the front of drawer (a sign is in place beforehand)
    - The dealler/desk is forced (7 shade already on the top of the desk at beginning)

2. Un-achievable variables
    - Higher dimention affection that will destermine the absolute outcome (God plan - The controler theories: Our reality is just a simulator)
    - The absolute outcome of the multiple possible reality (I borrow the term **Multiverse theories** - of which "we" split in all possible reality where each outcome have been decided)

> Currently I farvor **multiverse theories**, of which there were NO capable tools can get us to access to splited reality (each different outcome is in it's own **unobservable** universes, this may different from normal multiverse theories). The one where I/We currently living could be in control and force, but everything could happened will already happened.

When using **compute** power, we taking all of achievable variable and make a "inteligent farseer". That try to make sense of the outcome via our avaliable achievable variable

Which way different from those who belived they can access the un-achievable variable and "guessing" on the full 100% of variable (both achievable variable and un-achievable variable)

When I said about "inteligent farseer", it can be call as any different name (Try find words of which share the same meanning in your case). They, the "inteligent farseer", are stored everywhere through out the era: Knowledged, Interlectual property, Book, Movie, Religon, ... and one of those latest "hot name" AI - artificial intelligence. Where the **compute** power being used to create/extracting artificial intelligence is our best available tools - The computer/CPU/GPU/TPU/NPU... whatever the name we can call it

> In my current logic, organic creatured (including us) and everything around us share the same trait of "inteligent farseer". They (everything) themself are "inteligent farseer"... that affected themself to move/change in a predictable nature. Thus, causing us - human to "belive" nature is predictable, of which it is not.

### Math - or programing term?

> Let say it in "Math" term, so we don't just talk in hypertheorical. But in the end, I think they look the same anyway

#### Define our problem - the Framwork of which I will use to explain everything

The [s]tate of reality - find "inteligent [f]arseer" on **achievable [v]ariable problem**:
- Let look at an achievable variable `S` - we call it **[s]tate `S`**, which is a part of our **[r]eality - let call it `R`**
    ```
    S in R
    ```
- When [r]eality changing: We see `S` is in state `S[0]`, and then will change to it's next state `S[1]` regardless
- We only know `S[0]` but want to know `S[1]` even before it happened
- We have a set record of every possible occurrent (in our reality) that achievable where state `S` is changing
    ```
    S[0] in R[0]
    S[1] in R[1]
    ```
- It not limit on How big the state is though, which mean `S` could look like this
    ```
    S = {s[0], s[1] .. s[n]}
    S[0] = {s[0][0], s[0][1] .. s[0][n]}
    S[1] = {s[1][0], s[1][1] .. s[1][n]}
    ```

The **[p]ossible outcome - Let call it `P`**:
- `P` is the set of possible outcome from which `S[0]` could change into
    - This could came from all the record we found about `S`
    - Also could came from all the availabe theories where `S` might end up from `S[0]`
- We also assumming that `P` is large enough to contain `S[1]`
    ```
    P = {p[0], p[1], p[2] .. p[n]}
    S[1] "should be" in P
    ```
    > `n` is just a expression of multiple element, and isn't mean that total element of `S` = `P`

The [t]ime:
- We call every occurrent this change is **a unit of [t]ime tick `T`**. We (assumming that we) know the time `T`, which is how long it take state `S[0]` to change into new state
- `T` is (assumming to be) either
    ```
    T = 1 = a [c]onstant = C
    ```
- Or it is a new achievable variable problem it self, where we already solved
    ```
    T = {TS[0], TS[1]..}
    ```

The un-describable [r]eality problem:
- This is a sub-problem, where we know that we can't have the full snapshot of our current reality
    ```
    R = achievable + un-achievable variable
    ```
- A snapshot of **all achievable [v]ariable, let call it `V`** in which state `S` is recorded. They are the capabilty of every tools we can use to describing and achiving infomation from reality
    ```
    V = {v[0], v[1] .. v[n]}
    ```

    > Let borrowing `~` symbol to indicate the meanning of close and hopefully equal)

    ```
    R ~ V = {v[0], v[1] .. v[n]}
    R[0] ~ V[0] = {v[0][0], v[0][1] .. v[0][n]}
    R[1] ~ V[1] = {v[1][0], v[1][1] .. v[1][n]}
    ```

    > This it self is wrong in our case, as we ganning more and more achievable variable over the time, not all of it yet. Most of the time, our `V[0]` have less variables than `V[1]` (`length(V[0]) < length(V[1])`)

- In realness, we could said that our state `S` share the same trait with `R`, where some un-achievable variable is (could also be) a part of `S`
    ```
    S ~ {s[0], s[1] .. s[n]}
    ```

    > Because we are the one who defined set of state `S` that we want to predict, we often skip this problem


- We can also see that `S[0]` and `S[1]` is a part of reality that we can record, so:
    ```
    S[0] in V[0]
    S[1] in V[1]
    ```

- And time `T` it self is also known:
    ```
    T = C in V
    ```
    or
    ```
    TS[0] in V[0]
    TS[1] in V[1]
    ```

- Because `P` also within our capability, it also a part of achievable [v]ariable
    ```
    P in V
    ```

- So, we could conclude that

    > Let borrowing the `...` symbol to indicate the rest of a set

    ```
    R ~ V = {v[0], v[1] .. v[n]}
    R ~ V = {S, P, T, v[3], v[4] .. v[n]}
    R ~ {S, P, T, ...V}
    ```

    or in the specific record V when `S[0]` and `S[1]` happened
    ```
    R[0] ~ V[0] = {S[0], P[0], TS[0], ...V[0]}
    R[1] ~ V[1] = {S[1], P[1], TS[1], ...V[1]}
    ```
    
The record of [r]eality, where just by observing our reality we can get a lot of this infomation:
- We can call it **past data record**
    ```
    R[0] ~ {S[0], P[0], TS[0], ...V[0]}
    R[1] ~ {S[1], P[1], TS[1], ...V[1]}
    ..
    R[n] ~ {S[n], P[n], TS[n], ...V[n]}
    ```
- Past record of reality is also a variable, but it come with some cost, that the total variable in equaltion is unbalance and kept increasing:
    ```
    R[0] ~ {S[0], P[0], TS[0], R[<0], ...V[0]}
    R[1] ~ {S[1], P[1], TS[1], R[<1], ...V[1]}
    ..
    R[n] ~ {S[n], P[n], TS[n], R[<n], ...V[n]}
    ```
- This can be prevent by using **short memory** (you could relate it to Long term short memory - LTSM) where we only take a subset of the past record to describe our reality `R`, let say we only using total of `x` snapshot:
    ```
    R[0] ~ V[0] = {S[0], P[0], TS[0], R[0-x], R[0-(x-1)] .. R[0 - 1], V[0]}
    ```
    Making it share the same total variables
    ```
    R[0] ~ {S[0], P[0], TS[0], R[0 - x:0], ...V[0]}
    R[1] ~ {S[1], P[1], TS[1], R[1 - x:1], ...V[1]}
    ..
    R[n] ~ {S[n], P[n], TS[n], R[n - x:1], ...V[n]}
    ```


**The "inteligent [f]arseer" - let call it `F`** - where using `F` we can turn `S[0]` to a result which really close to (and hopefully is equal to) `S[1]`
    ```
    F(S[0]) = Calculated_Result ~ S[1]
    ```

- We take into account that we know so much on [r]eality, we push `R ~ V` into our equaltion. Most of machine learning will using this equaltion
    ```
    F(R[0]) ~ F(S[0], ...V[0]) = Calculated_result ~ {S[1]}
    ```
    But it can be even better
    ```
    F(R[1]) ~ F(S[0], ...V[0]) = Calculated_result ~ {S[1], ...V[1]} = R[1]
    ```

**The [a]bsolute trust - Let call it `A`** - That from a state `S` it can desterminding the true outcome of the next state:
    ```
    A(S[0]) = S[1]
    ```
    or even better
    ```
    A(R[0]) = S[1]
    A(R[0]) = {S[1], ...V[1]} = R[1]
    ```

> We can do all sort of thing and come up with `F`. But in this case, we can said we using Machine learning algorithm to find `F`. and then we call `F` artificial inteligent.

In the end, we just wanted all of this (all are the same thing)
```
F ~ A
F(R) ~ A(R)
F(S, ...V) ~ A(S, ...V)
F(S, P, T ...V) ~ A(S, P, T ...V)
F(R[0]) ~ A(R[0]) = R[1]
F(R[0]) ~ R[1]
F(V[0]) ~ V[1]
F(V[0]) ~ S[1]
F(S[0]) ~ S[1]
F({v[0][0], v[0][1], ... v[0][n]}) ~ V[1] = A({v[0][0], v[0][1], ... v[0][n]}) = R[1]
F({S[0], P[0], TS[0], ...V[0]}) ~ V[1] = {S[1], P[1], TS[1], ...V[1]} = A({S[0], P[0], TS[0], ...V[0]})
```

So we spending to most of our time to minimize the diferent of `F` and `A`, base on all the record we have, and within our current calculation tools (which we focus on Computer/GPU/TPU/NPU ...) ability. This lead to some extreme way to achive it like:

- **Large language model - LLM** try to achive that by try to describle reality more closly, that we add every possible parameter (i said different way to call "achivable variable") - 80 bilions? Even more?
- **Deep neuron network - DNN, CNN, ect** try to create a so complex function (via matrix multiple calculator, log fucntion on neutralizing vector ..) that can aproximate any possible set of vector record into a function given enough memory to store all of those layer of layer of "weight"

Most Machine learning research try to:
- Someone could try to find a better way to describing reality via novel way (it mean "never/haven't been used yet" way - in reseach terminology)
- Using a novel "better" way to trial and error function to make the compute a bit quicker.
- While we "dumb" people try to use the same thing over and over on our own "interested" state `S` (which mostly for Porn/Waifu/Dreamming generator, well at least I can create my vested pfp so it good enough) or some trash research like [this](https://github.com/nghiango1/WebDefaceDetection)

> In the end `F` is a function, it take in variable and give a output. But I haven't seen any programmer take their time try to explain this task in computer scienced term yet.

#### Analyzing created AI (or found `F`)

I explaining sample terminologies in Machine learning using my above "math" framework

##### [Loss function](https://en.wikipedia.org/wiki/Loss_function)

When we goes through all of past record. We found there is a different between the result of `F(V[0]) or F(S[0])` (predicted result) and `S[1] = A(V[0])` (actual new state that have been recorded) - Let call it Delta `δ` or uppercase `Δ` - As we are just too used to call them this name in math class
- Lost function is how we calculate `δ`, which can be whatever math function we can think of that make sense.
    ```
    δ = L(F(R[0]), R[1])
    δ = L(F(V[0]), V[1])
    ```

    > One of the most common used is euclid distance (in some special case where we use pure propability to represent reality then we will need more advanged distance function like Bayes' theorem function even), I will try to point these out later when we walk through specific a research paper.

The "total" of all different in our record: 
- "Sum" of all delta, let call it `∑(δ)`
- Some time we have continous of record, we can us this symbol instead `∫(δ)`

> Sum isn't nessesary addition, it again can be any function that make sense: Even multiply

##### Weight

Most appoard of Machine learning is that we try and find a way to minimized the lost function by changing `F`, the approach we come up with is [w]eight or `W` - the set of changing calculation.
- Again `W` is an obserable and definable variable so
    ```
    W in V
    R ~ V = {W, ...V}
    ```

    So we have `F`
    ```
    F(R[0]) ~ F(V[0]) = F(W, ...V[0]) ~ S[1]
    ```
    or 
    ```
    F(R[0]) ~ F(V[0]) = F(W, ...V[0]) ~ {S[1], ...V[1]} ~ A(R[0]) = R[1]
    ```

- As long as thing that be used to representing a **relationship** of variable `V[0]` to `V[1]`, is consider a part of `W`. But we optional assume (to solve the problem) that
    
    ```
    F = W <computable-operand> ...V
    W <computable-operand> ...V[0] = F({W, ...V[0]}) ~ S[1]
    W <computable-operand> ...V[0] ~ V[1]
    ```

    > Computable-operand example:  Matrix multiply operand, Euclid multiply, Addition, Log, Sqrt...
    > Or just any programable function with `(W, ...V[0])` input that can output result that similar to S[1]
    >
    > Oh, and Computable-operand also a part of `V`, as we can expect it, let call it `O`
    > ```
    > O in V
    > F = W O V[0] ~ S[1]
    > ```

- We (again) can assumming that
    weight `W` is a constant
    ```
    W = 1 = a constant = C
    ```

    or a solvable problem
    ```
    W = {WS[0], WS[1], ...}
    ```

- Weight which in Machine learning - or specially Deep learning - can be in form of multiple matrix, vector (each can be called layer) and in the most case is a constant after which model is formed.

In normal useage of Machine learning, we option try to avoid

```
∫(δ) or ∑(δ) = Sum of all loss function over our recorded data == 0
```

Because it became "bias" over the data, thus causing result to be too predictable. The cause can be because:
- We have far too less of achiveable variable of each record
- We know that we assumming thing to be too ideal, which by adding a layer of uncertainty, that will help us to reach the absoluted trust `A`

LLM model are built without any respect on that
- We push achiveable variable to the max capacity of our tools knowed boundary
- We force the ideal that we can reach `F = A`, which mean `∑(δ) = δ = 0`
- Again, we kinna fail to do so, but at least current morden applicance of LLM try adapting and adding more and more variable to the equation (Example: Human feedback, more token, more advanged computable-operand, ...)

Every form of increasing `V` is a form of growing, adapting, bettering of AI. We still need to do the learn step though, which can come in form of:
- Automatically changing `W`
- Re-calculating and updating `W`
- Adding more `W`
- Changing `O`

As long as `∑(δ) ~ 0` or `F ~ A` or `F[1]` is better than `F[0]` within our metric

### After notes

> We can reach the true AI like Skynet? When machine is capablable of increasing `V` and increasing it own compute power by themself?

Compute power increasing need to be pay (in any price, take any form). I alway look at human race as a whole one, where our race/me stand on all achivement of history which already try compute our current reality (We have a very big `W`). Computer at the moment which can't access all available `V`, is not that much to be overthink of, just a tool on our hand, but it sure is running in the right path to became the next one to replacing us all to become the "higher inteligent life form".

It was 4 year ago, when I belived more on The controller theories - which there is a true controller that guiding our ever changing direction. All possible race, animal, tree, everything... is just wandering arround, then "one will above all" try to control our existen for their wanted result. To think that every achivement that one could get is already controled to happened is just too sad to lived on, where we is (not that much different from computer in previous quote) a tool that being used.

Why I stress on this point so much? It could be becaused of the ideal that "I wanted to create a full virtual/simulated reality by myself (by programing of course)". It should likely for the benefit of understanding our/my current reality, but thing change over the time that the current I right now is unsure on my first true motivation which got losted in the past.
