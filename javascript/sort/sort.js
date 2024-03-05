const nums = [1, 2, 3, 4, 5, 1, 1, 1, 3, 2, 1, 3, 4, 1, 1, 4];
const numsCopy = [...nums];

// sort the original array
numsCopy.sort((a, b) => a - b);

console.log(numsCopy);

// Binary search
const target = 3;

let start = -1;
let end = nums.length;

let result = false;
while (start !== end - 1) {
    const middle = start + end;
    const value = numsCopy[middle];

    if (value === target) {
        // retrieve index from original array
        result = true;
        break;
    } if (value < target) {
        start = middle;
    } else if (value > target) {
        end = middle;
    }
}

console.log(result);
