package com.bigid.dsl;

import com.bigid.aggregator.KeyWordsAggregator;
import com.bigid.matcher.KeyWordsMatcher;
import com.bigid.model.TextEntry;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Builder pattern for creating text searching workflow.
 * Inspired by https://docs.spring.io/spring-integration/api/org/springframework/integration/dsl/IntegrationFlow.html
 * split and aggregate approach
 */

public class TextSearchingFlowBuilder {

    private final BufferedReader bufferedReader;
    private int threshold;
    private KeyWordsAggregator keyWordsAggregator;
    private KeyWordsMatcher keyWordsMatcher;
    private Collection<String> keywords;

    public TextSearchingFlowBuilder(final BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public TextSearchingFlowBuilder splitPerLines(final int threshold) {
        this.threshold = threshold;
        return this;
    }

    public TextSearchingFlowBuilder matchWith(final KeyWordsMatcher keyWordsMatcher) {
        this.keyWordsMatcher = keyWordsMatcher;
        return this;
    }

    public TextSearchingFlowBuilder aggregate(final KeyWordsAggregator keyWordsAggregator) {
        this.keyWordsAggregator = keyWordsAggregator;
        return this;
    }

    public TextSearchingFlowBuilder withKeysToSearch(final List<String> keywords) {
        this.keywords = keywords;
        return this;
    }

    public MultiValueMap<String, TextEntry> get() throws IOException {
        Assert.notNull(bufferedReader, "Buffered reader must be provided");
        Assert.notNull(keyWordsMatcher, "Matcher must be provided");
        Assert.notNull(keyWordsAggregator, "An Aggregator must be provided");
        Assert.notNull(keywords, "List of keywords must be provided");
        final MultiThreadSearchEngine multiThreadSearchEngine = new MultiThreadSearchEngine(bufferedReader, threshold,
                keyWordsMatcher, getRegexpPattern());
        return multiThreadSearchEngine.getBlockSearchResults()
                .stream()
                .reduce(new LinkedMultiValueMap<>(),
                        (accumulator, next) -> keyWordsAggregator.aggregate(accumulator, next));
    }

    private Pattern getRegexpPattern() {
        final String patter = String.join(")|(", keywords);
        return Pattern.compile("(" + patter + ")", Pattern.CASE_INSENSITIVE);
    }

}
