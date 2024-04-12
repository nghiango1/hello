# Moduler structure

We can use archived `.jar` of built class file and call them directly in our project. This project reused [lib](../lib) result packaged file as example

## Using Jar library

### Normal run using java

We have to providing all class, jar file directory for java CLI to run our Module

```sh
java -cp ".:greeting-lib.jar" Module
```

### Using jar file

#### Create jar file with class-path metadata

Check on how we compile normal java file with packaged `.jar` file (recheck the command in `Makefile` file)

Compile and package `.jar` file with
```sh
make
```

#### How it work

It this specific line that help java find the coresponding java class from `.jar`
```make
build:
	javac -cp greeting-lib.jar Module.java
#         ^^^ This is sorthand for class-path
package:
	jar cfm greeting.jar Manifest.txt Module.class
#                        ^^^^^^^^ This contain class-path infomation
```

and `Manifest.txt`
```
Class-Path: greeting-lib.jar
```

> This accepting only direct path to the library file, I can't use wildcard (`lib/*`) or libs directory (`lib/`)

So that we can use our module in other java project using `greeting-lib.jar`

```java
import nghiango1.asia.greeting.Greeting;
```

> Java LSP won't be any help though, the best way to do it is via maven/gradle
> local depedany config, eg:
> ```xml
> <dependency>
>     <groupId>com.sample</groupId>
>     <artifactId>sample</artifactId>
>     <version>1.0</version>
>     <scope>system</scope>
>     <systemPath>${project.basedir}/src/main/resources/Name_Your_JAR.jar</systemPath>
> </dependency>
> ```

#### Run the built code

We using linked, so it won't run if our library isn't in the same path

```sh
make run
#or
java -jar module.jar
```
