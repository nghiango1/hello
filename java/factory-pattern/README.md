# Factory

Why overloading constructor not enough for you
- You want a Iterface factory, not class
- You want to seperating create object code/logic from the actual what that type can do. So you chose a universally used OOP patern for this job.

## The problem

We need easy way to creat object (multi options burger, car, etc? Builder pattern isn't better?), but it different than a constructor.

## Implementation

Let start with a application need to connect DB. I will implement DAO and create a Factory to handle the create DAO Instance with 4 database type [Oracle DB](../../oracle/OracleRAC/README.md)/[MySQL DB](../../container/docker/mysql/)/Posgress DB connection/Plain text file (eg: csv).

I mean, will I need JDBC?
- Use it and setup expectation on every implement of my db connection related class structure
- Let not use it yet, and recreate the whell? Add another connection type into the pool, which is Plain text (csv file)?
- Turn out I will just do a in-memory DB instead

I done some structure code, which i think it turn out fine: A example with some web analysis stat data model and some helper interface for database

My first thought on factory implement is that I don't need it at all. Even when trying to create a situation expecially for it, I ain't think I could go too far from constructor overloading and an justify the need of Factory pattern. Let focus on implementing all DB support first, which need me to including Database driver for connecting DB and come with dependancy management. This give me two options:

- Using OS manage it (My current local machine using NIXOS, so dependancies will be me to care)
- Using other project dependancies management like Gradle or Maven (which mean I would need to switch my dev environment as NixOS won't work that well with other package manager - yes, gradle/maven/lazy.nvim/npm are all package manager)

After some consideration, I decide to do both. But it on my other Ubuntu server instead of the local one. I already fired up MySQL Database instance. Let see:

## Database connector

It better to use a package manager now so just go to Maven implement of this same project [../maven/factory-pattern/](../maven/factory-pattern/)

Database connector mean one thing, the program want to connect to the database manage server. And ofcouse, it reuse able, not every db related object will need to create a new connection (we can also have a pool of available connection readdy for more HA).

As we can reuse some object, or initilized it in-case we haven't done it yet. A factory class seem to be a better palace to contain all this code.

This mean all possible object related to creating the same Class/ or even better a Interface could be managed by a purposed Factory class.

Or we have normal option to code a/multi overload constructor function try to access/create all needed infomation to make the object within the concress class. This however could take a lager amount of that type code.

It is obviously more clear to and have others to focus on what the type can do instead of having 50% of code related to create the object it-self. Currently this is the best explaination so far from me.
