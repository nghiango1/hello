type
    point = record
        x: integer;
        y: integer;
    end;
var
    a : point;
    b : point;
begin
    a.x := 1;
    a.y := 4;
    writeln(a.x);
    writeln(a.y);
    b := a;
    writeln(b.x);
    writeln(b.y);
end.
