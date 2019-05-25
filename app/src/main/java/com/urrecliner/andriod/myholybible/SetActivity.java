package com.urrecliner.andriod.myholybible;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_NEW;
import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_OLD;
import static com.urrecliner.andriod.myholybible.Vars.editor;
import static com.urrecliner.andriod.myholybible.Vars.fullBibleNames;
import static com.urrecliner.andriod.myholybible.Vars.hymnImageShow;
import static com.urrecliner.andriod.myholybible.Vars.hymnTextShow;
import static com.urrecliner.andriod.myholybible.Vars.makeBible;
import static com.urrecliner.andriod.myholybible.Vars.markBibles;
import static com.urrecliner.andriod.myholybible.Vars.markChapters;
import static com.urrecliner.andriod.myholybible.Vars.markSaves;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.nowChapter;
import static com.urrecliner.andriod.myholybible.Vars.nowHymn;
import static com.urrecliner.andriod.myholybible.Vars.nowVerse;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBible66;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleRefer;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleText;
import static com.urrecliner.andriod.myholybible.Vars.textSizeKeyWord;
import static com.urrecliner.andriod.myholybible.Vars.textSizeSpace;
import static com.urrecliner.andriod.myholybible.Vars.topTab;
import static com.urrecliner.andriod.myholybible.Vars.utils;
import static java.lang.Integer.parseInt;

public class SetActivity extends Activity {

    TextView tv;
    CheckBox cb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        buildSetScreen();
    }

    void buildSetScreen() {
        String t;
        tv = (TextView) findViewById(R.id.bibleName_size); t = ""+textSizeBible66; tv.setText(t);
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
                tv.setText(t); editor.putInt("textSizeBible66", textSizeBible66).apply();
            }
        });

        tv = (TextView) findViewById(R.id.scripture_size); t = ""+textSizeBibleText; tv.setText(t);
        tv = (TextView) findViewById(R.id.scripture_size_down);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeBibleText--;
                tv = (TextView) findViewById(R.id.scripture_size);
                String t = "" + textSizeBibleText;
                tv.setText(t);
                tv.setText(t);
                editor.putInt("textSizeBibleText", textSizeBibleText).apply();
            }
        });
        tv = (TextView) findViewById(R.id.scripture_size_up);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSizeBibleText++;
                tv = (TextView) findViewById(R.id.scripture_size);
                String t = "" + textSizeBibleText;
                tv.setText(t);
                editor.putInt("textSizeBibleText", textSizeBibleText).apply();
            }
        });

        tv = (TextView) findViewById(R.id.keyword_size); t = ""+textSizeKeyWord; tv.setText(t);
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

        tv = (TextView) findViewById(R.id.crossing_size); t = ""+textSizeBibleRefer; tv.setText(t);
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

        tv = (TextView) findViewById(R.id.verse_space_size); t = ""+textSizeSpace; tv.setText(t);
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

        cb = (CheckBox) findViewById(R.id.hymn_image_check);
        cb.setChecked(hymnImageShow);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb = (CheckBox) findViewById(R.id.hymn_image_check);
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
            }
        });

        final int books[] = {R.id.bookmark_bible0, R.id.bookmark_bible1, R.id.bookmark_bible2,
                R.id.bookmark_bible3, R.id.bookmark_bible4, R.id.bookmark_bible5 };
        final int chks[] = {R.id.bookmark_check0, R.id.bookmark_check1, R.id.bookmark_check2,
                R.id.bookmark_check3, R.id.bookmark_check4, R.id.bookmark_check5 };

        for (int i = 0; i < 6; i++) {
            tv = (TextView) findViewById(books[i]);
            t = fullBibleNames[parseInt(markBibles.get(i))]+" "+markChapters.get(i);
            tv.setText(t);
            tv.setTag(i);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = (int) v.getTag();
                    nowBible = Integer.parseInt(markBibles.get(i));
                    nowChapter = Integer.parseInt(markChapters.get(i));
                    nowVerse = 0;
                    nowHymn = 0;
                    topTab = (nowBible < 40) ? TAB_MODE_OLD : TAB_MODE_NEW;
                    makeBible.generateBibleBody();
                }
            });

            cb = (CheckBox) findViewById(chks[i]);
            cb.setChecked(Boolean.parseBoolean(markSaves.get(i)));
            cb.setTag(i);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = (int) v.getTag();
                    cb = (CheckBox) findViewById(chks[i]);
                    markSaves.set(i, cb.isChecked() ? "true":"false");
                    utils.setStringArrayPref("markSaves",markSaves);
                }
            });
        }

        for (int i = 0; i < markBibles.size(); i++) {
            tv = (TextView) findViewById(books[i]);
            t = fullBibleNames[parseInt(markBibles.get(i))]+" "+markChapters.get(i);
            tv.setText(t);
            tv.setTag(i);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = parseInt(v.getTag().toString());
                    nowBible = Integer.parseInt(markBibles.get(i));
                    nowChapter = Integer.parseInt(markChapters.get(i));
                    nowVerse = 0;
                    nowHymn = 0;
                    topTab = (nowBible < 40) ? TAB_MODE_OLD : TAB_MODE_NEW;
                    makeBible.generateBibleBody();
                    finish();
                }
            });

            cb = (CheckBox) findViewById(chks[i]);
            cb.setChecked(Boolean.parseBoolean(markSaves.get(i)));
            cb.setTag(i);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = parseInt(v.getTag().toString());
                    cb = (CheckBox) findViewById(chks[i]);
                    markSaves.set(i, cb.isChecked() ? "true":"false");
                    utils.setStringArrayPref("markSaves",markSaves);
                }
            });
        }
    }

}
