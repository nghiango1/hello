# Factory

Why overloading constructor not enough for you

## The problem

We need easy way to creat object (multi options burger, car, etc? Builder pattern isn't better?), but it need to be limited than a constructor.

## Implementation

Let start with a application need to connect DB. I will implement DAO and create a Factory to handle the create DAO Instance with 3 database type [Oracle DB](../../oracle/OracleRAC/README.md)/[MySQL DB](../../container/docker/mysql/)/Posgress DB connection.
