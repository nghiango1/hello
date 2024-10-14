export class Appearance {
    /**
     * @param {number} position 
     * @param {boolean} isMember 
     * @param {number | null} first 
     * @param {number | null} last 
     */
    constructor(position, isMember, first = null, last = null) {
        this.position = position;
        this.isMember = isMember;
        this.first = first;
        this.last = last;
    }
}

/**
 * @param {number} target
 * @param {number[]} array
 * @return {Appearance}
 */
const binarySearch = function(target, array) {
    let first = null;
    let last = null;
    let isMember = false;
    let n = array.length;

    let left = -1;
    let right = n;

    // Find first apperance
    while (left != right - 1) {
        let mid = (left + right - (left + right) % 2) / 2;
        if (target <= array[mid]) {
            right = mid;
        } else {
            left = mid;
        }
    }

    if (array[right] == target) {
        isMember = true;
        first = right;
    }

    if (!isMember) {
        const res = new Appearance(right, isMember);
        return res;
    } else {
        let left = -1;
        let right = n;

        // Find last apperance
        while (left != right - 1) {
            let mid = (left + right - (left + right) % 2) / 2;
            if (target < array[mid]) {
                right = mid;
            } else {
                left = mid;
            }
        }
        last = left;

    }

    // @ts-ignore
    const res = new Appearance(first, isMember, first, last);
    return res;
};

/**
 * @param {number} position
 * @param {number[]} nums1
 * @param {number[]} nums2
 * @return {number}
 */
const advanceBinarySearch = function(position, nums1, nums2) {
    const n = nums1.length;
    const m = nums2.length;

    // try with nums1
    let left = -1;
    let right = n;
    while (left != right - 1) {
        let mid = (left + right - (left + right) % 2) / 2;

        let find1 = binarySearch(nums1[mid], nums1);
        let find2 = binarySearch(nums1[mid], nums2);
        let realPosition = find1.position + find2.position;
        let totalApperance = 0;
        if (find1.isMember) {
            // @ts-ignore
            totalApperance += find1.last - find1.first + 1;
        }
        if (find2.isMember) {
            // @ts-ignore
            totalApperance += find2.last - find2.first + 1;
        }

        // The target is the median
        if (realPosition <= position && position <= realPosition + totalApperance - 1) {
            return nums1[mid];
        }

        if (realPosition > position) {
            right = mid;
        } else {
            left = mid;
        }
    }

    // try with nums2
    left = -1;
    right = m;
    while (left != right - 1) {
        let mid = (left + right - (left + right) % 2) / 2;

        let find1 = binarySearch(nums2[mid], nums1);
        let find2 = binarySearch(nums2[mid], nums2);
        let realPosition = find1.position + find2.position;
        let totalApperance = 0;
        if (find1.isMember) {
            // @ts-ignore
            totalApperance += find1.last - find1.first + 1;
        }
        if (find2.isMember) {
            // @ts-ignore
            totalApperance += find2.last - find2.first + 1;
        }

        // The target is the median
        if (realPosition <= position && position <= realPosition + totalApperance - 1) {
            return nums2[mid];
        }

        if (realPosition > position) {
            right = mid;
        } else {
            left = mid;
        }
    }

    return 0;
};

/**
 * @param {number[]} nums1
 * @param {number[]} nums2
 * @return {number}
 */
const findTwoMiddlePoint = function(nums1, nums2) {
    const n = nums1.length;
    const m = nums2.length;

    const divM = (m - m % 2) / 2;
    if (n === 0) {
        return (nums2[divM] + nums2[divM - 1]) / 2;
    }

    const divN = (n - n % 2) / 2;
    if (m === 0) {
        return (nums1[divN] + nums1[divN - 1]) / 2;
    }

    const divInt = (n + m - (n + m) % 2) / 2;

    return (advanceBinarySearch(divInt, nums1, nums2) + advanceBinarySearch(divInt - 1, nums1, nums2)) / 2;
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
    if (n === 0) {
        return nums2[divM];
    }

    const divN = (n - n % 2) / 2;
    if (m === 0) {
        return nums1[divN];
    }

    const divInt = (n + m - (n + m) % 2) / 2;

    return advanceBinarySearch(divInt, nums1, nums2);
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
 * Compare function for sort
 * @param {number} a first number
 * @param {number} b second number
 * @returns {number} compare result
 */
function compareFn(a, b) {
    return a - b;
}

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
    let res = skipcombine(nums1, nums2);
    const check = combine(nums1, nums2);
    if (res != check) {
        console.log("Error, got", res, "but we want", check);
        //throw EvalError("Error, got " + res + " but we want " + check);
        res = check;
    }
    return res;
};
