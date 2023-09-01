type
    MyRec = record
        value: integer;
        next: ^MyRec;
    end;
var
    linkedList : MyRec;
    a : integer;
    b : ^integer;
    c : ^integer;
    pointerLinkedList : ^MyRec;
begin
    linkedList.value := 1;
    linkedList.next := nil;
    writeln(linkedList.value);
    writeln(linkedList.next = nil);
    a := 2;
    b := @a;
    writeln(a);
    writeln(b^);

    // Or use addr()
    writeln('=============');
    b := addr(a);
    writeln(a);
    writeln(b^);

    // Assign to another pointer
    writeln('=============');
    c := b;
    writeln(b^);
    writeln(c^, ' ', b = c);

    // Alocating new memory
    writeln('=============');
    new(b);
    b^ := 4;
    writeln(a);
    writeln(b^, ' (we can see that a and b is different)');
    dispose(b); // Remember to dispose after new

    // User defined struct type is simmilar
    writeln('=============');
    pointerLinkedList := @linkedList;

    writeln(pointerLinkedList^.value);
    writeln(pointerLinkedList^.next = nil);

    // User defined struct type is simmilar
    writeln('=============');
    new(pointerLinkedList);
    pointerLinkedList^.value := 2;
    pointerLinkedList^.next := nil;

    writeln(pointerLinkedList^.value);
    writeln(pointerLinkedList^.next = nil);
    dispose(pointerLinkedList); // Remember to dispose after new
end.
