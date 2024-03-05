// Defaul import - Which get the export default <variable> in the module file
import anyname, { hello, moduleName } from "./src/hello.mjs";

// Direct import which can call exact module export from module file, which can be
// Function, Class
// Const, var, let
import { add, str } from "./src/more.mjs";

// Wildcast import, which import everything into Name module object(null)
import * as name from "./src/more.mjs";

/**
 * concat two string
 * @param {string} a str1
 * @param {string} b str2
 * @returns {string} Result
 */
function conc(a, b) {
  return a + b;
}

const s = conc("wo", "rld");
anyname(s);
hello(s);

const obj = new moduleName();
hello(obj.name());

console.log(str);

console.log(`Wildcast import name have type '${typeof name}'`);
console.log("with value =", name);

anyname(add(1, 2).toString());
hello(add(1, 2).toString());
