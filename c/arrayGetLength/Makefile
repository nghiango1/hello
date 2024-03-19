all: mkbuilddir arr arrlen

mkbuilddir:
	mkdir -pv dist

arr:
	g++ array.cpp -o dist/array -fdiagnostics-plain-output -g -Wall -Wextra

arrlen:
	g++ arrlen.cpp -o dist/arrlen -fdiagnostics-plain-output -g -Wall -Wextra

clean:
	rm dist/array dist/arrlen
