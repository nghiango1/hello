// Exported module, need to import specifically
export class moduleName {
  name() {
    return 'hello';
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
