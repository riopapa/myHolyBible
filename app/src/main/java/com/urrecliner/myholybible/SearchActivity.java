package com.urrecliner.myholybible;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.urrecliner.myholybible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.myholybible.Vars.editor;
import static com.urrecliner.myholybible.Vars.fullBibleNames;
import static com.urrecliner.myholybible.Vars.nbrOfChapters;
import static com.urrecliner.myholybible.Vars.nowBible;
import static com.urrecliner.myholybible.Vars.nowChapter;
import static com.urrecliner.myholybible.Vars.searchActivity;
import static com.urrecliner.myholybible.Vars.searchDepth;
import static com.urrecliner.myholybible.Vars.searchResults;
import static com.urrecliner.myholybible.Vars.searchText;
import static com.urrecliner.myholybible.Vars.topTab;
import static com.urrecliner.myholybible.Vars.utils;

public class SearchActivity extends Activity {

    RecyclerView recyclerView;
    TextView tv;
    final String logID = "search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        searchActivity = this;
        TextView fromView = (TextView) findViewById(R.id.search_from);
        String s = fullBibleNames[nowBible]+" "+nowChapter+":1 부터";
        fromView.setText(s);
        searchResults = null;
        final TextView textView = (TextView) findViewById(R.id.search_text);
        ImageView imageView = (ImageView) findViewById(R.id.find);
        if (searchText != null)
            textView.setText(searchText);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topTab < TAB_MODE_HYMN) {
                    searchText = textView.getText().toString();
                    search_Bible(searchText);
                    recyclerView = (RecyclerView) findViewById(R.id.searched_list);
                    SearchAdapter searchAdapter = new SearchAdapter();
                    recyclerView.setAdapter(searchAdapter);
                }
            }
        });
        tv = (TextView) findViewById(R.id.search_clear);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv = (TextView) findViewById(R.id.search_text);
                tv.setText("");
            }
        });
        tv = (TextView) findViewById(R.id.search_down);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDepth--;
                tv = (TextView) findViewById(R.id.search_depth_size);
                String txt = "" + searchDepth;
                tv.setText(txt);
                editor.putInt("searchDepth", searchDepth).apply();
            }
        });
        tv = (TextView) findViewById(R.id.search_up);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDepth++;
                tv = (TextView) findViewById(R.id.search_depth_size);
                String txt = "" + searchDepth;
                tv.setText(txt);
                editor.putInt("searchDepth", searchDepth).apply();
            }
        });
        tv = (TextView) findViewById(R.id.search_depth_size);
        String txt = "" + searchDepth;
        tv.setText(txt);
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
    }

}