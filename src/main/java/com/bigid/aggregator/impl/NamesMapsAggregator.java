package com.bigid.aggregator.impl;

import com.bigid.aggregator.KeyWordsAggregator;
import com.bigid.model.TextEntry;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
public class NamesMapsAggregator implements KeyWordsAggregator {

    @Override
    public MultiValueMap<String, TextEntry> aggregate(final MultiValueMap<String, TextEntry> accumulator,
                                                      final MultiValueMap<String, TextEntry> next) {
        if (next != null && !next.isEmpty()) {
            for (String keyword : next.keySet()) {
                final List<TextEntry> textEntries = next.get(keyword);
                accumulator.addAll(keyword, textEntries);
            }
        }
        return accumulator;
    }
}
