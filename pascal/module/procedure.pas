procedure echo(hello : string);
var
    world : string;
begin
    world := 'world!';
    writeln(hello, ' ', world);
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
