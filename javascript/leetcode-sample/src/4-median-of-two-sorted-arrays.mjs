/**
 * Compare function for sort
 * @param {number} a first number
 * @param {number} b second number
 * @returns {number} compare result
 */
function compareFn(a, b) {
    return a - b;
}

/**
 * @param {number[]} nums1
 * @param {number[]} nums2
 * @return {number}
 */
const findTwoMiddlePoint = function(nums1, nums2) {
    const n = nums1.length;
    const m = nums2.length;

    const divM = (m - m % 2) / 2;
    if (n == 0) {
        return (nums2[divM] + nums2[divM - 1]) / 2;
    }

    const divN = (n - n % 2) / 2;
    if (m == 0) {
        return (nums1[divN] + nums1[divN - 1]) / 2;
    }

    return 0;
};

/**
 * @param {number[]} nums1
 * @param {number[]} nums2
 * @return {number}
 */
const findMiddlePoint = function(nums1, nums2) {
    const n = nums1.length;
    const m = nums2.length;

    const divM = (m - m % 2) / 2;
    if (n == 0) {
        return nums2[divM];
    }

    const divN = (n - n % 2) / 2;
    if (m == 0) {
        return nums1[divN];
    }

    return 0;
};

/**
 * @param {number[]} nums1
 * @param {number[]} nums2
 * @return {number}
 */
const skipcombine = function(nums1, nums2) {
    let res = 0;
    const n = nums1.length;
    const m = nums2.length;

    if (n + m === 0) {
        return 0;
    }

    const divInt = (n + m - (n + m) % 2) / 2;
    const modInt = (n + m) % 2;
    if (modInt === 1) {
        // we only need to find the middle point
        res = findMiddlePoint(nums1, nums2);

    } else {
        // we only need to find 2 middle point
        res = findTwoMiddlePoint(nums1, nums2);

    }
    return res;
};

/**
 * Combine two number array then sort them. Final answer will be calculated on new created join and sorted array
 * @param {number[]} nums1
 * @param {number[]} nums2
 * @return {number}
 */
const combine = function(nums1, nums2) {
    let res = 0;
    // This sould cause O(n)
    nums1.push(...nums2);
    // This sould cause O(n log n)
    nums1.sort(compareFn);
    // This sould cause O(1)
    const divInt = (nums1.length - nums1.length % 2) / 2;
    if (nums1.length % 2 === 1) {
        res = nums1[divInt];
    } else {
        res = (nums1[divInt] + nums1[divInt - 1]) / 2.;
    };

    return res;
};


/**
 * @param {number[]} nums1
 * @param {number[]} nums2
 * @return {number}
 */
export var findMedianSortedArrays = function(nums1, nums2) {
    return skipcombine(nums1, nums2);
    return combine(nums1, nums2);
};
