type
    integerStack = record
        top: ^node;
        length: integer;
    end;
    node = record
        value : integer;
        next : ^node;
    end;
    stringStack = record
        value: array [1..100] of char;
        length: integer;
    end;

const
    operation = ['+', '-', '*', '/', '(', ')'];

procedure push(var s: integerStack; v : integer);
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

function pop(var s: integerStack) : integer;
var
    temp : ^node;
begin
    pop := s.top^.value;
    temp := s.top;
    s.top := s.top^.next;
    s.length := s.length - 1;
    dispose(temp);
end;

procedure stringpush(var stack: stringStack; value : char);
begin
    stack.length := stack.length + 1;
    stack.value[stack.length] := value;
end;

function stringpop(var stack: stringStack) : char;
begin
    stringpop := stack.value[stack.length];
    stack.length := stack.length - 1;
end;

function stringtop(var stack: stringStack) : char;
begin
    stringtop := stack.value[stack.length];
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
function parse(s: string; var isParse: boolean) : integer;
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

procedure processingInfixString(current : string; var stack: stringStack; var postfix : string);
var
    isparse : boolean;
    temp : string;
begin
    writeln('Current postfix = ', postfix);
    isparse := false;
    writeln('Processing current = ', current);
    if (current[1] in operation) then begin
        if current = '(' then stringpush(stack, current[1]);
        if current = ')' then begin
            temp := stringpop(stack);
            while temp <> '(' do begin
                postfix := postfix + temp + ' ';
                temp := stringpop(stack);
            end;
        end;
        if current[1] in ['+', '-', '*', '/'] then begin
            while stack.length > 0 do begin 
                if priority(current) > priority(stringtop(stack)) then 
                    break;
                postfix := postfix + stringpop(stack) + ' ';
            end;
            stringpush(stack, current[1]);
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

function toPostfix(s : string): string;
var
    current : string;
    i : integer;
    postfix : string;
    stack : stringStack;
begin
    stack.length := 0;

    current := '';
    postfix := '';
    for i := 1 to length(s) do begin
        if s[i] <> ' ' then
            current := current + s[i]
        else begin
            processingInfixString(current, stack, postfix);
            current := '';
        end;
    end;
    if current <> '' then
        processingInfixString(current, stack, postfix);
    while stack.length > 0 do begin
        postfix := postfix + stringpop(stack) + ' ';
    end;

    toPostfix := postfix;
end;

procedure processingPostfixString(current : string; var stack: integerStack);
var
    x, y, value: integer;
    isparse : boolean;
begin
    writeln('Processing current = ', current);
    isparse := false;
    // try parse to integer
    if (current[1] in operation) then begin
        if stack.length < 2 then begin
            writeln('Formula error, not enough element for calculation');
        end;
        y := pop(stack);
        x := pop(stack);
        writeln('Found valid operation, do: ', x, ' ', current, ' ', y);
        if current[1] = '+' then push(stack, x + y);
        if current[1] = '-' then push(stack, x - y);
        if current[1] = '*' then push(stack, x * y);
        if current[1] = '/' then push(stack, x div y);
    end
    else begin
        value := parse(current, isparse);
        if isparse then
            push(stack, value)
        else
            writeln('Cant parse to integer, skipping');
    end;
end;

var
    s : string;
    current : string;
    i : integer;
    stack: integerStack;
begin
    readln(s);
    trimSpaceRight(s);
    trimSpaceLeft(s);
    trimMoreThan1Space(s);

    // adding space between operation and number
    for i:= length(s) downto 2 do begin
        writeln(s[i]);
        if (s[i] <> ' ') and ((s[i] in operation) or (s[i-1] in operation)) then begin
            insert(' ', s, i);
        end;
    end;
    s := toPostfix(s);

    current := '';
    stack.top := nil;
    stack.length := 0;
    for i := 1 to length(s) do begin
        if s[i] <> ' ' then
            current := current + s[i]
        else begin
            processingPostfixString(current, stack);
            current := '';
        end;
    end;
    if current <> '' then
        processingPostfixString(current, stack);
    while stack.top <> nil do begin
        writeln(pop(stack));
    end;
end.
