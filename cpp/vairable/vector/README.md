# Vector

The basic array container that we mainly use for every posible `std::` supported
functionality

## Interator object

Almost all of `std::` will return interator object, which act and behave like a
pointer. For usual use case, we will want to:

- Get the index of found element: `it - vector.begin()`
- Get the value of found element: `*it`

## Max

`std::max_element(vector.begin(), vector.end())`: Can be use to get the max value
of a vector

`std::lower_bound(vector.begin(), vector.end(), target)`: Biary search the
`target` and get the lowest possible index of it if there is one within the
vector (as interator object). Other wise it return the index where the element
should be

`std::upper_bound(vector.begin(), vector.end(), target)`: Biary search the
`target` and get the lowest possible index with value higher than target (as
interator object).
