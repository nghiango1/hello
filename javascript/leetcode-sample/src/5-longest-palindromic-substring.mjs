/**
 * @param {number[][]} dp
 * @param {string} s
 * @param {number} i
 * @param {number} len
 * @return {boolean}
 */
const isPalidromic = function(dp, s, i, len) {
  if (len <= 1) {
    dp[len][i] = 1;
    return true;
  }

  //console.log("Calculate ( i: ", i, ", len:", len, ") pair, checking it's middle substring", s.slice(i + 1, i + len - 2 + 1));
  if (dp[len % 2][i + 1] > 0) {
    //console.log("Middle substring", s.slice(i + 1, i + len - 2 + 1), "is palidromic");
    if (s[i] == s[i + len - 1]) {
      //console.log("Checking", i, "'th char", s[i], "vs", i + len - 1, "'th char", s[i + len - 1], "of sub string: ", s.slice(i, i + len));
      dp[len % 2][i] = 1;
      //console.log("Substring", s.slice(i, i + len), "is palidromic");
      return true;
    }
  }

  dp[len % 2][i] = -1;
  return false;
};

/** @type {number[][]} */
var dp = [];

/**
 * @param {string} s
 * @return {string}
 */
export var longestPalindrome = function(s) {
  if (dp.length === 0) {
    dp.push([]);
    for (let i = 0; i < s.length; i++)
      dp[0].push(1);

    dp.push([]);
    for (let i = 0; i < s.length; i++)
      dp[1].push(1);
  } else {
    for (let i = 0; i < s.length; i++) {
      dp[0][i] = 1;
      dp[1][i] = 1;
    }
  }

  let resIndex = 0;
  let maxLength = 0;

  for (let len = 1; len <= s.length; len++) {
    for (let i = 0; i < s.length - len + 1; i++) {
      //console.log(len, s.slice(i, i + len));
      const check = isPalidromic(dp, s, i, len);
      if (check && maxLength < len) {
        maxLength = len;
        resIndex = i;
      }
    }
  }

  // for (let i = 0; i < s.length; i++) {
  //   console.log(i, dp[i]);
  // }

  return s.slice(resIndex, resIndex + maxLength);
};

//console.log(longestPalindrome("abadabah"));
