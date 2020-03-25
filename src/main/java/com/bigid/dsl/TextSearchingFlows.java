package com.bigid.dsl;

import java.io.BufferedReader;

/**
 * Builder pattern for creating text searching workflow.
 * Inspired by https://docs.spring.io/spring-integration/api/org/springframework/integration/dsl/IntegrationFlow.html
 * split and aggregate approach
 */
public class TextSearchingFlows {
    public static TextSearchingFlowBuilder from(final BufferedReader bufferedReader) {
        return new TextSearchingFlowBuilder(bufferedReader);
    }
}
