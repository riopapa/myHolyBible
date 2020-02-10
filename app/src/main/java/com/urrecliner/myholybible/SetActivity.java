package com.urrecliner.myholybible;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.urrecliner.myholybible.Vars.LYRIC_ONLY;
import static com.urrecliner.myholybible.Vars.LYRIC_THEN_SHEET;
import static com.urrecliner.myholybible.Vars.SHEET_ONLY;
import static com.urrecliner.myholybible.Vars.SHEET_THEN_LYRIC;
import static com.urrecliner.myholybible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.myholybible.Vars.alwaysOn;
import static com.urrecliner.myholybible.Vars.biblePitch;
import static com.urrecliner.myholybible.Vars.bibleSpeed;
import static com.urrecliner.myholybible.Vars.blackMode;
import static com.urrecliner.myholybible.Vars.bookMarkAdapter;
import static com.urrecliner.myholybible.Vars.bookMarkView;
import static com.urrecliner.myholybible.Vars.editor;
import static com.urrecliner.myholybible.Vars.hymnShowWhat;
import static com.urrecliner.myholybible.Vars.hymnSpeed;
import static com.urrecliner.myholybible.Vars.mContext;
import static com.urrecliner.myholybible.Vars.mainActivity;
import static com.urrecliner.myholybible.Vars.makeBible;
import static com.urrecliner.myholybible.Vars.makeHymn;
import static com.urrecliner.myholybible.Vars.nowBible;
import static com.urrecliner.myholybible.Vars.nowHymn;
import static com.urrecliner.myholybible.Vars.setActivity;
import static com.urrecliner.myholybible.Vars.textSizeBible66;
import static com.urrecliner.myholybible.Vars.textSizeBibleBody;
import static com.urrecliner.myholybible.Vars.textSizeBibleRefer;
import static com.urrecliner.myholybible.Vars.textSizeHymnBody;
import static com.urrecliner.myholybible.Vars.textSizeKeyWord;
import static com.urrecliner.myholybible.Vars.textSizeSpace;
import static com.urrecliner.myholybible.Vars.topTab;
import static com.urrecliner.myholybible.Vars.utils;

public class SetActivity extends Activity {

    TextView tv;
    CheckBox cbAlwaysOn, cbBlackMode;
    String txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setActivity = this;
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

        tv = (TextView) findViewById(R.id.build_time);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
                TableRow tr;
                TableRow.LayoutParams params;
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                ArrayList<String> histories = utils.readRawTextFile(mContext, R.raw.history);
                for (String s: histories) {
                    String [] oneLines = s.split(";");
                    tr = new TableRow(mContext);
                    LinearLayout oneLine = (LinearLayout) inflater.inflate(
                            R.layout.history_table, null);
                    TextView tv = (TextView) oneLine.findViewById(R.id.date);
                    if (oneLines[1].equals("b"))
                        tv.setTypeface(null, Typeface.BOLD);
                    tv.setText(oneLines[0]);
                    tv = (TextView) oneLine.findViewById(R.id.updates);
                    if (oneLines[1].equals("b"))
                        tv.setTypeface(null, Typeface.BOLD);
                    tv.setText(oneLines[2].replace("||", "\n"));
                    params = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT, 1f);
                    tr.addView(oneLine, params);
                    tableLayout.addView(tr);
                }
                final ScrollView sv = (ScrollView) findViewById(R.id.setScrollView);
                sv.post(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0, 500);
                    }
                });
            }
        });
    }

    // 0.6f < bibleSpeed < (0.6 + 0.8) f     <==  0 <seekBar < 8
    private void buildSetBibleRead() {
        int progress;
        final SeekBar speedBar = (SeekBar) findViewById(R.id.bibleSpeed);
        progress = (int) (bibleSpeed * 10) - 6;
        speedBar.setProgress(progress);
        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = seekBar.getProgress();
                bibleSpeed =  (float) (progress+8) / 10f;
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
        progress = (int) (biblePitch * 10) - 6;
        pitchBar.setProgress(progress);
        pitchBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = seekBar.getProgress();
                biblePitch =  (float) (progress+6) / 10f;
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
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton radioBtn;
        switch(hymnShowWhat) {
            case SHEET_THEN_LYRIC:
                radioBtn = (RadioButton) findViewById(R.id.sheet_lyric); radioBtn.setChecked(true); break;
            case LYRIC_THEN_SHEET:
                radioBtn = (RadioButton) findViewById(R.id.lyric_sheet); radioBtn.setChecked(true); break;
            case SHEET_ONLY:
                radioBtn = (RadioButton) findViewById(R.id.sheet_only); radioBtn.setChecked(true); break;
            case LYRIC_ONLY:
                radioBtn = (RadioButton) findViewById(R.id.lyric_only); radioBtn.setChecked(true); break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.sheet_lyric:
                        hymnShowWhat = SHEET_THEN_LYRIC; break;
                    case R.id.lyric_sheet:
                        hymnShowWhat = LYRIC_THEN_SHEET; break;
                    case R.id.sheet_only:
                        hymnShowWhat = SHEET_ONLY; break;
                    case R.id.lyric_only:
                        hymnShowWhat = LYRIC_ONLY; break;
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

        cbAlwaysOn = (CheckBox) findViewById(R.id.keep_screen_check);
        cbAlwaysOn.setChecked(alwaysOn);
        cbAlwaysOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alwaysOn = cbAlwaysOn.isChecked();
                editor.putBoolean("alwaysOn", alwaysOn).apply();

                if (alwaysOn)
                    mainActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                else
                    mainActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });

        cbBlackMode = (CheckBox) findViewById(R.id.black_back);
        cbBlackMode.setChecked(blackMode);
        cbBlackMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blackMode = cbBlackMode.isChecked();
                editor.putBoolean("blackMode", blackMode).apply();
                mainActivity.setColors();
            }
        });
    }

    // 0.6f < bibleSpeed < (0.6 + 0.8) f     <==  0 <seekBar < 8
    private void buildSetPlayHymn() {
        int progress;
        final SeekBar speedBar = (SeekBar) findViewById(R.id.hymnSpeed);
        progress = (int) (hymnSpeed * 10) - 6;
        speedBar.setProgress(progress);
        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = seekBar.getProgress();
                hymnSpeed =  (float) (progress+6) / 10f;
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
        bookMarkView = (RecyclerView) findViewById(R.id.book_marks);
        bookMarkAdapter = new BookMarkAdapter();
        bookMarkView.setAdapter(bookMarkAdapter);
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
                makeBible.makeBibleBody();
            else
                makeBible.showBibleList();
        }
        else if (topTab == TAB_MODE_HYMN) {
            if (makeHymn == null)
                makeHymn = new MakeHymn();
            if (nowHymn <= 0)
                makeHymn.makeHymnKeypad();
            else
                makeHymn.makeHymnBody();
        }
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }
}
