function fibonachi(x: integer) : integer;
begin
    if x < 0 then begin
        fibonachi := -1;
    end
    else if x = 0 then begin
        fibonachi := 0;
    end
    else if x <= 2 then begin
        fibonachi := 1;
    end
    else begin
        fibonachi := fibonachi(x-1) + fibonachi(x-2);
    end
end;
var
    i : integer;
begin
    for i := 0 to 10 do begin
        writeln(fibonachi(i));
    end;
end.

