# Factory

Why overloading constructor not enough for you

## The problem

We need easy way to creat object (multi options burger, car, etc? Builder pattern isn't better?), but it need to be limited than a constructor.

## Implementation

Let start with a application need to connect DB. I will implement DAO and create a Factory to handle the create DAO Instance with 4 database type [Oracle DB](../../oracle/OracleRAC/README.md)/[MySQL DB](../../container/docker/mysql/)/Posgress DB connection/Plain text file (eg: csv).

I mean, will I need JDBC?
- Use it and setup expectation on every implement of my db connection related class structure
- Let not use it yet, and recreate the whell? Add another connection type into the pool, which is Plain text (csv file)?
- Turn out I will just do a in-memory DB instead

I done some structure code, which i think it turn out fine: A example with some web analysis stat data model and some helper interface for database
