var
    s : string;
    i : integer;
begin
    readln(s);
    for i := length(s) downto 2 do begin
        insert(',', s, i);
    end;
    writeln(s);
end.
