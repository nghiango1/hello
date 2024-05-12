# Maven

I used Maven as Java dependancy manager, some what simmilar to npm vs node. Maven build config on the other hand seem complicate but isn't that good vs other options

## Notes

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
