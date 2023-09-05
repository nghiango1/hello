uses sysutils;

type
    list = record
        value : array [1..2000000] of Longint;
        length : Longint;
    end;

procedure push(var l : list; v : Longint);
begin
    l.length := l.length + 1;
    l.value[l.length] := v;
end;

function pop(var l : list) : Longint;
begin
    pop := l.value[l.length];
    l.length := l.length - 1;
end;

procedure insert(var l : list; pos : Longint; v : Longint);
var
    i : Longint;
begin
    for i := l.length downto pos do begin
        l.value[i+1] := l.value[i]
    end;
    l.value[pos] := v;
    l.length := l.length + 1;
end;

function find(l : list; v : Longint) : Longint;
var
    i : Longint;
begin
    for i := 1 to l.length do begin
        if l.value[i] = v then begin
            find := i;
            break
        end;
    end;
end;

procedure delete(var l: list; pos : Longint); 
var
    i : Longint;
begin
    for i := pos to l.length do begin
        l.value[i] := l.value[i+1];
    end;
    l.length := l.length - 1;
end;

procedure printList(l : list);
var
    i : Longint;
begin
    write('List size ', l.length);
    // write(' with item: ');
    // for i := 1 to l.length-1 do begin
    //     write(l.value[i], ', ');
    // end;
    // write(l.value[i+1]);
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
