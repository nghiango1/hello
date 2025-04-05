# Boot.dev

Fun course, do it here. I start from chapter 3 and above here (as it not allow summit after free demo in the first 3 chapter)

## Create directory

Getting all the chapter

```js
Array.from(
  document.querySelectorAll(
    "div.text-gray-100:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div > div > a > span",
  ),
).reduce((sum, e) => {
  let text = e.childNodes[1].textContent;
  return (
    sum +
    '\nmkdir "' +
    text.trim().replace(": ", ".").replaceAll(" ", "-").toLowerCase() +
    '"'
  );
}, "#!bin/bash");
```

and lession name

```js
Array.from(
  document.querySelectorAll(
    "div.text-sm:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div > div > a > span",
  ),
).reduce((sum, e) => {
  let text = e.childNodes[1].textContent;
  return (
    sum +
    '\nmkdir "' +
    text.trim().replace(": ", ".").replaceAll(" ", "-").toLowerCase() +
    '"'
  );
}, "#!bin/bash");
```

Example result

```
#!bin/bash
mkdir "1.python-numbers"
mkdir "2.numbers-review"
mkdir "3.floor-division"
mkdir "4.exponents"
mkdir "5.changing-in-place"
mkdir "6.plus-equals"
mkdir "7.scientific-notation"
mkdir "8.logical-operators"
mkdir "9.not"
mkdir "10.binary-numbers"
mkdir "11.binary-numbers"
mkdir "12.binary-numbers"
mkdir "13.bitwise-'&'-operator"
mkdir "14.bitwise-'|'-operator"
mkdir "15.damage-meter"
mkdir "16.converting-binary"
```
