import java.util.List;
import java.util.function.Function;

import org.Filter;
import org.FilterStrategy.FilterStrategyIsEven;
import org.FilterStrategy.FilterStrategyIsNegative;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<Integer>(20);
        for (int i = -9; i < 10; i++) {
            arr.add(i);
        }

        Filter ft = new Filter(arr);
        ft.setFilterStrategy(new FilterStrategyIsNegative());
        ft.startFilter();
        System.out.printf("Helper object to filter:\t%s\n", ft.toString());

        List<Integer> filterArr = Filter.filterList(arr, new FilterStrategyIsEven());
        System.out.printf("Using Strategy to filter:\t%s\n", filterArr.toString());

        // Is this strategy enough
        Function<Integer, Boolean> filterLambda = x -> (x % 3 == 0);
        filterArr = Filter.filterList(arr, filterLambda);
        System.out.printf("Using Lambda function to filter:\t%s\n", filterArr.toString());
    }
}
