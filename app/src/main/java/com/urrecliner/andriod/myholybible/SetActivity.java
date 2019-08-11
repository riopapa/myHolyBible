package com.urrecliner.andriod.myholybible;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_NEW;
import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_OLD;
import static com.urrecliner.andriod.myholybible.Vars.alwaysOn;
import static com.urrecliner.andriod.myholybible.Vars.biblePitch;
import static com.urrecliner.andriod.myholybible.Vars.bibleSpeed;
import static com.urrecliner.andriod.myholybible.Vars.bookBibles;
import static com.urrecliner.andriod.myholybible.Vars.bookChapters;
import static com.urrecliner.andriod.myholybible.Vars.bookSaves;
import static com.urrecliner.andriod.myholybible.Vars.editor;
import static com.urrecliner.andriod.myholybible.Vars.fullBibleNames;
import static com.urrecliner.andriod.myholybible.Vars.hymnShowWhat;
import static com.urrecliner.andriod.myholybible.Vars.hymnSpeed;
import static com.urrecliner.andriod.myholybible.Vars.mainActivity;
import static com.urrecliner.andriod.myholybible.Vars.makeBible;
import static com.urrecliner.andriod.myholybible.Vars.makeHymn;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.nowChapter;
import static com.urrecliner.andriod.myholybible.Vars.nowHymn;
import static com.urrecliner.andriod.myholybible.Vars.nowVerse;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBible66;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleBody;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleRefer;
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
        buildSetBibleRead();
        buildSetHymn();
        buildSetPlayHymn();
        buildSetBookMark();
        buildShowAuthor();
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

    // 0.7f < bibleSpeed < (0.7 + 0.6) f     <==  0 <seekBar < 6
    private void buildSetBibleRead() {
        int progress;
        final SeekBar speedBar = (SeekBar) findViewById(R.id.bibleSpeed);
        progress = (int) (bibleSpeed * 10) - 7;
        speedBar.setProgress(progress);
        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = seekBar.getProgress();
                bibleSpeed =  (float) (progress+7) / 10f;
                editor.putFloat("bibleSpeed", bibleSpeed).apply();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        final SeekBar pitchBar = (SeekBar) findViewById(R.id.biblePitch);
        progress = (int) (biblePitch * 10) - 7;
        pitchBar.setProgress(progress);
        pitchBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = seekBar.getProgress();
                biblePitch =  (float) (progress+7) / 10f;
                editor.putFloat("biblePitch", biblePitch).apply();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void buildSetHymn() {
        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb;
        switch(hymnShowWhat) {
            case 0:
                rb = (RadioButton) findViewById(R.id.sheet_lyric); rb.setChecked(true); break;
            case 1:
                rb = (RadioButton) findViewById(R.id.lyric_sheet); rb.setChecked(true); break;
            case 2:
                rb = (RadioButton) findViewById(R.id.sheet_only); rb.setChecked(true); break;
            case 3:
                rb = (RadioButton) findViewById(R.id.lyric_only); rb.setChecked(true); break;
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.sheet_lyric:
                        hymnShowWhat = 0; break;
                    case R.id.lyric_sheet:
                        hymnShowWhat = 1; break;
                    case R.id.sheet_only:
                        hymnShowWhat = 2; break;
                    case R.id.lyric_only:
                        hymnShowWhat = 3; break;
                }
                editor.putInt("hymnShowWhat", hymnShowWhat).apply();
            }
        });

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
                    mainActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                else
                    mainActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
    }


    // 0.7f < bibleSpeed < (0.7 + 0.6) f     <==  0 <seekBar < 6
    private void buildSetPlayHymn() {
        int progress;
        final SeekBar speedBar = (SeekBar) findViewById(R.id.hymnSpeed);
        progress = (int) (hymnSpeed * 10) - 7;
        speedBar.setProgress(progress);
        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = seekBar.getProgress();
                hymnSpeed =  (float) (progress+7) / 10f;
                editor.putFloat("hymnSpeed", hymnSpeed).apply();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
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
                        if (makeBible == null)
                            makeBible = new MakeBible();
                        makeBible.MakeBibleBody();
                        finish();
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
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

    private void buildShowAuthor() {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String build_time = "제작 : "+dateTimeFormat.format(BuildConfig.BUILD_TIME)+", 하원철";
        tv = (TextView) findViewById(R.id.build_time);
        tv.setText(build_time);
    }

    @Override
    public void onBackPressed() {

        if (topTab < TAB_MODE_HYMN) {
            if (nowBible != 0)
                makeBible.MakeBibleBody();
            else
                makeBible.showBibleList();
        }
        else if (topTab == TAB_MODE_HYMN) {
            if (makeHymn == null)
                makeHymn = new MakeHymn();
            if (nowHymn == 0)
                makeHymn.makeHymnKeypad();
            else
                makeHymn.makeHymnBody();
        }
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }
}
