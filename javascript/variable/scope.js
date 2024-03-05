// Scope with var, let, const
// var is function scope - but the useage can be limited by many eslint rule
// let, const is block scope, as we have a example above
const inc = function increaseANumberByOne(x) {
    console.log('Start call function\n==========\n');
    const a = 1;
    if (a === 1) {
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
        var c = 1;
        console.log(typeof c);

        const b = 2;
        console.log(typeof b);
    }
    console.log(`a is within this scope so it still have value '${a}' with type '${typeof a}'`);
    console.log(`b is out of scope so now it have type '${typeof b}'`);
    console.log(`c used var, so it stil have it value '${c}' with type '${typeof c}' within this function 'inc' scope`);
    /* eslint block-scoped-var: off */
    console.log('====End call function====\n');
    return x + c;
};

console.log(inc(5));
console.log(`c is out of function scope now, so it have type '${typeof c}'`);
