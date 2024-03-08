import List from "./List.mjs";

/**
 * Structure repesenting a Linked list node
 */
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


/**
 * A List implement using Single linked array with numberic value
 * @augments {List<number>}
 */
class LinkedList extends List {
  /**
   * The head of the inner linked array
   * @type {Node|null}
   */
  head;

  /**
   * The tail of the inner linked array
   * @private
   * @type {Node|null}
   */
  tail;

  /** Linked list constructor */
  constructor() {
    super();
    this.head = null;
    this.tail = null;
  }

  /**
   * Add a new value into the list
   * @param {number} value Added value
   */
  push(value) {
    if (this.tail === null) {
      this.head = new Node(value);
      this.tail = this.head;
    } else {
      this.tail.next = new Node(value);
      this.tail = this.tail.next;
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
