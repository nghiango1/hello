import { println } from "./function.mjs";

const one = "one";
const a = 1;

{
    const count = {
        // Key with the same value
        a,
        // Function
        /** @returns {number} return 2 */
        b() { return 2; },
        /** @returns {number} return 3 */
        c: function aDifferentName() { return 3; },
        // Bad
        /* eslint object-shorthand: off */
        one: one,
        /** @returns {string} return two */
        two: function two() { return "two"; },
        /** @returns {string} return three */
        three: function() { return "three"; },
    };

    console.log(count);

    // Check if object have a property
    const have = Object.prototype.hasOwnProperty.call(count, "c");
    if (have) console.log("count object have c property");


    // Bad-way with example - hasOwnProperty can be overiden, could also be not
    // initilized in object(null) which has null prototype. So the result of 
    // <object>.hasOwnProperty() is unreliable.

    /* eslint no-prototype-builtins: off */

    // true - count do have c in it property
    console.log(count.hasOwnProperty("c"));

    // false - count don't have d in it property
    console.log(count.hasOwnProperty("d"));


    const nullobj = Object.create(null);
    // false - nullobj have null prototype, so it not have 'c' property
    console.log(Object.prototype.hasOwnProperty.call(nullobj, "c"));

    // error - nullobj have null prototype, so it not have 'hasOwnProperty'
    // function property. And try to access a null cause error throw
    try {
        // Error - object(null) have null prototype, which will throw error if we
        // try to access its prototype
        console.log(nullobj.hasOwnProperty("c"));
    } catch (error) {
        console.log("This throw error: ", error);
    }

    // hasOwnProperty here is overiden
    const notcount = {
        /**
         * Echo back any string
         * @param {string} x any thing param
         * @returns {string} echo it back
         */
        hasOwnProperty(x) { return x; },
        c: "c",
    };

    // true - notcount have c prototype
    console.log(Object.prototype.hasOwnProperty.call(notcount, "c"));

    // "c" - notcount have hasOwnProperty prototype, which is a function that echo
    // the param back to us
    console.log(notcount.hasOwnProperty("c"));
}

// Copy object property with object spread (introduced in ES2018)
{
    const original = { a: 1, b: 2 };
    const copy = { ...original, c: 3 };
    println(copy);

    const { a, ...noA } = copy;
    println("Do some thing with", a);
    println("Rest are:", noA);
}

// Copy object property with object.assign (used in ES2018 and bellow)
{
    // very bad - wrong way of useage Object.assign(target, source)
    {
        /** @type {{a?: number, b:number}}*/
        const original = { a: 1, b: 2 };
        const copy = Object.assign(original, { c: 3 }); // this mutates `original` (the target) and return it reference back to copy
        println("cpy-verybad", copy);
        println("ori-verybad", original);
        delete copy.a; // so does this
        println("cpy-verybad", copy);
        println("ori-vertbad", original);
    }

    // bad - Object.assign right usage with two args
    {
        /** @type {{a?: number, b:number}}*/
        const original = { a: 1, b: 2 };
        const copy = Object.assign({ c: 3 }, original); // this mutates `original` (the target) and return it reference back to copy
        println("cpy-bad", copy);
        println("ori-bad", original);
        delete copy.a; 
        println("cpy-bad", copy);
        println("ori-bad", original);
    }
    // bad - difference way of useage when three and more arg
    {
        /** @type {{a?: number, b:number}}*/
        const original = { a: 1, b: 2 };

        /* eslint prefer-object-spread: off */

        // Eslint rule can convert this to ES2018 syntax
        const copy = Object.assign({}, original, { c: 3 }, { d: 4 });
        println("cpy", copy);
        delete copy.a; 
        println("cpy", copy);
        println("ori", original);
    }
}
