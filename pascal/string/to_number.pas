var
    s : string;
    current : string;
    i, v, code: integer;
begin
    readln(s);
    current := '';
    for i := 1 to length(s) do begin
        if s[i] <> ' ' then
            current := current + s[i]
        else begin
            val(current, v, code);
            if code > 0 then
                writeln('Error parse at ', code, ' th character, c ="', current[code] ,'", value = "', current, '"')
            else
                writeln('Found: ', v);
            current := '';
        end;
    end;
end.
