import { sliceString } from "./split/split.mjs";

const one = 1;
const two = 2;

/**
 * Sum two number
 * @param {number} x number 1
 * @param {number} y number 2
 * @returns {number} result
 */
function add(x, y) {
  return x + y;
}

const str = sliceString("This is it");

// Export seperated from all init is possible
export { one, two, add, str };

// Defaul export as a object with str shorthand value
export default { str };
