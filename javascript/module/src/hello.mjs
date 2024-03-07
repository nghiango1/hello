// Exported module, need to import specifically
/**
 * Have a single property name() that return the namem of the module
 */
export class moduleName {
  /**
   * @returns {string} This module name
   */
  name() {
    return "moduleName";
  }
}

/**
 * Hello the person, or object
 * @param {string} name Who to gretting
 * @returns {undefined} the function will not return anything
 */
export function hello(name) {
  console.log(`hello ${name}`);
}

// Exported default module, which is function hello
// This can be default import with any name
export default hello;
