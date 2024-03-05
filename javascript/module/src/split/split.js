/**
 * Split a string to each character, limited to 5 character
 * @param {string} str string that need to be splited
 * @returns {string[]} splited string limited to at most 5 character
 */
export function sliceString(str) {
  return str.split('', 2);
}

export default { sliceString };
