package com.bigid.service.impl;

import com.bigid.aggregator.KeyWordsAggregator;
import com.bigid.config.ApplicationConfiguration;
import com.bigid.dsl.TextSearchingFlows;
import com.bigid.matcher.KeyWordsMatcher;
import com.bigid.model.TextEntry;
import com.bigid.service.TextSearchingService;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class TextSearchingServiceImpl implements TextSearchingService {

    private final ApplicationConfiguration applicationConfiguration;
    private final KeyWordsMatcher keyWordsMatcher;
    private final KeyWordsAggregator keyWordsAggregator;

    public TextSearchingServiceImpl(final ApplicationConfiguration applicationConfiguration,
                                    final KeyWordsMatcher keyWordsMatcher,
                                    final KeyWordsAggregator keyWordsAggregator) {
        this.applicationConfiguration = applicationConfiguration;
        this.keyWordsMatcher = keyWordsMatcher;
        this.keyWordsAggregator = keyWordsAggregator;
    }

    public MultiValueMap<String, TextEntry> findKeyWords(final File file, final List<String> keywords) {

        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return TextSearchingFlows.from(bufferedReader)
                    .splitPerLines(applicationConfiguration.getBreakdownThreshold())
                    .matchWith(keyWordsMatcher)
                    .withKeysToSearch(keywords)
                    .aggregate(keyWordsAggregator)
                    .get();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to open the file " + file.getAbsolutePath());
        }
    }
}
