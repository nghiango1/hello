import { ListNode, addTwoNumbers } from "./src/2-add-two-number.mjs";
import { lengthOfLongestSubstring } from "./src/3-longest-substring-without-repeating-characters.mjs";

{
  // 2-add-two-number.js
  const l1 = ListNode.create([2, 4, 3]);
  const l2 = ListNode.create([5, 6, 4]);
  if (l1 && l2) {
    console.log(addTwoNumbers(l1, l2));
  }
}

{
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
