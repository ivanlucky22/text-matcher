package com.bigid.service;

import com.bigid.model.TextEntry;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.util.List;

public interface TextSearchingService {
    MultiValueMap<String, TextEntry> findKeyWords(File file, List<String> keywords);
}
