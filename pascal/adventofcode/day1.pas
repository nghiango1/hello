var
    text : string;
    x : int64;
    code: integer;
    s : int64;
    max: int64;
begin
    s := 0;
    max := 0;
    while true do begin
        readln(text);
        if length(text) <> 0 then begin
            val(text, x, code);
            s := s + x;
        end
        else begin
            if max < s then
                max := s;
            writeln(s, ' ', max);
            s := 0;
        end;
    end;
end.
