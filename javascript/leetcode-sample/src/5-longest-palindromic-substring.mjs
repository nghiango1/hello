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
const betterLongestPalindrome = function(s) {
  let resIndex = 0;
  let maxLength = 0;

  for (let i = 0; i < s.length; i++) {
    for (let len = 1; (i - len) >= 0 && (i + len) <= s.length; len++) {
      //console.log(i, len, s.slice(i - len, i + len));
      if (s[i - len] == s[i + len - 1]) {
        //console.log(i, len, s.slice(i - len, i + len), "is a palidromic string");
        const realLength = 2 * len;
        if (maxLength < realLength) {
          maxLength = realLength;
          resIndex = i;
        }
      } else {
        break;
      }
    }
  }

  for (let i = 1; i < s.length; i++) {
    for (let len = 1; (i - len - 1) >= 0 && (i + len) <= s.length; len++) {
      //console.log(i, len, s.slice(i - len - 1, i + len));
      if (s[i - len - 1] == s[i + len - 1]) {
        //console.log(i, len, s.slice(i - len - 1, i + len), "is a palidromic string");
        const realLength = 2 * len + 1;
        if (maxLength < realLength) {
          maxLength = realLength;
          resIndex = i;
        }
      } else {
        break;
      }
    }
  }

  // Edge case
  if (maxLength === 0) {
    return s[0];
  }

  if (maxLength % 2 === 0) {
    return s.slice(resIndex - maxLength / 2, resIndex + maxLength / 2);
  } else {
    maxLength -= 1;
    return s.slice(resIndex - maxLength / 2 - 1, resIndex + maxLength / 2);
  }
};

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
  console.log(betterLongestPalindrome(s));
  return s.slice(resIndex, resIndex + maxLength);
};

//console.log(betterLongestPalindrome("abadabah"));
