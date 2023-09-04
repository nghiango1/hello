type
    int32array = array [1..1000] of integer;

procedure printArray(a : int32array ; length : integer);
var
    i : integer;
begin
    write('List length ', length,' with element: ');
    for i := 1 to length do begin
        write(a[i]);
        write(' ');
    end;
    writeln;
end;

procedure printBitArray(a : int32array ; length : integer);
var
    i,j : integer;
begin
    write('List length ', length,' with element: ');
    for i := 1 to length do begin
        for j := sizeof(a[i]) * 8 downto 1 do begin
            write((a[i] >> (j - 1)) and 1);
        end;
        writeln;
    end;
end;

procedure radixsort(var a: int32array; l : integer; r : integer ; bit : integer);
var
    i, j : integer;
    temp : integer;
begin
    if (l > r) then exit;

    i := l;
    j := r;
    while i <= j do begin
        while (a[i] >> bit) and 1 = 0 do i := i + 1;
        while (a[j] >> bit) and 1 = 1 do j := j - 1;
        if (i <= j) then begin
            temp := a[i];
            a[i] := a[j];
            a[j] := temp;
            i := i + 1;
            j := j - 1;
        end;
    end;

    if bit > 0 then begin
        radixsort(a, l, j, bit - 1);
        radixsort(a, i, r, bit - 1);
    end;
end;

var
    i : integer;
    a : int32array;
    length : integer;
begin
    length := 1000;
    for i := 1 to length do 
        a[i] := random(10000);
    printArray(a, length);
    //printBitArray(a, length);
    radixsort(a, 1, length, sizeof(a[1]) * 8);
    printArray(a, length);
    //printBitArray(a, length);
end.
