function priority(x : char): integer;
begin
    if ord(x) >= ord('a') then 
        priority := ord(x) - ord('a') + 1
    else
        priority := ord(x) - ord('A') + 27;
end;

var
    s : string;
    res : integer;
    i : integer;
    check : int64;
begin
    readln(s);
    res := 0;
    while (length(s) <> 0) do begin
        check := 0;
        if (length(s) mod 2) <> 0 then begin
            writeln('Error, odd length string: ', s);
            break;
        end;
        for i := 1 to (length(s) div 2) do begin
            check := check or (1 << priority(s[i]));
            writeln(check);
        end;
        for i := (length(s) div 2)+1 to length(s) do begin
            if check >> priority(s[i]) and 1 = 1 then begin
                writeln('Found: ', s[i]);
                res := res + priority(s[i]);
                break
            end;
        end;
        writeln('Total priority: ', res); 
        readln(s);
    end;

    writeln('Total priority: ', res); 
    
end.
