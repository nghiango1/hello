package org.FilterStrategy;

/**
 * FilterStrategyIsEven provide filter that check if value is a even number
 */
public final class FilterStrategyIsEven implements FilterStrategy {

    private static boolean isEven(int x) {
        return x % 2 == 0;
    }

    @Override
    public boolean filterFunction(int x) {
        return isEven(x);
    }
}
