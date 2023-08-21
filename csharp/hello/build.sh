if test ! -d target; then 
    mkdir target
fi;
if test ! -d target/debug; then 
    mkdir target/debug
fi;
if test ! -d target/release; then 
    mkdir target/release
fi;
csc src/hello.cs -debug -out:target/debug/hello.exe;
csc src/hello.cs -out:target/debug/hello.exe;

