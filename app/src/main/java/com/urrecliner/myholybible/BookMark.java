package com.urrecliner.myholybible;

public class BookMark {
    private int bible, chapter;
    private boolean save;
    private long when;

    public BookMark(int bible, int chapter, long when, boolean save) {
        this.bible = bible;
        this.chapter = chapter;
        this.when = when;
        this.save = save;
    }

    int getBible() { return bible; }

    int getChapter() { return chapter; }

    long getWhen() { return when; }

    boolean isSave() { return save; }
    void setSave(boolean save) { this.save = save; }
}
