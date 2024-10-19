#include <stdint.h>
#include <stdio.h>

// Flip first bit from the mask (which is sign bit), we have the maximum value
// of int: INT32_BOUND_MAX = b011..1
int INT32_BOUND_MAX = (int)~0 ^ (1 << 31);
//  0 = b000..0 (32bit all 0)
// Flip first bit from the mask (which is sign bit), we have the minimum value
// of int: INT32_BOUND_MIN = b100..0
int INT32_BOUND_MIN = (int)0 ^ (1 << 31);

/**
 * @brief This comparation use minus tatic to keep thing within uint32_t bound
 *
 * @param target The number before adding x to the back
 * @param x Will be add to the back of target
 * @param bound Limit bound which needed to be check
 * @return 0 (FALSE) when target..x won't goes above the bound
 * @return 1 (TRUE) when target..x will goes above the bound
 */
int compare(uint32_t target, uint32_t x, uint32_t bound) {
  uint32_t extra = (target % 10 + x) / 10;

  if (target > bound / 10 - extra)
    return 1;

  if (target == bound / 10 - extra) {
    uint32_t lastNumber = (x + target % 10) % 10;
    if (lastNumber > bound % 10)
      return 1;
  }

  return 0;
}

int reverse(int x) {
  uint32_t unsign_x = -0;
  int sign = 1;
  uint32_t bound = INT32_BOUND_MAX;
  // ~0 = b111..1 (32bit all 1)
  if (x < 0) {
    unsign_x = -x;
    sign = -1;
    uint32_t bound = INT32_BOUND_MAX + 1;
  } else {
    unsign_x = x;
  }

  uint32_t res = 0;
  while (unsign_x > 0) {
    if (compare(res, unsign_x % 10, bound))
      return 0;
    res = res * 10 + unsign_x % 10;
    unsign_x = unsign_x / 10;
  }

  return res * sign;
};

int main(int argc, char *argv[]) {
  int x = 1534236469;
  printf("%d\n", reverse(x));
}
