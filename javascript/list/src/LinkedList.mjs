class Node {
  /**
   * @type {number}
   */
  value;

  /**
   * @type {Node|null}
   */
  next;

  /**
   * @param {number} value value of the created node
   * @param {Node|null} next reference to next node
   */
  constructor(value, next = null) {
    this.value = value;
    this.next = next;
  }
}


class LinkedList {
  /**
   * @type {Node|null}
   */
  head;

  /**
   * @type {Node|null}
   */
  end;

  /**
   * @type {number}
   */
  length;

  constructor() {
    this.length = 0;
    this.head = null;
    this.end = null;
  }

  /**
   * Add a new value into the list
   * @param {number} value added value
   */
  push(value) {
    if (this.end === null) {
      this.head = new Node(value);
      this.end = this.head;
    } else {
      this.end.next = new Node(value);
      this.end = this.end.next;
    }
    this.length += 1;
  }

  /**
   * Get a value in the list, return undefined if list length not reach index
   * @param {number} index Position of elements
   * @returns {number|undefined} Value of elements if haved
   */
  get(index) {
    if (index >= this.length) {
      return undefined;
    }
    let p = this.head;
    for (let i = 0; i < index; i++) {
      if (p !== null)
        p = p.next;
    }
    if (p !== null)
      return p.value;
    return undefined;
  }
}

export default LinkedList;
