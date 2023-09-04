type
    int8array = array [1..1000] of integer;

procedure swap(var x : integer; var y : integer);
var
    temp : integer;
begin
    temp := x;
    x := y;
    y := temp;
end;


procedure quicksort(var a: int8array; l:integer; r: integer);
var
    m, i, j : integer;
    target : integer;
begin
    if l > r then  exit;
    m := (l + r) div 2;
    target := a[m];

    i := l;
    j := r;
    repeat
        while a[i] < target do i := i + 1;
        while a[j] > target do j := j - 1;
        if i <= j then begin
            swap(a[i], a[j]);
            i := i + 1;
            j := j - 1;
        end;
    until i > j;
    quicksort(a, l, j);
    quicksort(a, i, r);
end;

procedure printArray(var a: int8array; len: integer);
var
    i : integer;
begin
    for i := 1 to len do begin
        write(a[i]);
        write(', ');
    end;
    writeln;
end;

var
    a : int8array;
    i, len : integer;
begin
    len := 1000;
    for i := 1 to len do a[i] := random(10000);

    quicksort(a, 1, len);

    printArray(a, len);
end.
