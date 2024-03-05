// Use let and const as they are block scope
// Primitives
{
    console.log('\nPromitives type\n===========\n');
    // string
    const hello = 'Hello world!';
    console.log(`hello have value '${hello}' and type '${typeof hello}'`);
    // number
    const x = 1;
    console.log(`x have value '${x}' and type '${typeof x}'`);
    // boolean
    const b = true;
    console.log(`b have value '${b}' and type '${typeof b}'`);
    // null
    const nil = null;
    console.log(`nil have value '${nil}' and type '${typeof nil}'`);
    // undefined
    const undef = undefined;
    console.log(`undef have value '${undef}' and type '${typeof undef}'`);

    // Symbols and BigInts cannot be faithfully polyfilled, so they should not be used when
    // targeting browsers/environments that don’t support them natively.
    // symbol
    const sym = Symbol('foo');
    console.log(typeof sym);
    // bigint
    const big = 9007199254740991n;
    console.log(typeof big);
}

console.log(`hello is out of scope so it now type = '${typeof hello}'`);

// Primitives: When you access a primitive type you work directly on its value
{
    console.log('\nPromitives access\n===========\n');
    const foo = 2;
    let bar = foo;

    bar = 9;
    console.log(`As we can see, value bar = ${bar} is different from foo = ${foo}.\nWe are not have a reference to foo with bar = foo`);
}

// Complex type
{
    console.log('\nComplex type\n===========\n');
    // object
    const obj = {
        done: 1,
        notdone: 0,
    };
    console.log(typeof obj);

    // array
    const arr = [1, 2, 3, 4];
    console.log(typeof arr);

    // function
    const add = function addTwoNumber(x, y) {
        return x + y;
    };
    console.log(typeof add);
}

// Complex: When you access a complex type you work on a reference to its value.
// Also, alway using const for reference variable
{
    console.log('\nComplex type access give reference\n===========\n');
    const foo = [1, 2];

    const bar = foo;
    bar[1] = 1;
    console.log(`As we can see, value bar = ${bar} is the same with foo = ${foo}.\nWe have a reference to foo with bar = foo statement`);
}

// Scope - This break a lot of eslint rule, so just stick with let, const

// Object - Airbnb js style guilde
{
    console.log('\nObject type advanced\n===========\n');
    // Also, alway using const for reference variable
    // Key name generate function
    const newName = function constructKeyNameForObject(key) {
        return `new_${key}`;
    };

    // Init with a generated field name using a function
    // Also, alway using const for reference variable
    const obj = {
        id: '1',
        name: 'box',
        [newName('good')]: true,
    };

    console.log(obj);
    console.log(obj.new_good);
    // Avoid using these, this cause delearation being seperated
    // This is in airbnb js style guilde but not being enfored by
    // eslint setting
    obj[newName('bad')] = false;

    console.log(obj.new_bad);

    // Object with method

    const car = {
        engine: 'v8',
        go() {
            console.log(`car ${this.engine} engine go brrrr...`);
        },
    };

    car.go();

    // Object shorthand is enforce
    // Check more in object.js
    const one = 'one';
    const count = {
        // function/method in object with short hand syntax
        // a: function () {},
        // a: function a() {},
        a() {},
        // Key with the same value
        // one: one,
        one,
        // This is allowed
        b: function aDifferentName() {},
        c: 'c',
    };

    console.log(count);

    // Check if object have a property
    console.log(Object.prototype.hasOwnProperty.call(count, 'c'));
    // Check more in object.js
}

