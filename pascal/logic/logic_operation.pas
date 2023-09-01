var
    b : boolean;
    t : integer;
begin
    b := TRUE xor FALSE;
    writeln(b);
    b := TRUE or FALSE;
    writeln(b);
    b := TRUE and FALSE;
    writeln(b);
    // bytes wise logic operation does logic calculation on coresponsed bit one by one
    t := %1010110101100101 xor %1010100010101010;
    writeln(binstr(t, sizeof(t) * 8), ' integer have size = ', sizeof(t), ' which mean it have ', sizeof(t) * 8, ' bits');
    t := %0110101001010101 or %0010101000101001;
    writeln(binstr(t, sizeof(t) * 8));
    t := %1001010000101001 and %0101001010101000;
    writeln(binstr(t, sizeof(t) * 8));
end.

