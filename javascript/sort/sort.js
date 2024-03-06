/**
 * Compare function for sort
 * @param {number} a first number
 * @param {number} b second number
 * @returns {number} compare result
 */
function compareFn(a, b) {
    return a - b;
}

// Binary search
/**
 * Find if `target` in sorted array `arr` or not
 * @param {number[]} arr Sorted array
 * @param {number} target Needed to find number
 * @returns {boolean} true if target in array
 */
function binarySearch(arr, target) {
    let start = -1;
    let end = nums.length;

    let result = false;
    while (start !== end - 1) {
        const middle = start + end;
        const value = arr[middle];

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
    return result;
}

const nums = [1, 2, 3, 4, 5, 1, 1, 1, 3, 2, 1, 3, 4, 1, 1, 4];
const numsCopy = [...nums];

// sort the original array
// (method) Array<number>.sort(compareFn?: ((a: number, b: number) => number) | undefined): number[]
numsCopy.sort(compareFn);
console.log(numsCopy);

const target = 3;
console.log(binarySearch(numsCopy, target));
