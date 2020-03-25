package com.bigid.dsl;

import com.bigid.matcher.KeyWordsMatcher;
import com.bigid.model.TextEntry;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MultiThreadSearchEngine {

    private BufferedReader bufferedReader;
    private int threshold;
    private KeyWordsMatcher keyWordsMatcher;
    private Pattern regexpPattern;

    public MultiThreadSearchEngine(final BufferedReader bufferedReader,
                                   final int threshold,
                                   final KeyWordsMatcher keyWordsMatcher,
                                   final Pattern regexpPattern) {
        this.bufferedReader = bufferedReader;
        this.threshold = threshold;
        this.keyWordsMatcher = keyWordsMatcher;
        this.regexpPattern = regexpPattern;
    }

    public Set<MultiValueMap<String, TextEntry>> getBlockSearchResults() throws IOException {
        String line;
        final LinkedMultiValueMap<Integer, String> blocks = new LinkedMultiValueMap<>();
        final Set<Future<MultiValueMap<String, TextEntry>>> futureResults = new HashSet<>();
        int currentBlockNumber = 0;

        final ExecutorService taskExecutor = Executors.newFixedThreadPool(4);
        while ((line = bufferedReader.readLine()) != null) {
            if (blocks.get(currentBlockNumber) != null && blocks.get(currentBlockNumber).size() == threshold) {
                futureResults.add(startSearchingThread(blocks, currentBlockNumber, taskExecutor));
                currentBlockNumber++;
            }
            blocks.add(currentBlockNumber, line);
        }
        if (blocks.get(currentBlockNumber) != null) { // when EOF reached and unprocessed block left
            futureResults.add(startSearchingThread(blocks, currentBlockNumber, taskExecutor));
        }
        taskExecutor.shutdown();
        return futureResults.stream().map(this::waitResult).collect(Collectors.toSet());
    }

    private Future<MultiValueMap<String, TextEntry>> startSearchingThread(final LinkedMultiValueMap<Integer, String> blocks,
                                                                          final int currentParagraphNumber,
                                                                          final ExecutorService taskExecutor) {
        final int lineOffset = threshold * currentParagraphNumber;
        final List<String> block = blocks.get(currentParagraphNumber);
        return taskExecutor.submit(() -> keyWordsMatcher.match(lineOffset, block, regexpPattern));
    }

    private MultiValueMap<String, TextEntry> waitResult(final Future<MultiValueMap<String, TextEntry>> mapFuture) {
        try {
            return mapFuture.get();
        } catch (Exception e) {
            throw new IllegalStateException("An interruption exception happened while waiting for the child process", e);
        }
    }
}
