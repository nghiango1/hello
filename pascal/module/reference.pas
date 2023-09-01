procedure add1(step: integer; var x : integer);
begin
    x := x + step;
end;

var
    a: integer;
begin
    a := 0;
    writeln(a);
    add1(1, a);
    writeln(a);
    add1(2, a);
    writeln(a);
    add1(3, a);
    writeln(a);
end.
