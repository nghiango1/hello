procedure swap(var x, y: int64);
var
    tmp: int64;
begin
    tmp := x;
    x := y;
    y := tmp;
end;

procedure bubbleSort(var max: array of int64);
var
    i, j: integer;
begin
    for i:= 0 to 3 do
        for j:= 0 to 3-i-1 do
            if max[j] > max[j+1] then
                swap(max[j], max[j+1]);
end;

function sumTop3(max: array of int64) : int64;
var
    i : integer;
begin
    sumTop3 := 0;
    for i:= 1 to 3 do
        sumTop3 := sumTop3 + max[i];
end;

procedure printTop3(max: array of int64);
var
    i: integer;
begin
    write('Current top 3: ');
    for i:= 0 to 3 do
        write(max[i], ' ');
    writeln;
end;
var
    text : string;
    x : int64;
    code: integer;
    s : int64;
    max: array [0..3] of int64;
    sum: int64;
    i : integer;
begin
    s := 0;
    for i:= 0 to 3 do
        max[i] := 0;
    while true do begin
        readln(text);
        if length(text) <> 0 then begin
            val(text, x, code);
            s := s + x;
        end
        else begin
            max[0] := s;
            bubbleSort(max);
            sum := sumTop3(max);
            printTop3(max);
            writeln('Curr total: ', s, ', sum = ', sum);
            s := 0;
        end;
    end;
end.
