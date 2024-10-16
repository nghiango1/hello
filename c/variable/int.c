#include <stdint.h>
#include <stdio.h>

// Flip first bit from the mask (which is sign bit), we have the maximum value
// of int: INT32_BOUND_MAX = b011..1
int INT32_BOUND_MAX = (int)~0 ^ (1 << 31);
//  0 = b000..0 (32bit all 0)
// Flip first bit from the mask (which is sign bit), we have the minimum value
// of int: INT32_BOUND_MIN = b100..0
int INT32_BOUND_MIN = (int)0 ^ (1 << 31);

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
    if (res * 10 > bound - unsign_x % 10)
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
