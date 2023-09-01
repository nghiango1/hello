var
    f: text;
    i: integer;
begin
    assign(f, 'output.txt');
    rewrite(f);
    for i := 1 to 10 do begin
        writeln(f, i);
    end;
    close(f);
end.
