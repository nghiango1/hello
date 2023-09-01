var
    x : integer;
begin
    x := 1;
    writeln(x);

    if TRUE then begin
        x := 2;
        writeln('logic true ', x);
    end;

    if FALSE then begin
        x := 100;
        writeln('logic false, this is not running ', x);
    end
    else begin
        x := 3;
        writeln('logic false, so this run instead ', x);
    end;
end.
