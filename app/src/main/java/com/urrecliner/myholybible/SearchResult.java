package com.urrecliner.myholybible;

class SearchResult {
    private int bible, chapter, verse;
    private String text;

    SearchResult(int bible, int chapter, int verse, String text) {
        this.bible = bible;
        this.chapter = chapter;
        this.verse = verse;
        this.text = text;
    }

    int getBible() { return bible; }
    int getChapter() { return chapter; }
    int getVerse() { return verse; }
    String getText() { return text; }
}
