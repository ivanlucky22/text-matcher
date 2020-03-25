package com.bigid.model;

public class TextEntry {
    private int lineOffset;
    private int charOffset;

    public TextEntry(final int lineOffset, final int charOffset) {
        this.lineOffset = lineOffset;
        this.charOffset = charOffset;
    }

    @Override
    public String toString() {
        return "{" +
                "lineOffset=" + lineOffset +
                ", charOffset=" + charOffset +
                '}';
    }
}
