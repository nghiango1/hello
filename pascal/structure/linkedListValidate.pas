type
    list = record
        head, tail : ^node;
        length : integer;
    end;
    node = record
        value : integer;
        next : ^node;
    end;
    ptrnode = ^node;

procedure listset(var l : list; pos, v : integer);
var
    p : ^node;
    i : integer;
begin
    p := nil;
    for i := 1 to pos do begin
        if p = nil then begin
            p := l.head;
        end
        else begin
            p := p^.next;
        end;
    end;
    p^.value := v;
end;

procedure _get(l : list; pos : integer; var element : ptrnode);
var
    p : ^node;
    i : integer;
begin
    p := nil;
    for i := 1 to pos do begin
        if p = nil then
            p := l.head
        else begin
            p := p^.next;
        end;
    end;
    // writeln('Found: ', p^.value);
    element := p;
end;

procedure _insert(var l : list; pos : integer; v : integer);
var
    p, temp : ^node;
begin
    if (l.head = nil) or (pos = 1) then begin
        new(temp);
        temp^.value := v;
        temp^.next := l.head;
        l.head := temp;
        if l.tail = nil then begin
            l.tail := l.head;
            l.length := 0;
        end;
        l.length := l.length + 1;
    end
    else begin
        p := nil;
        _get(l, pos-1, p);
        if p <> nil then begin
            new(temp);
            temp^.value := v;
            temp^.next := p^.next;
            p^.next := temp;
            l.length := l.length + 1;
        end;
    end;
end;

function find(l : list; v : integer) : integer;
var
    p : ^node;
    i : integer;
begin
    // writeln('Finding ', v);
    p := l.head;
    i := 1;
    while p <> nil do begin
        if p^.value = v then begin
            find := i;
            break
        end;
        i := i + 1;
        p := p^.next;
    end;
end;

procedure _delete(var l: list; pos : integer); 
var
    p, temp : ^node;
begin
    p := nil;
    _get(l, pos-1, p);
    if p <> nil then begin
        temp := p^.next;
        p^.next := temp^.next;
        dispose(temp);
        l.length := l.length - 1;
    end;
end;

procedure _push(var l : list; v : integer);
begin
    if l.head = nil then begin
        new(l.head);
        l.head^.value := v;
        l.head^.next := nil;
        l.tail := l.head;
        l.length := 1;
    end
    else begin
        new(l.tail^.next);
        l.tail := l.tail^.next;
        l.tail^.value := v;
        l.tail^.next := nil;
        l.length := l.length + 1;
    end;
end;

function _pop(var l : list) : integer;
var
    p, temp : ^node;
begin
    p := nil;
    _get(l, l.length - 1, p);
    temp := p^.next;
    p^.next := nil;
    _pop := temp^.value;
    l.length := l.length - 1;
    dispose(temp);
end;

function validateGet(l : list; pos : integer) : boolean;
begin
    validateGet := (1 <= pos) and (pos <= l.length );
end;

function validateInsert(l : list; pos : integer) : boolean;
begin
    validateInsert := (1 <= pos) and (pos <= l.length + 1) and (l.length < 100);
end;

function get(l : list; pos : integer) : integer;
var
    p : ^node;
begin
    p := nil;
    if validateGet(l, pos) then begin
        _get(l, pos, p);
        get := p^.value;
    end;
end;

procedure push(var l : list; v : integer);
begin
    if validateInsert(l, l.length + 1) then begin
        _push(l, v);
    end;
end;

function pop(var l : list) : integer;
begin
    if validateGet(l, l.length) then begin
        pop := _pop(l);
    end;
end;

procedure insert(var l : list; pos : integer; v : integer);
begin
    if validateInsert(l, pos) then begin
        _insert(l,pos,v);
    end;
end;

procedure delete(var l: list; pos : integer); 
begin
    if validateGet(l, pos) then begin
        _delete(l, pos);
    end;
end;

procedure printList(l : list);
var
    p : ^node;
begin
    write('List size ', l.length, ' with item: ');
    p := l.head;
    write(p^.value);
    p := p^.next;
    while p <> nil do begin
        write(', ');
        write(p^.value);
        p := p^.next;
    end;
    writeln;
end;

var
    l : list;
    i, pos : integer;
begin
    l.head := nil;
    l.tail := nil;
    l.length := 0;
    for i := 1 to 10 do begin
        push(l,i);
    end;
    printList(l);

    for i := 0 downto -10 do begin
        insert(l, 1, i);
    end;
    printList(l);

    for i := -2 to 4 do begin
        pos := find(l, i);
        delete(l, pos);
    end;
    printList(l);

    for i := 1 to 5 do begin
        pop(l);
    end;
    printList(l);
end.
