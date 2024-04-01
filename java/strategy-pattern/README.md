# Strategy

Function as a params?

## The problem

Trendy High order function vs old passion programing pattern

## What this aim for

Java tool chain is really mature, so I want to know what Java development tools/environment give to developer.

## Implement

Not thing much to say about

```java
public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<Integer>(20);
        for (int i = -9; i < 10; i++) {
            arr.add(i);
        }

        // I think I done Builder patern here, not even Strategy
        Filter ft = new Filter(arr);
        ft.setFilterStrategy(new FilterStrategyIsNegative());
        ft.startFilter();
        System.out.printf("Helper object to filter:\t%s\n", ft.toString());

        // This feel better
        List<Integer> filterArr = Filter.filterList(arr, new FilterStrategyIsEven());
        System.out.printf("Using Strategy to filter:\t%s\n", filterArr.toString());

        // Is this strategy enough
        Function<Integer, Boolean> filterLambda = x -> (x % 3 == 0);
        filterArr = Filter.filterList(arr, filterLambda);
        System.out.printf("Using Lambda function to filter:\t%s\n", filterArr.toString());
    }
}
```

I think I was use to pass function as params more than enough in every other language, that when I go back to Java, it feel odd.
