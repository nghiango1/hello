type
    DSTreeValueType = integer;
    DSTreeNode = record
        value : DSTreeValueType;
        left, right : ^DSTreeNode;
    end;
    DSTreeNodeType = ^DSTreeNode;

function search( r : DSTreeNodeType ; v : DSTreeValueType) : DSTreeNodeType;
var
    p : DSTreeNodeType;
    z : integer;
begin
    z := sizeof(DSTreeValueType) * 8;
    p := r;
    while (p <> nil) and (p^.value <> v) do begin
        z := z - 1; 
        if (v >> z) and 1 = 0 then p := p^.left else p := p^.right;
    end;
    search := p;
end;

procedure delete( r : DSTreeNodeType ; v : DSTreeValueType);
var
    p, node, rootP : DSTreeNodeType;
    z : integer;
begin
    z := sizeof(DSTreeValueType) * 8;
    p := r;
    while (p <> nil) and (p^.value <> v) do begin
        z := z - 1; 
        rootP := p;
        if (v >> z) and 1 = 0 then p := p^.left else p := p^.right;
    end;

    if (p = nil) then exit;

    node := p;

    while (p^.left <> nil) and (p^.right <> nil) do begin 
        rootP := p;
        if p^.left <> nil then p := p^.left else p := p^.right;
    end;

    node^.value := p^.value;
    if r = p then 
        r := nil
    else begin
        if rootP^.left = p then
            rootP^.left := nil
        else
            rootP^.right := nil;
    end;
    dispose(p);
end;

procedure push(var r : DSTreeNodeType ; v : DSTreeValueType);
var
    rootP, p : DSTreeNodeType;
    z : integer;
begin
    z := sizeof(v) * 8;
    p := r;
    while (p <> nil) and (p^.value <> v) do begin
        z := z - 1;
        rootP := p;
        if (v >> z) and 1 = 0 then
            p := p^.left
        else
            p := p^.right;
    end;
    if p = nil then begin
        new(p);
        p^.value := v;
        p^.left := nil;
        p^.right := nil;
        if r = nil then begin
            r := p;
        end
        else begin
            if (v >> z) and 1 = 0 then begin
                rootP^.left := p;
            end
            else begin
                rootP^.right := p;
            end;
        end;
    end;
end;

procedure printTree(r : DSTreeNodeType);
procedure helper(r : DSTreeNodeType; indent : integer);
var
    i : integer;
begin
    if r = nil then exit;
    for i:= 1 to indent-2 do write(' ');
    if indent > 0 then begin
        write('+');
        write('-');
    end;
    write(r^.value, ' (' );
    for i:= sizeof(r^.value) * 8 downto 1 do
        write((r^.value >> (i - 1)) and 1);
    writeln(')');
    if r^.left <> nil then helper(r^.left, indent + 2);
    if r^.right <> nil then helper(r^.right, indent + 2);
end;

begin
    writeln('Tree print:');
    helper(r, 0);
end;

var
    root, p : DSTreeNodeType;
    i, test : integer;
begin
    root := nil;
    for i := 0 to 100 do begin
        push(root, random(100));
    end;
    printTree(root);
    for i := 1 to 100 do begin
        test := random(100);
        p := search(root, test);
        write(test, ' is ');
        if p <> nil then begin 
            writeln('found');
            delete(root, test);
        end
        else writeln('not found');
    end;
    printTree(root);
end.

