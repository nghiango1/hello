type
    list = record
        value : array [1..100] of integer;
        length : integer;
    end;

procedure push(var l : list; v : integer);
begin
    l.length := l.length + 1;
    l.value[l.length] := v;
end;

function pop(var l : list) : integer;
begin
    pop := l.value[l.length];
    l.length := l.length - 1;
end;

procedure insert(var l : list; pos : integer; v : integer);
var
    i : integer;
begin
    for i := l.length downto pos do begin
        l.value[i+1] := l.value[i]
    end;
    l.value[pos] := v;
    l.length := l.length + 1;
end;

function find(l : list; v : integer) : integer;
var
    i : integer;
begin
    for i := 1 to l.length do begin
        if l.value[i] = v then begin
            find := i;
            break
        end;
    end;
end;

procedure delete(var l: list; pos : integer); 
var
    i : integer;
begin
    for i := pos to l.length do begin
        l.value[i] := l.value[i+1];
    end;
    l.length := l.length - 1;
end;

procedure printList(l : list);
var
    i : integer;
begin
    write('List size ', l.length, ' with item: ');
    for i := 1 to l.length-1 do begin
        write(l.value[i], ', ');
    end;
    writeln(l.value[i+1]);
end;

var
    l : list;
    i, pos : integer;
begin
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
