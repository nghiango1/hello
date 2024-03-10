/**
 * Special data structure to store stage of current largest substring which
 * have different character via add(char) interface. A helper ultility class
 * to slove lengthOfLongestSubstring
 */
class Store {
  /* @private */
  head = 0;
  /* @private */
  tail = 0;
  /* @private */
  length = 0;
  /* @private */
  lastFound;

  /** Constructor */
  constructor() {
    /* @type {Map<string, number>} */
    this.lastFound = new Map();
  }

  /**
   * Adding new character in to the store, and update stage
   * @param {string} char next character
   */
  add(char) {
    if (char.length !== 1) {
      console.log("This is not a character, this call will be skip");
      return;
    }
    const lastFound = this.lastFound.get(char);
    if (lastFound) {
      if (this.head < lastFound) {
        this.head = lastFound;
      }
    }
    this.lastFound.set(char, this.tail + 1);
    this.tail += 1;
    this.length = this.tail - this.head;
  }

  /**
   * Return current length of different character sub-string
   * @returns {number} above
   */
  getLength() {
    return this.length;
  }
}

/**
 * Given a string s, this function find the length of the longest substring without repeating characters
 * @param {string} s given input string
 * @returns {number} longest substring
 */
export var lengthOfLongestSubstring = function aMoreDesciptiveLengthOfLongestSubstr(s) {
  // Odd case special handle
  if (s.length < 2) {
    return s.length;
  }

  let maxLength = 0;
  const store = new Store();
  for (let i = 0; i < s.length; i++) {
    store.add(s[i]);
    if (maxLength < store.getLength()) {
      maxLength = store.getLength();
    }
  }

  return maxLength;
};
