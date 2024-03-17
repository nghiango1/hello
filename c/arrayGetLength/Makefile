all: mkbuilddir arr arrlen

mkbuilddir:
	mkdir -pv dist

arr:
	g++ array.cpp -o array -fdiagnostics-plain-output -g -Wall -Wextra

arrlen:
	g++ arrlen.cpp -o arrlen -fdiagnostics-plain-output -g -Wall -Wextra

clean:
	rm dist/array dist/arrlen
