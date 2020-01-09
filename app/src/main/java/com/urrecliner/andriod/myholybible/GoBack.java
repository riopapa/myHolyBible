package com.urrecliner.andriod.myholybible;

public class GoBack {
    private int topTab;
    private int bible;
    private int chapter;
    private int verse;
    private int hymn;
    private String dic;

    GoBack(int topTab, int bible, int chapter, int verse, int hymn, String dic) {
        this.topTab = topTab; this.bible = bible; this.chapter = chapter; this.verse = verse; this.hymn = hymn; this.dic = dic;
    }

    public int getTopTab() {
        return topTab;
    }

    public void setTopTab(int topTab) {
        this.topTab = topTab;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getVerse() {
        return verse;
    }

    public void setVerse(int verse) {
        this.verse = verse;
    }

    public int getHymn() {
        return hymn;
    }

    public void setHymn(int hymn) {
        this.hymn = hymn;
    }

    public String getDic() {
        return dic;
    }

    public void setDic(String dic) {
        this.dic = dic;
    }

    public int getBible() {
        return bible;
    }

    public void setBible(int bible) {
        this.bible = bible;
    }
}
