# Structure

Package is the main way to create a module structure in Java project. It have a specification to enforce that package name is expected to be the same with the directory name in full relative paths using `.` as the seperator

## Notes

Sometime, companny really want to have they name in the package module. While this could help to make sure we not have collison when merging library/project, it not stricly needed

```sh
.
├── Module.class
├── Module.java
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
