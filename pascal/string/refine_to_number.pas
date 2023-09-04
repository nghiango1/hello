// Check on String handling. trim.pas
procedure trimSpaceRight(var s: string);
begin
    while (length(s) > 0) and (s[length(s)] = ' ') do begin
        delete(s, length(s), 1);
    end;
end;

procedure trimSpaceLeft(var s: string);
begin
    while (length(s) > 0) and (s[1] = ' ') do begin
        delete(s, 1, 1);
    end;
end;

procedure trimMoreThan1Space(var s: string);
var
    i : integer;
begin
    for i:= length(s) downto 2 do begin
        if (s[i] = s[i-1]) and (s[i] = ' ') then
            delete(s, i, 1);
    end;
end;

// Seperating parse module
function parse_integer(s: string; var isParse: boolean) : integer;
var
    v, code: integer;
begin
    val(s, v, code); // build-in function
    if code > 0 then begin
        writeln('Error parse at ', code, ' th character, c ="', s[code] ,'", value = "', s, '"');
        isParse := false;
    end
    else begin
        writeln('Found: ', v);
        isParse := true;
    end;
    parse_integer := v;
end;

// Self-code
function parse(s: string; var isParse: boolean) : integer;
var
    i, v: integer;
begin
    v := 0;
    isparse := true;
    for i:= 1 to length(s) do begin
        if (ord('0') <= ord(s[i])) and (ord(s[i]) <= ord('9')) then
            v := v * 10  + ord(s[i]) - ord('0')
        else begin
            isparse := false;
            break;
        end;
    end;
    if not isparse then
        writeln('Error parse at ', i, ' th character, c ="', s[i] ,'", value = "', s, '"')
    else 
        writeln('Found: ', v);
    parse := v;
end;

// Check on String handleing to_number.pas
var
    s : string;
    current : string;
    i: integer;
    isparse: boolean;
begin
    readln(s);
    trimSpaceRight(s);
    trimSpaceLeft(s);
    trimMoreThan1Space(s);
    current := '';
    for i := 1 to length(s) do begin
        if s[i] <> ' ' then
            current := current + s[i]
        else begin
            parse(current, isparse);
            current := '';
        end;
    end;
end.
