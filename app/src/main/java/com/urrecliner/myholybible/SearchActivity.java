package com.urrecliner.myholybible;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.urrecliner.myholybible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.myholybible.Vars.mContext;
import static com.urrecliner.myholybible.Vars.nbrOfChapters;
import static com.urrecliner.myholybible.Vars.nowBible;
import static com.urrecliner.myholybible.Vars.nowChapter;
import static com.urrecliner.myholybible.Vars.searchActivity;
import static com.urrecliner.myholybible.Vars.searchDepth;
import static com.urrecliner.myholybible.Vars.searchNext;
import static com.urrecliner.myholybible.Vars.searchResults;
import static com.urrecliner.myholybible.Vars.searchText;
import static com.urrecliner.myholybible.Vars.shortBibleNames;
import static com.urrecliner.myholybible.Vars.topTab;
import static com.urrecliner.myholybible.Vars.utils;

public class SearchActivity extends Activity {

    RecyclerView recyclerView;
    TextView tvClearKey, tvFrom, tvSearchKey;
    ImageView ivQuickSearch, ivSearchNext;
//    final String logID = "search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        searchActivity = this;
//        utils.log(logID, "searchNext "+searchNext);

        tvFrom = findViewById(R.id.searchStartVerse);
        String s = shortBibleNames[nowBible]+" "+nowChapter+":1~";
        tvFrom.setText(s);
        searchResults = null;
        tvSearchKey = findViewById(R.id.txtSearch);
        if (searchText != null) {
            tvSearchKey.setText(searchText);
            tvSearchKey.setSelectAllOnFocus(true);
            tvSearchKey.requestFocus();
        }
        View.OnKeyListener keyListenerEnter = (v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_ENTER)       // response to 다음 on keyboard
                searchQuick();
            return false;
        };
        tvSearchKey.setOnKeyListener(keyListenerEnter);

        ivQuickSearch = findViewById(R.id.quickSearch);
        ivQuickSearch.setOnClickListener(v -> searchQuick());

        ivSearchNext = findViewById(R.id.searchNext);
        ivSearchNext.setOnClickListener(v -> {
            if (topTab < TAB_MODE_HYMN) {
                searchText = tvSearchKey.getText().toString();
                search_BibleNext();
                recyclerView = findViewById(R.id.searchedList);
                SearchAdapter searchAdapter = new SearchAdapter();
                recyclerView.setAdapter(searchAdapter);
                tvSearchKey.requestFocus();
            }
        });
        tvClearKey = findViewById(R.id.search_clear);
        tvClearKey.setOnClickListener(v -> {
            tvSearchKey.setText("");
            tvSearchKey.setFocusable(true);
        });
        if (searchNext) {
            searchNext = false;
            search_Bible(searchText);
            recyclerView = findViewById(R.id.searchedList);
            SearchAdapter searchAdapter = new SearchAdapter();
            recyclerView.setAdapter(searchAdapter);
            tvSearchKey.requestFocus();
        }

    }

    void searchQuick() {
        if (topTab < TAB_MODE_HYMN) {
            searchText = tvSearchKey.getText().toString();
            search_Bible(searchText);
            recyclerView = (RecyclerView) findViewById(R.id.searchedList);
            SearchAdapter searchAdapter = new SearchAdapter();
            recyclerView.setAdapter(searchAdapter);
            tvSearchKey.requestFocus();
        }

    }
    String[] bibleVerses;
    void search_Bible(String text) {
        int bible = nowBible;
        int chapter = nowChapter;
        int depth = searchDepth;
        searchResults = new ArrayList<>();
        final String match = "[^\uAC00-\uD7A3xfe/,\\s]"; // 한글만 OK

        while (depth > 0) {
            String file2read = "bible/" + bible + "/" + chapter + ".txt";
            bibleVerses = utils.readBibleFile(file2read);
            for (int i = 0; i < bibleVerses.length; i++) {
                String s = bibleVerses[i].substring(0, bibleVerses[i].indexOf("`"));
                if (s.indexOf('}') > 0)
                    s = s.substring(s.indexOf('}') + 1);
                s = s.replaceAll(match, "");
                if (s.contains(text)) {
                    SearchResult searchResult = new SearchResult(bible, chapter, i+1, s);
                    searchResults.add(searchResult);
                }
            }
            depth--;
            chapter++;
            if (chapter > nbrOfChapters[bible]) {
                bible++;
                if (bible > 66) {
                    depth = 0;
                } else {
                    chapter = 1;
                }
            }
        }
        if (searchResults.size() == 0)
            Toast.makeText(mContext," 검색되지 않습니다. 계속 버튼을 누르거나\n설정에서 검색장수("+searchDepth+")를 늘려보세요.",Toast.LENGTH_LONG).show();
    }

    void search_BibleNext() {
        int depth = searchDepth;
        while (depth > 0) {
            depth--;
            nowChapter++;
            if (nowChapter > nbrOfChapters[nowBible]) {
                nowBible++;
                if (nowBible > 66) {
                    depth = 0;
                } else {
                    nowChapter = 1;
                }
            }
        }
        searchNext = true;
        finish();
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
    }
}