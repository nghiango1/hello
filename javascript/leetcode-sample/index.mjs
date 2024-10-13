import { ListNode, addTwoNumbers } from "./src/2-add-two-number.mjs";
import { lengthOfLongestSubstring } from "./src/3-longest-substring-without-repeating-characters.mjs";
import { findMedianSortedArrays } from "./src/4-median-of-two-sorted-arrays.mjs";

let test = 1;
test = 4;

if (test === 4) {
  let nums1 = [1, 3];
  let nums2 = [2];
  let res = findMedianSortedArrays(nums1, nums2);
  console.log(res);
  nums1 = [1, 3];
  nums2 = [2, 4];
  res = findMedianSortedArrays(nums1, nums2);
  console.log(res);
  nums1 = [1, 2, 3, 4];
  nums2 = [];
  res = findMedianSortedArrays(nums1, nums2);
  console.log(res);
  nums1 = [];
  nums2 = [1, 2, 3, 4];
  res = findMedianSortedArrays(nums1, nums2);
  console.log(res);
  nums1 = [1, 2, 3];
  nums2 = [];
  res = findMedianSortedArrays(nums1, nums2);
  console.log(res);
  nums1 = [];
  nums2 = [1, 2, 3];
  res = findMedianSortedArrays(nums1, nums2);
  console.log(res);
}

if (test === 2) {
  // 2-add-two-number.js
  const l1 = ListNode.create([2, 4, 3]);
  const l2 = ListNode.create([5, 6, 4]);
  if (l1 && l2) {
    console.log(addTwoNumbers(l1, l2));
  }
}

if (test === 3) {
  // 3-longest-substring-without-repeating-characters.js
  let s = "abcabcbb";
  // Should return 3
  console.log(lengthOfLongestSubstring(s));
  s = "bbbbb";
  // Should return 1
  console.log(lengthOfLongestSubstring(s));
  s = "pwwkew";
  // Should return 3
  console.log(lengthOfLongestSubstring(s));
  s = "abba";
  // Should return 2
  console.log(lengthOfLongestSubstring(s));
}
