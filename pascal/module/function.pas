function add(x : integer; y: integer) : integer;
begin
    add := x + y;
end;
var
    a : integer;
begin
    writeln(add(3, 3));
    a := add(5, 3);
    writeln(a);
end.
