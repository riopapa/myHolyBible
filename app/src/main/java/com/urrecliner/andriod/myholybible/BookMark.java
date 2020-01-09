package com.urrecliner.andriod.myholybible;

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

    public int getBible() { return bible; }
    public void setBible(int bible) { this.bible = bible; }

    public int getChapter() { return chapter; }
    public void setChapter(int chapter) { this.chapter = chapter; }

    public long getWhen() { return when; }
    public void setWhen(long when) { this.when = when; }

    public boolean isSave() { return save; }
    public void setSave(boolean save) { this.save = save; }
}
