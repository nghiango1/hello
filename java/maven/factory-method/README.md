# Factory method

Super class hava a common method, that subclass then overide some method and kept the code structure stay unchange.

## The problem

We need already create object model from business analysis, it time to save them into a database. But it start to get tricky when we want to use multiple database where there is some slight different beetween them.

## Implementation

Start with the last factory-pattern where we implemented inmemory/mysql/posgressql database support for our application. But all model -> entities code is a mess with SQL code all over the place.
