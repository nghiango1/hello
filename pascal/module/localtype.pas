procedure echo(hello : string);
type
    point = record 
    x : integer;
    y : integer;
end;
var
    world : string;
    p1 : point;
begin
    world := 'world!';
    writeln(hello, ' ', world);
    p1.x := 1;
    p1.y := 1;
    writeln(p1.x, p1.y);
end;

var
    hello: string;
begin
    hello := 'Hello';
    echo(hello);
    echo(hello);
    echo(hello);
    echo(hello);
end.
