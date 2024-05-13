# Maven

I used Maven as Java dependancy manager, some what simmilar to npm vs node. Maven build config on the other hand seem complicate but isn't that good vs other options

## Notes

### Class-path manifest for packaged jar file

After some trouble when packing `*.jar` file with all needed dependancy, it seem better just goes back to manifest file class-path. Maven support this via jar plugin.
- `<mainClass>Main</mainClass>` Add main class entry so our packaged `*.jar` can executable

- `<addClasspath>true</addClasspath>` Add all dependancy `*.jar` class file class-path records, this then being modified by these two option
    - `<classpathLayoutType>repository</classpathLayoutType>` Generate full path that resemble a maven local repo file path
    - `<classpathPrefix>/home/osadmin/.m2/repository/</classpathPrefix>` Prefix all class-path with `/home/<username>/.m2/repository/`, which is default path for my VM's local maven repo                 

This mean I can use automated maven dependancy repo pull to local directly in class-path, this allow better dev exprience at the moment of writing.

```xml
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <configuration>
        <archive>
            <manifest>
                <addClasspath>true</addClasspath>
                <mainClass>Main</mainClass>
                <classpathPrefix>/home/osadmin/.m2/repository/</classpathPrefix>
                <classpathLayoutType>repository</classpathLayoutType>
                </manifest>
            <manifestFile>src/main/resources/META-INF/MANIFEST.MF</manifestFile>
            </archive>
        </configuration>
    </plugin>
```

### All-in-one packaged

I currently using shade to package all dependance into build `*.jar` file which I have little control over. This lead to

JDBC driver conector isn't reconised after pack the code with this in the `pom.xml` file
```xml
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.3</version>
        </dependency>
```

and then being fix by swapping their order
```xml
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.3</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>
```

While this could solve the solution, it seems to be a big problems on the reliable of the plugin in the futures.
