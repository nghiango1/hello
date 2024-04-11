# Hello

Sample Hello project template with only base Java tool

## Hello world java main function

Every java file should follow this rule: It should contain one class, which have the same name with the file

The main function in the class will be run if it being call to executed. Remember that you will need to compile the code before hand

```sh
javac hello.HelloWorld.java
```

After that, we can call and run the program
```sh
java hello.HelloWorld
```

## Jar package

### Package

Read more [here](https://docs.oracle.com/javase/tutorial/deployment/jar/appman.html), this packages java compiled code to a single file. Allow you to distribute and run code in production environment
```sh
jar cfm hello.jar Manifest.txt hello/*.class
#                 ^^^^^^^^^^^^ This will be explain in the next session
```

Running packaged `.jar` file
```sh
java -jar hello.jar
```

### Manifest

Application bundled contain Manifest file to control running behavior and infomation about the compiled code. We can provide this file when packages our compiled *.class file

Example: Check out `Manifest.txt` file in the repo

- Entry point of the `.jar` file when running
  ```
  Main-Class: hello.HelloWorld
  ```

- Referencing other classes in other JAR files, **this allow us to import jar library as dependancies!!**. This behavior will be explaining more in [module](../module/)
  ```
  Class-Path: jar1-name jar2-name directory-name/jar3-name
  ```

- Other infomation like version or vendor, more can be found [here](https://docs.oracle.com/javase/tutorial/deployment/jar/packageman.html). While this isn't related, this `Created-By` infomation will be generated if we not specified in manifest.
  ```
  Created-By: asia.nghiango
  ```

### Jar file content

`jar` CLI tool could help to read/extract infomation from java archive format 

```sh
jar tf hello.jar
# META-INF/
# META-INF/MANIFEST.MF
# hello/HelloWorld.class
```
