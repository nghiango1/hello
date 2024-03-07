/**
 * Log to output
 * @param {...any} x Anything
 */
export const println = function aMoreComplexPrintlnFunctionName(...x) {
    console.log(...x);
};

println("Hello", "world!");

{
    // define function in if statement may cause unwanted behavior (browser
    // handle it differently)
    // const current = false;
    // if (current) {
    //     /**
    //      * @returns {undefined} This won't return
    //      */
    //     var test = function aLongerDescriptionOfTestFunction() {
    //         console.log("Nope.");
    //     };

    //     test();
    // }
    // test();
}

{
    /**
     * aown
     * @param {string} name own
     * @param {{name?: string, test?: string}}  a own
     * @param {...any} args rest
     * @returns {{name?: string, test?: string}} own
     */
    const fn = function handle(name, a = {}, ...args) {
        console.log(args);
        a["name"] = name;
        return a;
    };

    const a = { test: "test" };
    const b = fn("not", a);
    println(a);
    println(b);
}
