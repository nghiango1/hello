type
    stacktype = record
        top: ^node;
        length: integer;
    end;
    node = record
        value : string;
        next : ^node;
    end;

const
    operation = ['+', '-', '*', '/', '(', ')'];

procedure push(var s: stacktype; v : string);
var
    temp : ^node;
begin
    if s.top = nil then begin
        new(s.top);
        s.top^.value := v;
        s.top^.next := nil;
    end
    else begin
        new(temp);
        temp^.value := v;
        temp^.next := s.top;
        s.top := temp;
    end;
    s.length := s.length + 1;
end;

function pop(var s: stacktype) : string;
var
    temp : ^node;
begin
    pop := s.top^.value;
    temp := s.top;
    s.top := s.top^.next;
    s.length := s.length - 1;
    dispose(temp);
end;

procedure stackTraversal(stack: stacktype);
var
    temp : ^node;
begin
    writeln('Stack length: ', stack.length, ' element');
    temp := stack.top;
    write('Stack state: ');
    while temp <> nil do begin
        write(temp^.value, ', ');
        temp := temp^.next;
    end;
    writeln('nil');
end;

function top(stack: stacktype) : string;
begin
    top := stack.top^.value;
end;

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

// Check on string handleing to_number.pas
function parse(var s: string; var isParse: boolean) : integer;
var
    v, code: integer;
begin
    val(s, v, code);
    if code > 0 then begin
        writeln('Error parse at ', code, ' th character, c ="', s[code] ,'", value = "', s, '"');
        isParse := false;
    end
    else begin
        writeln('Found: ', v);
        isParse := true;
    end;
    parse := v;
end;

function priority(current: string) : integer;
begin
    if (current = '*') or (current = '/') then priority := 2;
    if (current = '+') or (current = '-') then priority := 1;
    if (current = '(') then priority := 0;
end;

procedure processingCurrentString(current : string; var stack: stacktype; var postfix : string);
var
    isparse : boolean;
    temp : string;
begin
    writeln;
    stackTraversal(stack);
    writeln('Current postfix = ', postfix);
    isparse := false;
    writeln('Processing current = ', current);
    if (current[1] in operation) then begin
        if current = '(' then push(stack, current);
        if current = ')' then begin
            temp := pop(stack);
            while temp <> '(' do begin
                postfix := postfix + temp + ' ';
                temp := pop(stack);
            end;
        end;
        if current[1] in ['+', '-', '*', '/'] then begin
            while stack.length > 0 do begin 
                if priority(current) > priority(top(stack)) then 
                    break;
                postfix := postfix + pop(stack) + ' ';
            end;
            push(stack, current);
        end;
    end
    else begin
        parse(current, isparse);
        if isparse then begin
            postfix := postfix + current + ' ';
        end
        else begin
            writeln('Found unknown value "', current,'" skipping');
        end;
    end;
end;

var
    s : string;
    current : string;
    i : integer;
    postfix : string;
    stack : stacktype;
begin
    readln(s);
    trimSpaceRight(s);
    trimSpaceLeft(s);

    // adding space between operation and number
    for i:= length(s) downto 2 do begin
        writeln(s[i]);
        if (s[i] <> ' ') and ((s[i] in operation) or (s[i-1] in operation)) then begin
            insert(' ', s, i);
        end;
    end;

    trimMoreThan1Space(s);

    writeln(s);

    stack.top := nil;
    stack.length := 0;
    current := '';
    postfix := '';
    for i := 1 to length(s) do begin
        if s[i] <> ' ' then
            current := current + s[i]
        else begin
            processingCurrentString(current, stack, postfix);
            current := '';
        end;
    end;
    if current <> '' then
        processingCurrentString(current, stack, postfix);
    while stack.top <> nil do begin
        postfix := postfix + pop(stack) + ' ';
    end;
    writeln(postfix);
end.
