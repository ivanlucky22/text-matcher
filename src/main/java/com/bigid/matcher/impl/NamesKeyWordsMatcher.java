package com.bigid.matcher.impl;

import com.bigid.matcher.KeyWordsMatcher;
import com.bigid.model.TextEntry;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NamesKeyWordsMatcher implements KeyWordsMatcher {

    @Override
    public MultiValueMap<String, TextEntry> match(final int lineOffset, final List<String> lines, final Pattern pattern) {
        final LinkedMultiValueMap<String, TextEntry> stringTextEntryLinkedMultiValueMap = new LinkedMultiValueMap<>();
        for (int i = 0; i < lines.size(); i++) {
            final Matcher matcher = pattern.matcher(lines.get(i));
            while (matcher.find()) {
                final int charOffset = matcher.start();
                final String keyWord = normalize(matcher.group());
                stringTextEntryLinkedMultiValueMap.add(keyWord, new TextEntry( lineOffset + i + 1, charOffset + 1));
            }
        }
        return stringTextEntryLinkedMultiValueMap;
    }

    /**
     * Source text contains people names in different cases (upper/lower).
     * This methods helps to normalize matcher groups tat found to a single format. Example
     * CHRISTOPHER -> Christopher
     * Christopher -> Christopher
     * and in a final map entries in text for both name will be in single list that corresponds to 'Christopher'
     * @param keyword a found by pattern person's name wic can be in different cases
     * @return person name with first capital character
     */
    private String normalize(final String keyword) {
        final String lowercaseKeyWord = keyword.trim().toLowerCase();
        final String capitalCharacter = lowercaseKeyWord.substring(0, 1).toUpperCase();
        return capitalCharacter + lowercaseKeyWord.substring(1);
    }

}
