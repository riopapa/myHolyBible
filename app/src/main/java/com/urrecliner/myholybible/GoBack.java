package com.urrecliner.myholybible;

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

    int getTopTab() {
        return topTab;
    }
    int getBible() {
        return bible;
    }
    int getChapter() {
        return chapter;
    }
    int getVerse() {
        return verse;
    }
    int getHymn() {
        return hymn;
    }
    String getDic() {
        return dic;
    }
}
