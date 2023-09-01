var
    f: text;
    fo: text;
    i: integer;
    v: integer;
    s: integer;
    str: string;
begin
    assign(f, 'input.txt');
    reset(f);
    s := 0;
    for i:= 1 to 4 do begin
        read(f, v);
        s := s + v;
        writeln(v)
    end;
    writeln(s);
    writeln('========');

    // Real until end of file
    while not eof(f) do begin
        readln(f, str);
        writeln(fo,str);
    end;
    close(f);
end.
