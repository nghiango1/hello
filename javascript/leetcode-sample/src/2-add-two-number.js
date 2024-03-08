/**
 * Definition for singly-linked list.
 * @class ListNode
 */
class ListNode {
  /**
   * @param {number} val done
   * @param {ListNode|undefined} next done
   */
  constructor(val, next = undefined) {
    this.val = (val === undefined ? 0 : val);
    this.next = (next === undefined ? null : next);
  }

  /**
   * @param {number[]} arr done
   * @returns {ListNode|null} done
   */
  static create(arr) {
    if (arr.length === 0) {
      return null;
    }
    const head = new ListNode(arr[0]);
    let p = head;
    for (let i = 1; i < arr.length; i++) {
      p.next = new ListNode(arr[i]);
      p = p.next;
    }

    return head;
  }
}

/**
 * @param {...any} args done
 * @returns {{divide: number, remain: number}} done
 */
const divide10 = function divideNumberWith10AndGetFullResultAndRemain(...args) {
  let total = 0;
  for (let c of args) {
    if (c) { total += c; }
  }
  const remain = total % 10;
  const divide = (total - remain) / 10;
  return { divide, remain };
};

/* eslint no-unused-vars: off */
/**
 * @param {ListNode} l1 done
 * @param {ListNode} l2 done
 * @returns {ListNode} done
 */
const addTwoNumbers = function AddTwoNumberWithADescriptedName(l1, l2) {
  // You are given two non-empty linked lists so the check alway pass                                                                                                                                         
  if (l1 === null) { return l2; }
  if (l2 === null) { return l1; }

  let p = l1;
  let q = l2;

  const { divide, remain } = divide10(p.val, q.val);
  const sumValue = new ListNode(remain);

  let remember = divide;
  let s = sumValue;
  p = p.next;
  q = q.next;
  while (p !== null) {
    if (q === null) { break; }
    const { divide, remain } = divide10(p.val, q.val, remember);
    remember = divide;

    s.next = new ListNode(remain);
    s = s.next;
    p = p.next;
    q = q.next;
  }

  while (p !== null) {
    const { divide, remain } = divide10(p.val, remember);
    remember = divide;

    s.next = new ListNode(remain);
    s = s.next;
    p = p.next;
  }

  while (q != null) {
    const { divide, remain } = divide10(q.val, remember);
    remember = divide;

    s.next = new ListNode(remain);
    s = s.next;
    q = q.next;
  }

  if (remember != 0) {
    s.next = new ListNode(remember);
  }

  return sumValue;
};

const l1 = ListNode.create([2, 4, 3]);
const l2 = ListNode.create([5, 6, 4]);
if (l1 && l2) {
  console.log(addTwoNumbers(l1, l2));
}
