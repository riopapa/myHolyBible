package com.urrecliner.myholybible;

import static com.urrecliner.myholybible.Vars.TAB_MODE_NEW;
import static com.urrecliner.myholybible.Vars.goBacks;
import static com.urrecliner.myholybible.Vars.nowBible;
import static com.urrecliner.myholybible.Vars.nowChapter;
import static com.urrecliner.myholybible.Vars.nowDic;
import static com.urrecliner.myholybible.Vars.nowHymn;
import static com.urrecliner.myholybible.Vars.nowVerse;
import static com.urrecliner.myholybible.Vars.topTab;

class History {

    void push() {
        if (goBacks.size() > 40) {
            goBacks.remove(0);
            goBacks.remove(0);
            goBacks.remove(0);
            goBacks.remove(0);
        }
        GoBack goBack = new GoBack(topTab,nowBible, nowChapter, nowVerse, nowHymn, nowDic);
        goBacks.add(goBack);
    }
    void pop() {
        if (goBacks.size() > 1) {
            GoBack goBack = goBacks.get(goBacks.size()-1);
            topTab = goBack.getTopTab();
            nowBible = goBack.getBible();
            nowChapter = goBack.getChapter();
            nowVerse = goBack.getVerse();
            nowHymn = goBack.getHymn();
            nowDic = goBack.getDic();
            goBacks.remove(goBacks.size()-1);
        }
    }

    void init() {
        topTab = TAB_MODE_NEW;
        nowBible = 0;
        nowChapter = 0;
        nowVerse = 0;
        nowHymn = 0;
        nowDic = "";
    }
}
