#include <stdio.h>

int b = 0;
int n = 0;
int count = 2;

// We return 0 if False, 1 if True, -1 which mean out of ball
// As boolean in C is just 0 for False and 1 for True
int checkBreak(int x){
    if (count == 0)
        return -1;
    count = count - (x >= b);
    return x >= b;
}

// Square root of integer
unsigned int int_sqrt(unsigned int s)
{
	// Zero yields zero
    // One yields one
	if (s <= 1) 
		return s;

    // Initial estimate (must be too high)
	unsigned int x0 = s / 2;

	// Update
	unsigned int x1 = (x0 + s / x0) / 2;

	while (x1 < x0)	// Bound check
	{
		x0 = x1;
		x1 = (x0 + s / x0) / 2;
	}		
	return x0;
}

int solve(int n, int (* cb)(int) ) {
    int step = int_sqrt(n);
    int i = 0;

    for (i = 0; i < n; i += step) {
        if (cb(i)) break;
    }


    for (i = i - step; i < n; i ++) {
        if (cb(i)) break;
    }


    return i;
}

int main() {
    int input_n[] = {53,163,1616,7747};
    int input_b[] = {12,13,1416,247};

    for (int i = 0; i < 4; i++) {
        count = 2;
        n = input_n[i];
        b = input_b[i];
        if (b == solve(n, checkBreak)) {
            printf("Test %d pass!\n", i);
        }
        else {
            count = 2;
            printf("What, %d != %d\n", b, solve(n, checkBreak));
        }
    }
}