// class
// type Node struct {
//     value int
//     next *Node
// }
//
// type LinkedList struct {
//     head *Node
//     tail *Node
//     length int
// }
//
// func initLinkedList(l *LinkedList) {
//     l.length = 0
//     l.head = nil
//     l.tail = nil
// }
//
// func pushLinkedList(l *LinkedList, v int) {
//     l.length += 1;
//     var newNode *Node = &Node{v, nil};
//     if l.head == nil {
//         l.head = newNode;
//         l.tail = l.head;
//     } else {
//         l.tail.next = newNode;
//         l.tail = l.tail.next;
//     };
// }
//
// func printLinkedListCompact(l *LinkedList) {
//     fmt.Printf("List length = %d", l.length)
//     fmt.Println()
// }
//
// func printLinkedList(l *LinkedList) {
//     var p *Node
//     p = l.head
//
//     fmt.Printf("List length = %d, with element:", l.length)
//     for i:= 0; i < l.length; i ++ {
//         fmt.Printf(" %d", p.value)
//         if p != nil { p = p.next }
//     }
//     fmt.Println()
// }
//
// type ArrayList struct {
//     value [1_000_000]int32
//     length int
// }
//
// func initArrayList(l *ArrayList) {
//     l.length = 0
// }
//
// func pushArrayList(l *ArrayList, v int32) {
//     l.value[l.length] = v
//     l.length += 1;
// }
//
// func printArrayListCompact(l *ArrayList) {
//     fmt.Printf("List length = %d", l.length)
//     fmt.Println()
// }
//
// func printArrayList(l *ArrayList) {
//     fmt.Printf("List length = %d, with element:", l.length)
//     for i:= 0; i < l.length; i ++ {
//         fmt.Printf(" %d", l.value[i])
//     }
//     fmt.Println()
// }
//
// func main() {
//     // Test commending
//     message = "Hello world!"
//     fmt.Println(message, len(message))
//     fmt.Println(unsafe.Sizeof(i))
//     fmt.Println(unsafe.Sizeof(i2))
//     fmt.Println(unsafe.Sizeof(i3))
//     fmt.Println(unsafe.Sizeof(i4))
//     fmt.Println(unsafe.Sizeof(i5))
//
//     var multi int = 10_000
//     var length int = 100*multi
//     var total int = 5*multi
//     var first int = 50*multi
//
//     var list LinkedList
//     initLinkedList(&list)
//     testLinkedList(length, list, total, first)
//
//   var list2 ArrayList
//     initArrayList(&list2)
//     testArrayList(length, list2, total, first)
// }
//
// func testArrayList(length int, list2 ArrayList, total int, first int) {
//   for i := 0; i < length; i++ {
//     pushArrayList(&list2, int32( rand.Intn(1000000)))
//   }
//
//   printArrayListCompact(&list2)
//
//   var now = time.Now()
//   for i := 0; i < total; i++ {
//     var pos int = rand.Intn(first)
//     deleteArrayList(&list2, pos)
//     if i%1000 == 0 {
//       fmt.Printf("%d ", i/1000)
//     }
//   }
//
//   fmt.Println()
//
//   fmt.Println("ArrayList test done in: ", (-now.UnixMilli()+time.Now().UnixMilli())/1000, "s")
//   printArrayListCompact(&list2)
// }
//
// func deleteArrayList(l *ArrayList, pos int) {
//     l.length --
//
//     for i = pos; i < l.length; i ++ {
//         l.value[i] = l.value[i+1]
//     }
// }
//
// func testLinkedList(length int, list LinkedList, total int, first int) {
//   for i := 0; i < length; i++ {
//     pushLinkedList(&list, rand.Intn(1000000))
//   }
//
//   printLinkedListCompact(&list)
//
//   var now = time.Now()
//   for i := 0; i < total; i++ {
//     var pos int = rand.Intn(first)
//     deleteLinkedList(&list, pos)
//     if i%1000 == 0 {
//       fmt.Printf("%d ", i/1000)
//     }
//   }
//
//   fmt.Println()
//   fmt.Println("LinkedList test done in: ", (-now.UnixMilli()+time.Now().UnixMilli())/1000, "s")
// }
//
// func deleteLinkedList(l *LinkedList, pos int) {
//     var p *Node
//     var i int
//
//     l.length --
//     p = l.head
//     if pos == 0 {
//         l.head = l.head.next
//         if l.head == nil {
//             l.tail = nil
//         }
//         return
//     }
//
//     for i = 1; i < pos; i ++ {
//         p = p.next
//     }
//
//     var nextP *Node = p.next
//     p.next = nextP.next
// }
