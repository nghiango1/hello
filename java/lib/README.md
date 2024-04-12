# Lib - Structure

`package` keyword is the main way to create a module structure in Java project. It have a specification to enforce that package name is expected to be the same with the directory name in full relative paths using `.` as the seperator

This create a `package` then package all build class file into a `.jar` archive so that we can distribute the file as other project dependancies.

## `package` keyword and projet struture

Sometime, companny really want to have they name in the package module. While this could help to make sure we not have collison when merging library/project, it not stricly needed

> Also, most using backward domain name eg: com.google.<package-name>.<sub-class>

```sh
.
├── nghiango1
│   └── asia
│       └── greeting
│           ├── Greeting.class
│           └── Greeting.java
└── README.md
```

Here is the structure of the current module project.
- The main module `Module.java` at top level doesn't need to have package name
- Class file `Greeting.java` will be require to be in `nghiango1.aisa.greeting` package 
- `Module.java` can import and use public class of the `nghiango1.aisa.greeting` package by import, then will be freely using public method/properties of that class

## Jar library

> Make doen't that good with javac, so I skip it

Packaged file with

```sh
javac nghiango1/**/*.class
jar cfm greeting-lib.jar Manifest.txt nghiango1/**/*.class
```

Now we actually can use our module in other java project using our created `greeting.jar`

```java
import nghiango1.asia.greeting.Greeting;
```
