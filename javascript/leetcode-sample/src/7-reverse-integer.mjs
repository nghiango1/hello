/**
 * @param {number} x
 * @return {number}
 */
export var reverse = function(x) {
  let sign = 1;
  if (x < 0) {
    sign = -1;
    x = x * -1;
  }
  let res = x.toString();
  res = res.split("").reverse().join("");
  let number = Number.parseInt(res) * sign;
  if (number <= (2 ** 31) - 1 && number >= -1 * (2 ** 31))
    return number;
  return 0;
};

/**
 * @param {number} x
 * @return {number}
 */
const reverseNumberCalculation = function(x) {
  let sign = 1;
  let BOUND = 2 ** 31 - 1;
  if (x < 0) {
    sign = -1;
    BOUND += 1;
    x = x * -1;
  }

  // Let just asumming we using unsign number 32-bit here?
  let res = 0;
  while (x > 0) {
    // does 32-bit architect can still handle this comparasion
    if (10 * res > BOUND - (x % 10)) {
      return 0;
    }
    res = x % 10 + 10 * res;
    x = (x - x % 10) / 10;
  }
  return res * sign;
};

console.log(reverseNumberCalculation(12332));
