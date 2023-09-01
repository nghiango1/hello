var
    x: real;
    y: double;
    z: currency;
begin
    x := 1.4;
    writeln(x, ' ', sizeof(x));
    y := 1.4;
    writeln(y, ' ', sizeof(y));
    z := 1.4;
    writeln(z, ' ', sizeof(z));
    z := 1.23456789;
    writeln(z, ' ', sizeof(z));
end.
