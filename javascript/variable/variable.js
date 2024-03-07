import { println } from "./function.mjs";
println("Hello", "world!");

// Use let and const as they are block scope
const hello = 1;
// Primitives
{
    println("\nPromitives type\n===========\n");
    // string
    const hello = "Hello world!";
    println(`hello inside Promitives scope have value '${hello}' and type '${typeof hello}'`);
    // number
    const x = 1;
    println(`x have value '${x}' and type '${typeof x}'`);
    // boolean
    const b = true;
    println(`b have value '${b}' and type '${typeof b}'`);
    // null
    const nil = null;
    println(`nil have value '${nil}' and type '${typeof nil}'`);
    // undefined
    const undef = undefined;
    println(`undef have value '${undef}' and type '${typeof undef}'`);

    // Symbols and BigInts cannot be faithfully polyfilled, so they should not be used when
    // targeting browsers/environments that don’t support them natively.
    // symbol
    const sym = Symbol("foo");
    println(typeof sym);
    // bigint
    const big = 9007199254740991n;
    println(typeof big);
    println("Promitives scope end\n===========\n");
}

println(`\nhello is out of scope so it now back to type = '${typeof hello}'`);

// Primitives: When you access a primitive type you work directly on its value
{
    println("\nPromitives access\n===========\n");
    const foo = 2;
    let bar = foo;

    bar = 9;
    println(`As we can see, value bar = ${bar} is different from foo = ${foo}.\nWe are not have a reference to foo with bar = foo`);
}

// Complex type
{
    println("\nComplex type\n===========\n");
    // object
    const obj = {
        done: 1,
        notdone: 0,
    };
    println(typeof obj);

    // array
    const arr = [1, 2, 3, 4];
    println(typeof arr);

    // function
    /**
     * Sum two number and return total
     * @param {number} x One
     * @param {number} y Two
     * @returns {number} Result
     */
    const add = function addTwoNumber(x, y) {
        return x + y;
    };
    println(typeof add);
}

// Complex: When you access a complex type you work on a reference to its value.
// Also, alway using const for reference variable
{
    println("\nComplex type access give reference\n===========\n");
    const foo = [1, 2];

    const bar = foo;
    bar[1] = 1;
    println(`As we can see, value bar = ${bar} is the same with foo = ${foo}.\nWe have a reference to foo with bar = foo statement`);
}

// Scope - This break a lot of safety from LSP, eslint rule, so just stick with let and const
// Read more on scope.js

// Object - With some Airbnb js style guilde
{
    println("\nObject type advanced\n===========\n");
    // Also, alway using const for reference variable
    // Key name generate function
    /**
     * Generate a new key name with a salt value
     * @param {string} key salt value
     * @returns {string} generated key
     */
    const newName = function constructKeyNameForObject(key) {
        return `new_${key}`;
    };

    // Init with a generated field name using a function
    // Also, alway using const for reference variable
    const obj = {
        id: "1",
        name: "box",
        [newName("good")]: true,
    };

    println(obj);
    println(obj.new_good);

    // Avoid using these, this cause delearation being seperated
    // This is in airbnb js style guilde but not being enfored by
    // eslint setting
    obj[newName("bad")] = false;

    println(obj.new_bad);

    // Object with method
    const car = {
        engine: "v8",
        /**
         *
         */
        go() {
            println(`car ${this.engine} engine go brrrr...`);
        },
    };

    car.go();

    // Object shorthand is enforce. But eslint rule for jsdoc isn't enforce for object method
    const one = "one";
    const count = {
        // function/method in object

        /**
         * Added something in the end of sentence
         * @param {string} pading Any string
         * @returns {string} function return 'a'
         */
        a(pading) { println("Isn't this great", pading); return "a"; },
        // Key with the same value, eg one: one
        one,
        /**
         * Added something in the end of sentence
         * @param {string} pading Any string
         * @returns {string} function return 'a'
         */
        b: function aDifferentName(pading) { println("So, why don't you use short hand instead", pading); return "b"; },
        c: "c",
    };

    println(count);
    println(count.a("?"));
    println(count.b("!"));
    // Check if object have a property
    println(count.c);
    println(Object.prototype.hasOwnProperty.call(count, "c"));

    // Check more in object.js
}

{
    // Array
    println("\nArray type advanced\n===========\n");
    const a = [];

    // Add new item to array
    // Good
    a.push(1);
    // Bad practice
    a[2] = 2;
    a[a.length] = 3;

    println(a);

    // Copy array - any empty element in new array is undefined
    const b = [...a];
    println(b);

    // A like array object to array - eg: JSON parse result
    // 
    // interface ArrayLike<T> {
    //     readonly length: number;
    //     readonly [n: number]: T;
    // }

    const arrLike = { 0: "foo", 1: "bar", 2: "baz", length: 3 };
    const arr = Array.from(arrLike);

    println(arr);

    // Map over an iterables object should use Array.from
    // Example: Map is iterables
    /** @type {Map<number, number>} */
    const mapObj = new Map();
    mapObj.set(3, 1);
    mapObj.set(2, 2);
    mapObj.set(1, 3);
    const mapArr = Array.from(mapObj, (value, index) => [index, value[0] + value[1]]);
    println(mapArr);

    const reduceArr = arr.reduce((acumulator, value) => (acumulator.concat(value)));
    println(reduceArr);

    const filterArr = arr.filter((_value, index) => {
        if (index[0] % 2 !== 0) {
            return true;
        }
        return false;
    });
    println(filterArr);
}

