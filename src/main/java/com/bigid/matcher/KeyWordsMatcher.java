package com.bigid.matcher;

import com.bigid.model.TextEntry;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.regex.Pattern;

public interface KeyWordsMatcher {
    MultiValueMap<String, TextEntry> match(int lineOffset, List<String> lines, Pattern pattern);
}
