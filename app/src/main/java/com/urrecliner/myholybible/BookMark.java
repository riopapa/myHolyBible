package com.urrecliner.myholybible;

class BookMark {
    private int bible;
    private int chapter;
    private int verse;
    private boolean save;
    private long when;

    BookMark(int bible, int chapter, int verse, long when, boolean save) {
        this.bible = bible;
        this.chapter = chapter;
        this.verse = verse;
        this.when = when;
        this.save = save;
    }

    int getBible() { return bible; }
    int getChapter() { return chapter; }
    int getVerse() { return verse; }
    long getWhen() { return when; }
    boolean isSave() { return save; }
    void setSave(boolean save) { this.save = save; }
}
