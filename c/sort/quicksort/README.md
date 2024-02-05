# quicksort

This algorithm sort a array in-place is a respectable (on average) `O(n log n)` time complexity. And, it is in a c-lang project structure

## How to use

The source code file is in `src/main.c`

You can build and run the file with this

```sh
# Build the code
make
# Run the program
./myprogram
```

Delete built file and other artifact

```sh
make clean
```

## Notes

I revisiting it after a while, the first implementation of quicksort in my high school is a different variance. It more like this:
- Only have a single function call `quicksort`
- Recursively call `quicksort`

> This almost a instinct at that time, as most I will need to rewrite and modify the search function (for CS contests problem)

```pascal
procedure quicksort(var arr: int8array; l:integer; r: integer);
var
    m, i, j : integer;
    target : integer;
begin
    if l > r then  exit;
    m := (l + r) div 2;
    target := a[m];

    i := l;
    j := r;
    repeat
        while arr[i] < target do i := i + 1;
        while arr[j] > target do j := j - 1;
        if i <= j then begin
            swap(a[i], a[j]);
            i := i + 1;
            j := j - 1;
        end;
    until i > j;
    quicksort(a, l, j);
    quicksort(a, i, r);
end;
```

This project instead have Wiki implementation, which I also assumming that it is the original version:
- Have a seperated `partition` function
- Seem to have less line of implementation

