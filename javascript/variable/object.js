// Object.prototype.hasOwnProperty.call(obj_name, '<property name>')
const one = "one";
const a = 1;

const count = {
    // Key with the same value
    a,
    // Function
    b: function aDifferentName() { return 2; },
    c() { return 3; },
    // Bad
    /* eslint object-shorthand: off */
    one: one,
    two: function two() { return "two"; },
    three: function() { return "three"; },
    // Good
};

console.log(count);

// Check if object have a property
// Check object.js
const have = Object.prototype.hasOwnProperty.call(count, "c");
if (have) console.log("count object have c property");

// Bad-way with example
/* eslint no-prototype-builtins: off */
console.log(count.hasOwnProperty("c"));
console.log(count.hasOwnProperty("d"));

const nullobj = Object.create(null);
console.log(Object.prototype.hasOwnProperty.call(nullobj, "c"));
try {
    console.log(nullobj.hasOwnProperty("c"));
} catch (error) {
    console.log("This throw error=", error);
}
const notcount = {
    /**
     * Echo back any string
     * @param {string} x any thing param
     * @returns {string} echo it back
     */
    hasOwnProperty(x) { return x; },
    c: "c",
};
console.log(Object.prototype.hasOwnProperty.call(notcount, "c"));
console.log(notcount.hasOwnProperty("c"));
