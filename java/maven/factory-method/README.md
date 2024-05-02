# Factory method

Super class hava a common method, that subclass then overide some method and kept the code structure stay unchange.

## The problem

We need already create object model from business analysis, it time to save them into a database. But it start to get tricky when we want to use multiple database where there is some slight different beetween them.

## Implementation

Start with the last factory-pattern where we implemented inmemory/mysql/posgressql database support for our application. But all model -> entities code is a mess with SQL code all over the place. I update all call so that it working, also changing name of all function that Now I understand more what it does.

The firsthing to do is that I change Entity to abtract class, my current ideal is that:

- **Model**: Is a interface that all Business object model implement
- **Entity**: All SQL code will be related to Entity, it will be implement as a abtract class with some share concrete method. A Entity inheritance type can be save into database, which then can be use to store model information (either full or partial of a model)
- **Dao - Data access object**: Is a interface that help to handle inter-connection of (Business object) Model and Entity/Entities

Factory method will appear in Entity class, where we have: Different database and different Model
