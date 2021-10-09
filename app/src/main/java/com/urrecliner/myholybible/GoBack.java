package com.urrecliner.myholybible;

public class GoBack {
    private final int topTab;
    private final int bible;
    private final int chapter;
    private final int verse;
    private final int hymn;
    private final String dic;

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