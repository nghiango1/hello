type
    stacktype = record
        top: ^node;
        length: integer;
    end;
    node = record
        value : integer;
        next : ^node;
    end;

procedure push(var s: stacktype; v : integer);
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

function pop(var s: stacktype) : integer;
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

function top(stack: stacktype) : integer;
begin
    top := stack.top^.value;
end;

var
    s : stacktype;
begin
    s.top := nil;
    s.length := 0;
    push(s, 1);
    writeln(top(s));
    push(s, 2);
    writeln(top(s));
    stackTraversal(s);
    while s.top <> nil do begin
        pop(s);
    end;
end.
