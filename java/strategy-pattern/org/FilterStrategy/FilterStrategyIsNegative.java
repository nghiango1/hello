package org.FilterStrategy;

/**
 * FilterStrategyIsEven provide filter that check if value is a negative number
 */
public final class FilterStrategyIsNegative implements FilterStrategy {
    private static boolean isNegative(int x) {
        return x < 0;
    }

    @Override
    public boolean filterFunction(int x) {
        return isNegative(x);
    }
}
