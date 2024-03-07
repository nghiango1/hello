// @ts-nocheck


/**
 * Scope
 * - var: is function scope
 * - let, const: is block scope
 * - function: A function define is a mix of both, we could say it same as var
 * @param {number} x anynumber
 * @returns {number} return x + 1
 */
const inc = function increaseANumberByOne(x) {
    console.log("Start call function\n==========\n");
    const a = 1;
    if (a === 1) {
        console.log("\ninner scope\n==========\n");
        // Javascript doing compile JIT so by redeclearing a will cause
        // this code error, it will not look for upper block when stand
        // along this scope re-init `a`

        // console.log(`a currently have type '${typeof a}'`);

        // This code will get error by eslint because of redecleare `a`
        // But node still can run it no problem

        /* eslint no-shadow: off */
        const a = 3;
        console.log(`a value '${a}' with type '${typeof a}'`);

        /* eslint no-var: off */
        /* eslint vars-on-top: off */
        const b = 2;
        console.log(`b value '${b}' with type '${typeof b}'`);

        var c = 1;
        console.log(`c value '${c}' with type '${typeof c}'`);

        console.log("End inner scope\n==========\n");
    }
    console.log(`a is within this scope so it still have value '${a}' with type '${typeof a}'`);
    console.log(`b is out of scope so now it have type '${typeof b}'`);
    console.log(`c used var, so it stil have it value '${c}' with type '${typeof c}' within this function 'inc' scope`);
    /* eslint block-scoped-var: off */
    console.log("====End call function====\n");

    {
        /* eslint no-inner-declarations: off */
        /* eslint func-style: off */
        /**
         * This is function define which have function scope (similar to var)
         * @param {number} x One
         * @param {number} y Two
         * @returns {number} Result
         */
        function AddTwoNumber(x, y) {
            return x + y;
        }
        console.log("Inner scope AddTwoNumber is", typeof AddTwoNumber);

        /**
         * We can enforce it into this scope by binding it with const
         * @param {number} x One
         * @param {number} y Two
         * @returns {number} Result
         */
        const add = function addTwoNumber(x, y) {
            return x + y;
        };
        console.log("Inner scope add is", typeof add);
    }
    console.log("Outer scope AddTwoNumber is", typeof AddTwoNumber);
    console.log("Outer scope add is", typeof add);

    console.log("End call function\n==========\n");
    return x + c;
};

console.log(inc(5));

console.log("Main scope AddTwoNumber is", typeof AddTwoNumber);
console.log("Main scope add is", typeof add);

console.log(`c is out of function scope now, so it have type '${typeof c}'`);