{
    // jsdoc and lsp - Ref: https://www.typescriptlang.org/docs/handbook/jsdoc-supported-types.html
    // Example: Create new object that can iterables with jsdoc-suported-types
    println("\nObject with type define\n===========\n");

    // To set up a new iterable object as example, we need to following
    // Interator protocol interface, which we will define right here

    /**
     * Properties for our custom type
     * @typedef {object} InterableObjectProperties
     * @property {number} counter counter
     *
     * Custom interableObject
     * @typedef {InterableObjectProperties & Iterable<number>}  CustomIterable
     */

    /** @type {CustomIterable} */
    const iterableObject = {
        counter: 0,

        /**
         * Part of Iterator protocol, required to make the object iterable
         * @returns {Iterator<number, IteratorResult<number, boolean>>} Part of Iterator protocol
         */
        [Symbol.iterator]() {
            // This is a function of iterableObject, so we can access its value
            // from `this` keyword

            const upper = this;

            /** @type { Iterator<number> } */
            const interatorObject = {
                /**
                 * Part of Iterator protocol
                 * @returns {IteratorResult<number>} Part of Iterator protocol
                 */
                next() {
                    if (upper.counter < 10) {
                        upper.counter += 1;
                        return {
                            done: false,
                            value: upper.counter
                        };
                    }

                    const iteratorResult = {
                        done: true,
                        value: upper.counter,
                    };

                    return iteratorResult;
                },

                /**
                 * (Optional) Part of Iterator protocol - Can be call to finish the iterate process early so the iterator can properly clean up
                 * @param {number} value Caller repassed data back to iterator
                 * @returns {IteratorResult<number>} Part of Iterator protocol
                 */
                return(value) {
                    console.log("Do some thing with", value);
                    console.log("Done interating, cleanning up");

                    const iteratorResult = {
                        done: true,
                        value: value,
                    };
                    return iteratorResult;
                },

                /**
                 * (Optional) Part of Iterator protocol - Can be call to finish the iterate process early that cause of error so the iterator can properly handle and clean up
                 * @param {Error} exception Caller repassed a error back to iterator
                 * @returns {IteratorResult<number>} Part of Iterator protocol
                 */
                throw(exception) {
                    console.log("Do some thing with", exception);
                    console.log("Done interating, cleanning up");
                    const iteratorResult = {
                        done: true,
                        value: upper.counter,
                    };
                    return iteratorResult;
                }
            };

            return interatorObject;
        }
    };

    /**
     * Adding the index value of element to the current value
     * @param {number} v Value used for map
     * @param {number} i Index of the Value
     * @returns {number} sum value and index
     */
    const mapFn = function mapFunction(v, i) {
        return i + v;
    };

    const iterArr = Array.from(iterableObject, mapFn);
    println(iterArr);
}

{
    // Class
    // We can use @implements to add a interface into a class, which enforce
    // property into our class
    println("\nClass define\n===========\n");
    /**
     * Custom iterable class
     * @implements {Iterable<number>}
     */
    class customIterableClass {
        /**
         *
         */
        constructor(counter = 0) {
            this.counter = counter;
        }

        /**
         *
         */
        [Symbol.iterator]() {
            // This is a function of iterableObject, so we can access its value
            // from `this` keyword

            const upper = this;

            /** @type { Iterator<number> } */
            const interatorObject = {
                /**
                 *
                 */
                next() {
                    if (upper.counter < 10) {
                        upper.counter += 1;
                        return {
                            done: false,
                            value: upper.counter
                        };
                    }

                    const iteratorResult = {
                        done: true,
                        value: upper.counter,
                    };

                    return iteratorResult;
                },

                /**
                 *
                 */
                return(value) {
                    console.log("Do some thing with", value);
                    console.log("Done interating, cleanning up");

                    const iteratorResult = {
                        done: true,
                        value: value,
                    };
                    return iteratorResult;
                },

                /**
                 *
                 */
                throw(exception) {
                    console.log("Do some thing with", exception);
                    console.log("Done interating, cleanning up");
                    const iteratorResult = {
                        done: true,
                        value: upper.counter,
                    };
                    return iteratorResult;
                }
            };

            return interatorObject;
        }
    }

    const iterableObject = new customIterableClass();

    /**
     * Adding the index value of element to the current value
     * @param {number} v Value used for map
     * @param {number} i Index of the Value
     * @returns {number} sum value and index
     */
    const mapFn = function mapFunction(v, i) {
        return i + v;
    };

    const iterArr = Array.from(iterableObject, mapFn);
    println(iterArr);

    /**
     * @typedef Base
     * @property {number} done what
     */

    /**
     * @implements {Base}
     */
    class custom {
        what = 1;
        who = 1;
        /**
         * Constructor
         */
        constructor() {
            this.done = 2;
        }
    }

    const a = new custom();
    println(a);
}


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
