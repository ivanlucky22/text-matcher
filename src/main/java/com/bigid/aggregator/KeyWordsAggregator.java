package com.bigid.aggregator;

import com.bigid.model.TextEntry;
import org.springframework.util.MultiValueMap;

public interface KeyWordsAggregator {
    MultiValueMap<String, TextEntry> aggregate(MultiValueMap<String, TextEntry> accumulator,
                                               MultiValueMap<String, TextEntry> next);
}
