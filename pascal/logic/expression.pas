var
    b: boolean;
begin
    b := 1 = 1;
    writeln(b); // 1 equal 1 -> true
    b := 1 <> 1;
    writeln(b); // 1 different 1 -> false
    b := 1 >= 1;
    writeln(b); // 1 equal or bigger than 1 -> true
    b := 1 <= 1;
    writeln(b); // 1 equal or less than 1 -> true
    b := 1 > 1;
    writeln(b); // 1 bigger 1 -> false
    b := 1 < 1;
    writeln(b); // 1 less 1 -> false
end.
