package com.urrecliner.andriod.myholybible;

import static com.urrecliner.andriod.myholybible.Vars.dictWord;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.nowChapter;
import static com.urrecliner.andriod.myholybible.Vars.nowHymn;
import static com.urrecliner.andriod.myholybible.Vars.nowVerse;
import static com.urrecliner.andriod.myholybible.Vars.stackMax;
import static com.urrecliner.andriod.myholybible.Vars.stackP;
import static com.urrecliner.andriod.myholybible.Vars.topTab;

class History {

    private int STACK_SIZE = 40;
    private int [] topTabStack  = new int[STACK_SIZE];
    private int [] bibleStack = new int[STACK_SIZE];
    private int [] chapterStack = new int[STACK_SIZE];
    private int [] verseStack = new int[STACK_SIZE];
    private int [] hymnStack = new int[STACK_SIZE];
    private String [] keyStack = new String[STACK_SIZE];

    void push() {
        if (stackP > 38) {
            for (int i = 0; i < 35; i++) {
                topTabStack[i] = topTabStack[i+4];
                bibleStack[i] = bibleStack[i+4];
                chapterStack[i] = chapterStack[i+4];
                verseStack[i] = verseStack[i+4];
                hymnStack[i] = hymnStack[i+4];
                keyStack[i] = keyStack[i+4];
            }
            stackP -= 4;
            stackMax -= 4;
        }
        topTabStack[stackP] = topTab;
        bibleStack[stackP] = nowBible;
        chapterStack[stackP] = nowChapter;
        verseStack[stackP] = nowVerse;
        hymnStack[stackP] = nowHymn;
        keyStack[stackP] = dictWord;
        stackP++;
        if (stackP > stackMax)
            stackMax++;
    }
    void pop() {
        if (stackP > 0) {
            stackP--;
        }
        topTab = topTabStack[stackP];
        nowBible = bibleStack[stackP];
        nowChapter = chapterStack[stackP];
        nowVerse = verseStack[stackP];
        nowHymn = hymnStack[stackP];
        dictWord = keyStack[stackP];
    }
    boolean shift() {
        if (topTabStack[stackP+1] > 0) {
            stackP++;
            topTab = topTabStack[stackP];
            nowBible = bibleStack[stackP];
            nowChapter = chapterStack[stackP];
            nowVerse = verseStack[stackP];
            nowHymn = hymnStack[stackP];
            dictWord = keyStack[stackP];
            return true;
        }
        return false;
    }

}
