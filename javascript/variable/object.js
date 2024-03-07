const one = "one";
const a = 1;

const count = {
    // Key with the same value
    a,
    // Function
    /**
     * @returns {number} return 2
     */
    b: function aDifferentName() { return 2; },
    /**
     * @returns {number} return 3
     */
    c() { return 3; },
    // Bad
    /* eslint object-shorthand: off */
    one: one,
    /**
     * @returns {string} return two
     */
    two: function two() { return "two"; },
    /**
     * @returns {string} return three
     */
    three: function() { return "three"; },
    // Good
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
