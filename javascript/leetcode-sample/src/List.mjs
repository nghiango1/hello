/* eslint no-unused-vars: off */

/**
 * List interface
 * @interface List
 * @template T
 */
class List {
  /** List alway start at 0 length */
  constructor() {
    this.length = 0;
  }

  /** @type {number} */
  length;

  /**
   * Add a new value into the list
   * @param {T} value added value
   * @returns {void} this function not suppose to return
   */
  push(value) {
    throw new Error("not implemented");
  }
  /**
   * Get a value in the list, return undefined if list length not reach index
   * @param {T} index Position of elements
   * @returns {number|undefined} Value of elements if have
   */
  get(index) {
    throw new Error("not implemented");
  }
}

export default List;
