package com.geniusver.util;

import java.util.*;

/**
 * Select item randomly with given probability.
 *
 * The probability is relative probability.
 * For example, we have:
 * Item: A, probability: 201;
 * Item B, probability: 799;
 * The result will be we have 201 / (201 + 799) = 20.1% chance to get item A, 799 / (201 + 799) = 79.9% chance to get
 * item B.
 *
 * Normally, a big probability number(>1000000) will be better, or the result will be less accurate.
 */
public class ProbabilitySelector<T> {
    private final List<T> items = new ArrayList<>();
    private final Map<T, Integer> probabilityMap = new HashMap<>();
    private final Random rand = new Random();
    private int totalSum = 0;
    private boolean inited = false;

    /**
     * Append item and set the relative probability
     * @param item item to select
     * @param probability relative probability
     */
    public void add(T item, Integer probability) {
        items.add(item);
        probabilityMap.put(item, probability);
        inited = false;
    }


    private void init() {
        for(T item : items) {
            totalSum = totalSum + probabilityMap.get(item);
        }
        inited = true;
    }

    /**
     * Random get item according to probability.
     * @return item
     */
    public T getRandom() {
        if (!inited) {
            init();
        }
        int index = rand.nextInt(totalSum);
        int sum = 0;
        int i=0;
        while(sum < index ) {
            sum = sum + probabilityMap.get(items.get(i++));
        }
        return items.get(Math.max(0,i-1));
    }
}
