var
    arr1 : array [1..10] of shortint;
    arr2 : array [1..10] of integer;
    arr3 : array [1..10] of longint;
    arr4 : array [1..10] of int64;
    c : char;
    ac : Array [1..100] of char;
    s : string;
begin
    // length can tell the length of array 'like' structure
    writeln(length(arr1), ' ', sizeof(arr1[1]));
    writeln(length(arr2), ' ', sizeof(arr2[1]));
    writeln(length(arr3), ' ', sizeof(arr3[1]));
    writeln(length(arr4), ' ', sizeof(arr4[1]));

    // a single character have 1 length, with size 1 bytes
    c := 'A';
    writeln(c, ' ', length(c), ' ', sizeof(c));

    // a static character array have 100 length
    ac:= 'ABCDEFGH';
    writeln(ac,' ', length(ac));

    // a dynamic character array have length modified by the assigned contents
    s := 'Hello';
    writeln(s, ' ', length(s));
    s := 'Many more character';
    writeln(s, ' ', length(s));
end.
