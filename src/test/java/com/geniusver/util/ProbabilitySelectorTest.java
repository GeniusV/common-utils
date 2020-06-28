package com.geniusver.util;

import org.junit.jupiter.api.Test;

/**
 * Created by GeniusV on 6/28/20.
 */
class ProbabilitySelectorTest {
    public static final int PROBABILITY_MULTIPLIER = 1000000;

    @Test
    public void testProbabilitySelector() {
        ProbabilitySelector<String> probabilitySelector = new ProbabilitySelector<>();
        // 80% chance to get R
        probabilitySelector.add("R", 80 * PROBABILITY_MULTIPLIER);
        // 19% chance to get SR
        probabilitySelector.add("SR", 19 * PROBABILITY_MULTIPLIER);
        // 1% chance to get SRR
        probabilitySelector.add("SSR", 1 * PROBABILITY_MULTIPLIER);

        int totalCount = 100000;
        ItemProbabilityCounter<String> itemCounter = new ItemProbabilityCounter<>();
        for (int i = 0; i < totalCount; i++) {
            String str = probabilitySelector.getRandom();
            itemCounter.count(str);
        }
        System.out.println(itemCounter.toString());
    }


}