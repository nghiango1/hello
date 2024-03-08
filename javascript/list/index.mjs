import LinkedList from "./src/LinkedList.mjs";
import List from "./src/List.mjs";

const ref = new LinkedList();

/* @type {List<number>} */
let s = new List();

s = ref;

console.log(s);
console.log(s instanceof List);
console.log(s instanceof LinkedList);
s.push(1);
console.log(s);
s.push(2);
console.log(s);
s.push(3);
console.log(s);
console.log(s.get(0));
console.log(s.get(1));
console.log(s.get(2));
console.log(s.get(3));
