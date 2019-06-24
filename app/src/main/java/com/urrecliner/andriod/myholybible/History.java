package com.urrecliner.andriod.myholybible;

import java.util.ArrayList;

import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_NEW;
import static com.urrecliner.andriod.myholybible.Vars.dictWord;
import static com.urrecliner.andriod.myholybible.Vars.editor;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.nowChapter;
import static com.urrecliner.andriod.myholybible.Vars.nowHymn;
import static com.urrecliner.andriod.myholybible.Vars.nowVerse;
import static com.urrecliner.andriod.myholybible.Vars.stackP;
import static com.urrecliner.andriod.myholybible.Vars.topTab;
import static com.urrecliner.andriod.myholybible.Vars.utils;

class History {

    private ArrayList<Integer> topTabStack, bibleStack, chapterStack, verseStack, hymnStack;
    private ArrayList<String> keyStack;

    void push() {
        if (stackP > 38) {
            for (int i = 0; i < 4; i++) {
                topTabStack.remove(0);
                bibleStack.remove(0);
                chapterStack.remove(0);
                verseStack.remove(0);
                hymnStack.remove(0);
                keyStack.remove(0);
            }
            stackP -= 4;
//            stackMax -= 4;
        }
        topTabStack.add(topTab);
        bibleStack.add(nowBible);
        chapterStack.add(nowChapter);
        verseStack.add(nowVerse);
        hymnStack.add(nowHymn);
        keyStack.add(dictWord);
        stackP++;
//        if (stackP > stackMax)
//            stackMax++;
        dumpStack();
    }
    void pop() {
        if (stackP > 1) {
            stackP--;
            topTab = topTabStack.get(stackP);
            nowBible = bibleStack.get(stackP);
            nowChapter = chapterStack.get(stackP);
            nowVerse = verseStack.get(stackP);
            nowHymn = hymnStack.get(stackP);
            dictWord = keyStack.get(stackP);

            topTabStack.remove(stackP);
            bibleStack.remove(stackP);
            chapterStack.remove(stackP);
            verseStack.remove(stackP);
            hymnStack.remove(stackP);
            keyStack.remove(stackP);
        }
    }
    boolean shift() {
        if (topTabStack.get(stackP+1) > 0) {
            stackP++;
            topTab = topTabStack.get(stackP);
            nowBible = bibleStack.get(stackP);
            nowChapter = chapterStack.get(stackP);
            nowVerse = verseStack.get(stackP);
            nowHymn = hymnStack.get(stackP);
            dictWord = keyStack.get(stackP);
            return true;
        }
        return false;
    }

    void restore() {
        topTabStack = utils.getIntArrayPref("topTabStack");
        bibleStack = utils.getIntArrayPref("bibleStack");
        chapterStack = utils.getIntArrayPref("chapterStack");
        verseStack = utils.getIntArrayPref("verseStack");
        hymnStack = utils.getIntArrayPref("hymnStack");
        keyStack = utils.getStringArrayPref("keyStack");

        stackP = topTabStack.size();
        if (stackP > 0) {
            topTab = topTabStack.get(stackP - 1);
            nowBible = bibleStack.get(stackP - 1);
            nowChapter = chapterStack.get(stackP - 1);
            nowVerse = verseStack.get(stackP - 1);
            nowHymn = hymnStack.get(stackP - 1);
            dictWord = keyStack.get(stackP - 1);
        }
        else {
            topTab = TAB_MODE_NEW;
            nowBible = 0;
            nowChapter = 0;
            nowVerse = 0;
            nowHymn = 0;
            dictWord = "";
        }
        dumpStack();
    }

    void save() {
//        utils.log("saving","stack "+stackP+" "+topTabStack.size());
        dumpStack();
        editor.putInt("stackP", stackP).apply();
        utils.setIntArrayPref("topTabStack", topTabStack);
        utils.setIntArrayPref("bibleStack", bibleStack);
        utils.setIntArrayPref("chapterStack", chapterStack);
        utils.setIntArrayPref("verseStack", verseStack);
        utils.setIntArrayPref("hymnStack", hymnStack);
        utils.setStringArrayPref("keyStack", keyStack);
    }

    void dumpStack() {
//        utils.log("stackP",stackP+"==");
//        for (int i = 0; i < topTabStack.size(); i++) {
//            utils.log("i="+i, topTabStack.get(i) +" "+bibleStack.get(i) +" "+chapterStack.get(i) +" "+verseStack.get(i) +" "+hymnStack.get(i)+" "+keyStack.get(i));
//        }
//        utils.log("dump","topTab "+topTab+" nowBible "+nowBible+" nowChapter "+nowChapter+" nowVerse "+nowVerse+" nowHymn "+nowHymn);
    }
}
