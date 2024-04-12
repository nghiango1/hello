all: build package

build:
	javac -cp greeting-lib.jar Module.java

package:
	jar cfm module.jar Manifest.txt Module.class 

run:
	java -jar module.jar

clean:
	rm -f Module.class
	rm -f module.jar
