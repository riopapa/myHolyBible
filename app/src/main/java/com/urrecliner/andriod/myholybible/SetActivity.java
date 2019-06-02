package com.urrecliner.andriod.myholybible;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_NEW;
import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_OLD;
import static com.urrecliner.andriod.myholybible.Vars.alwaysOn;
import static com.urrecliner.andriod.myholybible.Vars.editor;
import static com.urrecliner.andriod.myholybible.Vars.fullBibleNames;
import static com.urrecliner.andriod.myholybible.Vars.hymnImageShow;
import static com.urrecliner.andriod.myholybible.Vars.hymnTextShow;
import static com.urrecliner.andriod.myholybible.Vars.mSettings;
import static com.urrecliner.andriod.myholybible.Vars.makeBible;
import static com.urrecliner.andriod.myholybible.Vars.bookBibles;
import static com.urrecliner.andriod.myholybible.Vars.bookChapters;
import static com.urrecliner.andriod.myholybible.Vars.bookSaves;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.nowChapter;
import static com.urrecliner.andriod.myholybible.Vars.nowHymn;
import static com.urrecliner.andriod.myholybible.Vars.nowVerse;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBible66;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleRefer;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleBody;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnBody;
import static com.urrecliner.andriod.myholybible.Vars.textSizeKeyWord;
import static com.urrecliner.andriod.myholybible.Vars.textSizeSpace;
import static com.urrecliner.andriod.myholybible.Vars.topTab;
import static com.urrecliner.andriod.myholybible.Vars.utils;
import static java.lang.Integer.parseInt;

public class SetActivity extends Activity {

