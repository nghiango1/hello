package org;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.FilterStrategy.FilterStrategy;
import org.FilterStrategy.FilterStrategyIsEven;

/**
 * Filter is a helper class to filter a List&lt;Integer&gt;.
 * You can use the helper function directy with:
 * 
 * <pre>
 * filterList = Filter.filterList(listInstance, filterStrategyInstance);
 * </pre>
 *
 * or by using filerInstancedirectly with:
 * 
 * <pre>
 * Filter ft = new Filter(listInstance);
 * ft.setFilterStrategy(filterStrategyInstance);
 * ft.startFilter();
 * filterList = ft.getFilterList();
 * </pre>
 */
public class Filter {
    private FilterStrategy _innerFilterStrategy;

    private List<Integer> _innerList;

    /**
     * Filter constructor to creater Filter Instance
     * You can filter a list multiple time by using Filter object as a helper.
     * It manage provided list as a internal value, and update it everytime
     * we run a filter strategy.
     *
     * Default strategy is set as a new FilterStrategyIsEven instance
     * 
     * @param list   List instance that need to be filter (shadow clone)
     * @param filStr FilterStrategy will be use
     */
    public Filter(List<Integer> list, FilterStrategy filStr) {
        this._innerList = list;
        this._innerFilterStrategy = filStr;
    }

    /**
     * Filter constructor to creater Filter Instance
     * You can filter a list multiple time by using Filter object as a helper.
     * It manage provided list as a internal value, and update it everytime
     * we run a filter strategy.
     *
     * Default strategy is set as a new FilterStrategyIsEven instance
     * 
     * @param list List instance that need to be filter (shadow clone)
     */
    public Filter(List<Integer> list) {
        this._innerList = list;
        this._innerFilterStrategy = new FilterStrategyIsEven();
    }

    public void setFilterStrategy(FilterStrategy filStr) {
        this._innerFilterStrategy = filStr;
    }

    /**
     * Apply internal filter strategy to internal list
     */
    public void startFilter() {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (Integer val : this._innerList) {
            if (this._innerFilterStrategy.filterFunction(val)) {
                temp.add(val);
            }
        }
        this._innerList = temp;
    }

    /**
     * Apply a filter strategy to internal list
     */
    public void applyFilter(FilterStrategy filStr) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (Integer val : this._innerList) {
            if (filStr.filterFunction(val)) {
                temp.add(val);
            }
        }
        this._innerList = temp;
    }

    public List<Integer> getInternalFilterList() {
        return this._innerList;
    }

    public String toString() {
        return this._innerList.toString();
    }

    /**
     * Helper filter function that use a filter strategy to filter a list
     *
     * @param list   List instance that need to be filter (will be shadow clone)
     * @param filStr Filter strategy that you want to aplly
     * @return a new List instance that have all filter value
     */
    public static List<Integer> filterList(List<Integer> list, FilterStrategy filStr) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (Integer val : list) {
            if (filStr.filterFunction(val)) {
                temp.add(val);
            }
        }
        return temp;
    }

    /**
     * Helper filter function that hava a function param to filter a list.
     * I can't found passing function directly yet though, but lambda is availabe
     *
     * @param list   List instance that need to be filter (will be shadow clone)
     * @param filStr filter function that you want to aplly
     * @return a new List instance that have all filter value
     */
    public static List<Integer> filterList(List<Integer> list, Function<Integer, Boolean> filterFunction) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (Integer val : list) {
            if (filterFunction.apply(val)) {
                temp.add(val);
            }
        }
        return temp;
    }
}
