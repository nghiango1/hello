# Maven hello

Maven project to build a Hello world application

## Install Maven.

Ubuntu Jammy
```sh
apt get install -y open-jdk17-jdk maven
```

## Pom.xml

Maven project will tart with `Pom.xml` file (similar to `package.json`). This help to config project dependancies and build/package/test from source code.

### Define Maven project

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>org.springframework</groupId>
    <artifactId>hello-world</artifactId>
    <packaging>jar</packaging>
    <version>0.1.0</version>
</project>
```

Explain each configuration line;
+ `<modelVersion>`. POM model version (here we using 4.0.0 - always?).
+ `<groupId>`. Group or organization that the project belongs to (Often expressed as an inverted domain name?, eg: **com.google**.protobuf.protoc)
+ `<artifactId>`. Name to be given to the project’s library artifact (for example, the name of its JAR or WAR file).
+ `<version>`. Version of the project that is being built.
+ `<packaging>`. How the project should be packaged. Defaults to "jar" for JAR file packaging. Use "war" for WAR file packaging.

### Define Maven Dependencies

> I currently stuck with Oracle Java 8

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    ...
    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependency>
        <groupId>org.jenkins-ci.plugins</groupId>
        <artifactId>daily-quote</artifactId>
        <version>1.0</version>
    </dependency>
</project>
```

Declares a dependencies will be inside `<dependancies>` tag, follow by a inner list of `<dependancy>` tag.

+ `<groupId>` - The group or organization that the dependency belongs to.
+ `<artifactId>` - The library that is required.
+ `<version>` - The specific version of the library that is required.

We may `<scope>` tag to specify:

+ `provided` - Dependencies that are required for compiling the project code, but that will be provided at runtime by a container running the code (e.g., the Java Servlet API).
+ `test` - Dependencies that are used for compiling and running tests, but not required for building or running the project’s runtime code.

### Define Maven build

```xml
    <properties>
        <maven.compiler.release>17</maven.compiler.release>
    </properties>

    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.8.1</version>
                </plugin>
            </plugins>
        </pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>2.1</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                            </goals>
                        <configuration>
                            <transformers>
                                <transformer
                                    implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>hello.HelloWorld</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

Too many thing happend here, so I don't care, this should be help with the framework you are using. I will stick to other tool to help me handle/configured build step
- Changing java target build (openjdk-17)
- Changing maven compiler plugin to match later version that support JDK 17
- Using maven-shade-plugin for full library compessing that can be run independ

Example output packaged jar file without maven-shade-plugin

```sh
> java -cp target/HelloWorld-0.1.0.jar hello.HelloWorld
# Hello world!
# Exception in thread "main" java.lang.NoClassDefFoundError: com/google/gson/Gson
#         at hello.Greeter$Obj.toJSON(Greeter.java:18)
#         at hello.Greeter.sayHello(Greeter.java:25)
#         at hello.HelloWorld.main(HelloWorld.java:6)
# Caused by: java.lang.ClassNotFoundException: com.google.gson.Gson
#         at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:641)
#         at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:188)
#         at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:525)
#         ... 3 more
```

## Using Maven CLI tool

### Dependancies

Dependencies: pull dependencies from repo (default will be save to `~/.m2/repository` directory).

```sh
mvn install
```

As I usually working with offline build (no local repo being setting up). I use bellow command instead and move all local cache dependancy to have a portable offline build environment (eg: Docker/Contanerized build-env images)

```sh
mvn dependency:go-offline
```

> However, even then, this is not enough. Some dependancies like code generator (by which I mean google protobuf gprc `.proto` file code generator plugin) will be download on build/package step. You have to run
> ```sh
> mvn package
> ```
> To make sure all dependancy is properly download.

### Build

`mvn compile`
  + Simmilar to `javac` that generate class file
  + As we isn't distribute `.class` files directly, most likely we have to package to `.jar` or `.war` instead

`mvn package`
  + Compile your code, run tests, and packaging the code up in a `.jar` (or `.war` base on the configuration)
  + The name of the JAR file will be based on the project’s `<artifactId>` and `<version>`.

### Run project

#### Run compiled code

> This won't run properly as our dependancies isn't there, java can't detect them in runtimes

```sh
mvn compile
java -cp target/classes/ hello.HelloWorld
```

Or we can change directory
```sh
cd target/classes/
java hello.HelloWorld
```

#### Run packaged code

Run `.jar` file
```sh
mvn package
java -cp target/HelloWorld-0.1.0.jar hello.HelloWorld
```

or
```sh
java -jar target/HelloWorld-0.1.0.jar
```
