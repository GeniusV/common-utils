package com.geniusver.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GeniusV on 6/28/20.
 */
class ItemProbabilityCounter<T> {
    private final Map<T, Integer> counterMap = new HashMap<>();
    private int total = 0;

    public void count(T item) {
        total++;
        counterMap.put(item, counterMap.getOrDefault(item, 0) + 1);
    }

    public String getItemProbility(T item) {
        return (double) counterMap.get(item) / total * 100 + "%";
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<T, Integer> entry : counterMap.entrySet()) {
            stringBuilder.append(entry.getKey().toString()).append(": ").append(entry.getValue())
                    .append(", probability: ").append((double) entry.getValue() / total * 100).append("%\n");
        }
        stringBuilder.append("total: ").append(total);
        return stringBuilder.toString();
    }

    public int itemCount() {
        return counterMap.size();
    }
}
