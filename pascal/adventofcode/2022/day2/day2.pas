function calscore(A, B: char) : integer;
var
    x, y: integer;
begin
    x := ord(A) - ord('A');
    y := ord(B) - ord('X'); 
    writeln(x, ' ', y);
    if x = y then
        calscore := y + 1 + 3
    else begin
        if ((x + 1) mod 3) = y then
            calscore := y + 1 + 6
        else
            calscore := y + 1;
    end;
end;
var
    s: string;
    res: int64;
begin
    res := 0;
    while True do begin
        readln(s);
        if length(s) = 0 then begin
            writeln('Score: ', res);
            break
        end
        else begin
            res := res + calscore(s[1], s[3]);
            writeln('Score: ', res);
        end;
    end;
end.
