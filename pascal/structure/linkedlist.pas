uses sysutils;

type
    list = record
        head, tail : ^node;
        length : Longint;
    end;
    node = record
        value : Longint;
        next : ^node;
    end;
    ptrnode = ^node;

procedure listset(var l : list; pos, v : Longint);
var
    p : ^node;
    i : Longint;
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

procedure _get(l : list; pos : Longint; var element : ptrnode);
var
    p : ^node;
    i : Longint;
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

function get(l : list; pos : Longint) : Longint;
var
    p : ^node;
    i : Longint;
begin
    p := nil;
    for i := 1 to pos do begin
        if p = nil then
            p := l.head
        else begin
            p := p^.next;
        end;
    end;
    get := p^.value;
end;

procedure insert(var l : list; pos : Longint; v : Longint);
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

function find(l : list; v : Longint) : Longint;
var
    p : ^node;
    i : Longint;
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

procedure delete(var l: list; pos : Longint); 
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

procedure push(var l : list; v : Longint);
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

function pop(var l : list) : Longint;
var
    p, temp : ^node;
begin
    p := nil;
    _get(l, l.length - 1, p);
    temp := p^.next;
    p^.next := nil;
    pop := temp^.value;
    l.length := l.length - 1;
    dispose(temp);
end;

procedure printList(l : list);
var
    p : ^node;
begin
    write('List size ', l.length);
    // write(' with item: ');
    // p := l.head;
    // write(p^.value);
    // p := p^.next;
    // while p <> nil do begin
    //     write(', ');
    //     write(p^.value);
    //     p := p^.next;
    // end;
    writeln;
end;

var
    l : list;
    i, pos : Longint;
    t : TDateTime;
begin
    Randomize;

    l.length := 0;
    for i := 1 to 1000000 do begin
        push(l,random(1000));
    end;
    printList(l);

    for i := 0 downto -10 do begin
        insert(l, 1, i);
    end;
    printList(l);

    for i := -10 to 0 do begin
        pos := find(l, i);
        delete(l, pos);
    end;
    printList(l);

    for i := 1 downto 5 do begin
        push(l, i);
    end;
    printList(l);

    for i := 1 to 5 do begin
        pop(l);
    end;
    printList(l);
    t := Now;
    writeln(t);

    for i := 1 to 50000-1 do begin
        delete(l, random(500000));
        if i mod 1000 = 0 then writeln(i div 1000);
    end;

    t := Now - t;
    writeln(TimeToStr(t));

    printList(l);
end.
