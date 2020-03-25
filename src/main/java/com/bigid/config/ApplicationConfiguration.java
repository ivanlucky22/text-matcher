package com.bigid.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.bigid"})
public class ApplicationConfiguration {

    @Value("${breakdownThreshold:10000}")
    private int breakdownThreshold;

    public int getBreakdownThreshold() {
        return breakdownThreshold;
    }

    public void setBreakdownThreshold(final int breakdownThreshold) {
        this.breakdownThreshold = breakdownThreshold;
    }
}
