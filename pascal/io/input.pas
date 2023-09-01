var
    i: integer;
    v: integer;
    s: integer;
begin
    s := 0;
    for i:= 1 to 3 do begin
        readln(v);
        writeln('You input v = ', v);
        s := s + v;
    end;

    writeln('Total = ', s);
end.
