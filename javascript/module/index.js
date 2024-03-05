import { hello } from './src/hello.js';
import { add } from './src/more.js';

/**
 * concat two string
 * @param {string} a str1
 * @param {string} b str2
 * @returns {string} Result
 */
function conc(a, b) {
  return a + b;
}

const s = conc('wo', 'rld');
hello(s);

hello(add(1, 2).toString());