    TextView tv;
    CheckBox cb;
    String txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        buildSetBible();
        buildSetHymn();
        buildSetBookMark();
    }


    private void buildSetBible() {
        tv = (TextView) findViewById(R.id.bibleName_size);
        txt = "" + textSizeBible66;
        tv.setText(txt);
        tv = (TextView) findViewById(R.id.bibleName_size_down);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeBible66--;
                tv = (TextView) findViewById(R.id.bibleName_size);
                String t = "" + textSizeBible66;
                tv.setText(t);
                editor.putInt("textSizeBible66", textSizeBible66).apply();
            }
        });
        tv = (TextView) findViewById(R.id.bibleName_size_up);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeBible66++;
                tv = (TextView) findViewById(R.id.bibleName_size);
                String t = "" + textSizeBible66;
                tv.setText(t);
                editor.putInt("textSizeBible66", textSizeBible66).apply();
            }
        });

        tv = (TextView) findViewById(R.id.scripture_size);
        txt = "" + textSizeBibleBody;
        tv.setText(txt);
        tv = (TextView) findViewById(R.id.scripture_size_down);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeBibleBody--;
                tv = (TextView) findViewById(R.id.scripture_size);
                String t = "" + textSizeBibleBody;
                tv.setText(t);
                tv.setText(t);
                editor.putInt("textSizeBibleBody", textSizeBibleBody).apply();
            }
        });
        tv = (TextView) findViewById(R.id.scripture_size_up);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeBibleBody++;
                tv = (TextView) findViewById(R.id.scripture_size);
                String t = "" + textSizeBibleBody;
                tv.setText(t);
                editor.putInt("textSizeBibleBody", textSizeBibleBody).apply();
            }
        });

        tv = (TextView) findViewById(R.id.keyword_size);
        txt = "" + textSizeKeyWord;
        tv.setText(txt);
        tv = (TextView) findViewById(R.id.keyword_size_down);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeKeyWord--;
                tv = (TextView) findViewById(R.id.keyword_size);
                String t = "" + textSizeKeyWord;
                tv.setText(t);
                editor.putInt("textSizeKeyWord", textSizeKeyWord).apply();
            }
        });
        tv = (TextView) findViewById(R.id.keyword_size_up);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeKeyWord++;
                tv = (TextView) findViewById(R.id.keyword_size);
                String t = "" + textSizeKeyWord;
                tv.setText(t);
                editor.putInt("textSizeKeyWord", textSizeKeyWord).apply();
            }
        });

        tv = (TextView) findViewById(R.id.crossing_size);
        txt = "" + textSizeBibleRefer;
        tv.setText(txt);
        tv = (TextView) findViewById(R.id.crossing_size_down);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeBibleRefer--;
                tv = (TextView) findViewById(R.id.crossing_size);
                String t = "" + textSizeBibleRefer;
                tv.setText(t);
                editor.putInt("textSizeBibleRefer", textSizeBibleRefer).apply();
            }
        });
        tv = (TextView) findViewById(R.id.crossing_size_up);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeBibleRefer++;
                tv = (TextView) findViewById(R.id.crossing_size);
                String t = "" + textSizeBibleRefer;
                tv.setText(t);
                editor.putInt("textSizeBibleRefer", textSizeBibleRefer).apply();
            }
        });

        tv = (TextView) findViewById(R.id.verse_space_size);
        txt = "" + textSizeSpace;
        tv.setText(txt);
        tv = (TextView) findViewById(R.id.verse_space_size_down);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeSpace--;
                tv = (TextView) findViewById(R.id.verse_space_size);
                String t = "" + textSizeSpace;
                tv.setText(t);
                editor.putInt("textSizeSpace", textSizeSpace).apply();
            }
        });
        tv = (TextView) findViewById(R.id.verse_space_size_up);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeSpace++;
                tv = (TextView) findViewById(R.id.verse_space_size);
                String t = "" + textSizeSpace;
                tv.setText(t);
                editor.putInt("textSizeSpace", textSizeSpace).apply();
            }
        });
    }

    private void buildSetHymn() {
        cb = (CheckBox) findViewById(R.id.hymn_sheet_check);
        cb.setChecked(hymnImageShow);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb = (CheckBox) findViewById(R.id.hymn_sheet_check);
                hymnImageShow = cb.isChecked();
                editor.putBoolean("hymnImageShow", hymnImageShow).apply();
            }
        });

        cb = (CheckBox) findViewById(R.id.hymn_lyric_check);
        cb.setChecked(hymnTextShow);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb = (CheckBox) findViewById(R.id.hymn_lyric_check);
                hymnTextShow = cb.isChecked();
                editor.putBoolean("hymnTextShow", hymnTextShow).apply();
                if (hymnTextShow) {
                    View lyric = findViewById(R.id.hymn_lyric_set);
                    lyric.setVisibility(View.VISIBLE);
                } else {
                    View lyric = findViewById(R.id.hymn_lyric_set);
                    lyric.setVisibility(View.INVISIBLE);
                }
            }
        });
        if (hymnTextShow) {
            View lyric = findViewById(R.id.hymn_lyric_set);
            lyric.setVisibility(View.VISIBLE);
        } else {
            View lyric = findViewById(R.id.hymn_lyric_set);
            lyric.setVisibility(View.INVISIBLE);
        }

        tv = (TextView) findViewById(R.id.hymn_lyric_size);
        txt = "" + textSizeHymnBody;
        tv.setText(txt);
        tv = (TextView) findViewById(R.id.hymn_lyric_size_down);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeHymnBody--;
                tv = (TextView) findViewById(R.id.hymn_lyric_size);
                String t = "" + textSizeHymnBody;
                tv.setText(t);
                editor.putInt("textSizeHymnBody", textSizeHymnBody).apply();
            }
        });
        tv = (TextView) findViewById(R.id.hymn_lyric_size_up);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeHymnBody++;
                tv = (TextView) findViewById(R.id.hymn_lyric_size);
                String t = "" + textSizeHymnBody;
                tv.setText(t);
                editor.putInt("textSizeHymnBody", textSizeHymnBody).apply();
            }
        });

        cb = (CheckBox) findViewById(R.id.keep_screen_check);
        cb.setChecked(alwaysOn);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb = (CheckBox) findViewById(R.id.keep_screen_check);
                alwaysOn = cb.isChecked();
                editor.putBoolean("alwaysOn", alwaysOn).apply();
                if (alwaysOn)
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                else
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
    }

    private void buildSetBookMark() {
        final int[] books = new int[]{R.id.bookmark_bible0, R.id.bookmark_bible1, R.id.bookmark_bible2,
                R.id.bookmark_bible3, R.id.bookmark_bible4, R.id.bookmark_bible5};
        final int[] saves = new int[]{R.id.bookmark_check0, R.id.bookmark_check1, R.id.bookmark_check2,
                R.id.bookmark_check3, R.id.bookmark_check4, R.id.bookmark_check5};

        for (int i = 0; i < 6; i++) {
            tv = (TextView) findViewById(books[i]); tv.setVisibility(View.INVISIBLE);
            cb = (CheckBox) findViewById(saves[i]); cb.setVisibility(View.INVISIBLE);
        }
        if (bookBibles.size() > 0) {
            for (int i = 0; i < bookBibles.size(); i++) {
                tv = (TextView) findViewById(books[i]);
                txt = fullBibleNames[parseInt(bookBibles.get(i))] + " " + bookChapters.get(i);
                tv.setText(txt);
                tv.setTag(i);
                tv.setVisibility(View.VISIBLE);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int iTag = (int) v.getTag();
                        nowBible = Integer.parseInt(bookBibles.get(iTag));
                        nowChapter = Integer.parseInt(bookChapters.get(iTag));
                        nowVerse = 0;
                        nowHymn = 0;
                        topTab = (nowBible < 40) ? TAB_MODE_OLD : TAB_MODE_NEW;
                        makeBible.MakeBibleBody();
                    }
                });

                cb = (CheckBox) findViewById(saves[i]);
                cb.setChecked(Boolean.parseBoolean(bookSaves.get(i)));
                cb.setTag(i);
                cb.setVisibility(View.VISIBLE);
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int iTag = (int) v.getTag();
                        cb = (CheckBox) findViewById(saves[iTag]);
                        bookSaves.set(iTag, cb.isChecked() ? "true" : "false");
                        utils.setStringArrayPref("bookSaves", bookSaves);
                    }
                });
            }
        }
    }
}
