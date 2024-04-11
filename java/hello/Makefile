all: build package

build:
	javac hello/HelloWorld.java

package:
	jar cfm hello.jar Manifest.txt hello/*.class

run:
	java -jar hello.jar

clean:
	rm -f hello/**/*.class
	rm -f hello/*.class
	rm -f hello.jar
