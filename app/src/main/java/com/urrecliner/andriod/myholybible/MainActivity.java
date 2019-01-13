package com.urrecliner.andriod.myholybible;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static com.urrecliner.andriod.myholybible.Vars.TABMODE_HYMN;
import static com.urrecliner.andriod.myholybible.Vars.fromSubFunction;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.topTab;
import static com.urrecliner.andriod.myholybible.Vars.utils;


public class MainActivity extends Activity {

    TextView vMenu, vOldBible, vNewBible, vHymn, vGoBack;
    TextView vAgpBible, vLeftAction, vCurrBible, vRightAction, vCevBible;
    long backKeyPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vars.mContext = getApplicationContext();
        Vars.mActivity = this;
        Log.w("main", "started");

        setContentView(R.layout.activity_main);

        initializeVariables();

        Vars.packageFolder = new File(Environment.getExternalStorageDirectory(), "myHolyBible");
        Vars.topTab = Vars.TABMODE_NEW;
        Vars.nowBible = 0;
        Vars.nowChapter = 0;
        Vars.nowVerse = 0;
        Vars.nowHymn = 0;

        makeTopMenu();
        makeBottomMenu();
        makeBibleList();
        assignButtonListeners();
    }

    private void initializeVariables() {
        Vars.mContainerBody = findViewById(R.id.fragment_body);
        vMenu = findViewById(R.id.menu);
        vOldBible = findViewById(R.id.oldBible);
        vNewBible = findViewById(R.id.newBible);
        vHymn = findViewById(R.id.hymn);
        vGoBack = findViewById(R.id.goBack);
        vAgpBible = findViewById(R.id.agpBible);
        vLeftAction = findViewById(R.id.leftAction);
        vCurrBible = findViewById(R.id.currBible);
        vRightAction = findViewById(R.id.rightAction);
        vCevBible = findViewById(R.id.cevBible);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) Vars.mContext.getSystemService(WINDOW_SERVICE);
        try {
            windowManager.getDefaultDisplay().getMetrics(dm);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Vars.xPixels = dm.widthPixels;
        Vars.yPixels = dm.heightPixels;
    }

    public void makeTopMenu() {
        int highLight = getResources().getColor(R.color.MenuHighLight);
        int normal;
//        if (vMenu.getBackground() instanceof ColorDrawable) {
//            ColorDrawable cd =  vMenu.getCurrentTextColor();
            normal = vMenu.getCurrentTextColor();
//        }
        if (Vars.topTab == Vars.TABMODE_OLD)
            vOldBible.setTextColor(highLight);
        else
            vOldBible.setTextColor(normal);
        if (Vars.topTab == Vars.TABMODE_NEW)
            vNewBible.setTextColor(highLight);
        else
            vNewBible.setTextColor(normal);
        if (Vars.topTab == Vars.TABMODE_HYMN)
            vHymn.setTextColor(highLight);
        else
            vHymn.setTextColor(normal);
    }

    public void makeBottomMenu() {

        if (Vars.topTab < 4)
            makeBibleBottomMenu();
        else if (Vars.topTab == Vars.TABMODE_HYMN)
            makeHymnBottomMenu();
        else
            clearBottomMenu();
    }

    public void makeBibleBottomMenu() {
        Log.w("makeBibleBottomMenu", nowBible + " nowbible is " + Vars.shortBibleNames[nowBible]);
        if (nowBible == 0)
            makeBibleBottomClear();
        else
            makeBibleBottomNormal();
    }

    public void makeBibleBottomClear() {
        Log.w("makeBibleBottomClear", "nowbible" + nowBible);
        vAgpBible.setText(Vars.blank);
        vLeftAction.setText((Vars.blank));
        String text;
        if (Vars.topTab == Vars.TABMODE_OLD)
            text = Vars.oldName;
        else if (Vars.topTab == Vars.TABMODE_NEW)
            text = Vars.newName;
        else if (Vars.topTab == Vars.TABMODE_HYMN)
            text = Vars.hymnName;
        else
            text = Vars.blank;
        vCurrBible.setText(text);
        vRightAction.setText((Vars.blank));
        vCevBible.setText(Vars.blank);
    }

    public void makeBibleBottomNormal() {
        Log.w("makeBibleBottom normal", "nowbible" + nowBible);
        String txt;
        if (Vars.nowChapter==0) {
            vAgpBible.setText(Vars.blank);
            vLeftAction.setText(Vars.blank);
            vCurrBible.setText(Vars.fullBibleNames[nowBible]);
            vRightAction.setText(Vars.blank);
            vCevBible.setText(Vars.blank);
        }
        else {
            vAgpBible.setText((Vars.agpShow) ? "agp" : "AGP");
            int chapter = Vars.nowChapter - 1;
            if (chapter > 0)
                txt = Vars.shortBibleNames[nowBible] + chapter;
            else {
                int prev = nowBible - 1;
                if (prev > 0)
                    txt = Vars.shortBibleNames[prev] + Vars.nbrofChapters[prev];
                else
                    txt = Vars.blank;
            }
            vLeftAction.setText(txt);
            txt = Vars.fullBibleNames[nowBible] + Vars.nowChapter;
            vCurrBible.setText(txt);
            chapter = Vars.nowChapter + 1;
            if (chapter <= Vars.nbrofChapters[nowBible])
                txt = Vars.shortBibleNames[nowBible] + chapter;
            else {
                int next = nowBible + 1;
                if (next > 66)
                    txt = Vars.blank;
                else
                    txt = Vars.shortBibleNames[next] + "1";
            }
            vRightAction.setText(txt);
            vCevBible.setText((Vars.cevShow) ? "cev" : "CEV");
        }
    }

    public void clearBottomMenu() {
        vAgpBible.setText(Vars.blank);
        vLeftAction.setText(Vars.blank);
        vCurrBible.setText(Vars.blank);
        vRightAction.setText(Vars.blank);
        vCevBible.setText(Vars.blank);
    }

    public void assignButtonListeners() {
        vLeftAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vLeftAction.getText().toString().equals(Vars.blank))
                    return;
                if (Vars.topTab < Vars.TABMODE_HYMN)
                    makeBibleLeft();
                else if (Vars.topTab == Vars.TABMODE_HYMN)
                    makeHymnLeft();
            }
        });
        vRightAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vRightAction.getText().toString().equals(Vars.blank))
                    return;
                if (Vars.topTab < Vars.TABMODE_HYMN)
                    makeBibleRight();
                else if (Vars.topTab == Vars.TABMODE_HYMN)
                    makeHymnRight();
            }
        });
        vMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vars.topTab = Vars.TABMODE_MENU;

            }
        });
        vOldBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vars.topTab = Vars.TABMODE_OLD;
                nowBible = 0;
                makeBibleBottomMenu();
                makeBibleList();
                makeTopMenu();
            }
        });
        vNewBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vars.topTab = Vars.TABMODE_NEW;
                nowBible = 0;
                makeBibleBottomMenu();
                makeBibleList();
                makeTopMenu();
            }
        });
        vHymn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vars.topTab = Vars.TABMODE_HYMN;
                nowBible = 0;
                Vars.nowHymn = 0;
                Vars.utils.generateHymnKeypad();
                makeHymnBottomMenu();
                makeTopMenu();
            }
        });
        vAgpBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vAgpBible.getText().toString().equals(Vars.blank))
                    return;
                Vars.agpShow ^= true;
                Vars.utils.generateBibleBody();
                makeBibleBottomNormal();
                makeTopMenu();
            }
        });
        vCevBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vCevBible.getText().toString().equals(Vars.blank))
                    return;
                Vars.cevShow ^= true;
                Vars.utils.generateBibleBody();
                makeBibleBottomNormal();
                makeTopMenu();
            }
        });
    }

    public void makeBibleLeft() {
        utils.pushHistory();
        int c = Vars.nowChapter - 1;
        if (c == 0) {   // prev bible required
            int b = nowBible - 1;
            if (b == 0)
                return;
            else {
                nowBible = b;
                Vars.nowChapter = Vars.nbrofChapters[b];
            }
        } else
            Vars.nowChapter = c;
        Vars.utils.generateBibleBody();
        makeBibleBottomNormal();
    }

    public void makeHymnLeft() {
        utils.pushHistory();
        Vars.nowHymn--;
        Vars.utils.generateHymnBody();
        makeHymnBottomMenu();
    }

    public void makeBibleRight() {
        utils.pushHistory();
        int c = Vars.nowChapter + 1;
        if (c > Vars.nbrofChapters[nowBible]) {   // next bible required
            int b = nowBible + 1;
            if (b > 66) {
                return;
            } else {
                nowBible = b;
                Vars.nowChapter = 1;
            }
        } else {
            Vars.nowChapter = c;
        }
        Vars.utils.generateBibleBody();
        makeBibleBottomNormal();
    }

    public void makeHymnRight() {
        utils.pushHistory();
        Vars.nowHymn++;
        Vars.utils.generateHymnBody();
        makeHymnBottomMenu();
    }

    public void makeHymnBottomMenu() {
        String txt = "";
        vAgpBible.setText(txt);
        vCevBible.setText(txt);
        if (Vars.nowHymn == 0) {
            vLeftAction.setText(txt);
            vRightAction.setText(txt);
            vCurrBible.setText(Vars.hymnName);
        }
        else {
            if (Vars.nowHymn > 1)
                txt = "" + (Vars.nowHymn - 1);
            else
                txt = Vars.blank;
            vLeftAction.setText(txt);
            vCurrBible.setText(Vars.hymnTitles[Vars.nowHymn]);
            if (Vars.nowHymn < 645)
                txt = "" + (Vars.nowHymn + 1);
            else
                txt = Vars.blank;
            vRightAction.setText(txt);
        }
    }

    public void makeBibleList() {
        Vars.nowChapter = 0;
        Vars.utils.showBibleList();
    }

    @Override
    public void onBackPressed() {

        Log.w("timegap", fromSubFunction + " " + (System.currentTimeMillis()-backKeyPressedTime));
        if(System.currentTimeMillis()>backKeyPressedTime+300){
            backKeyPressedTime = System.currentTimeMillis();
            Log.w("back","topTab " + Vars.topTab + " bible " + nowBible+" chap "+ Vars.nowChapter+" hymn "+ Vars.nowHymn);
            Log.w("topTab",""+ topTab);
            if (!fromSubFunction) {
                utils.popHistory();
                if (topTab < TABMODE_HYMN && nowBible > 0) {
                    utils.generateBibleBody();
                } else {
                    utils.generateHymnBody();
                }
                makeTopMenu();
                makeBottomMenu();
            }
            else {
                makeTopMenu();
                makeBottomMenu();
            }
        }
        else{
            if (fromSubFunction) {
                fromSubFunction = false;
                makeTopMenu();
                makeBottomMenu();
            }
            else {
                quitApp();
            }
        }
    }
    private void quitApp()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("riopapa HolyBible");
        builder.setMessage("리오파파 성경찬송 이젠 그만 볼래요?");
        builder.setPositiveButton("그래",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
        builder.setNegativeButton("아니",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }
}

//
//    StringBuilder bibleString = new StringBuilder();
////                .append(" and the John 3:16-18")
////                .append(" and then 1 Corinthians 3:4")
////                .append(" and the 2 Corinthians 4:6-8 and then we are ready to go with string manipulation.");
//
////        StringBuilder sampleString = new StringBuilder();
//        bibleString.append("Bold Span\n"); // 0-10
//                bibleString.append("Foreground Span\n"); // 11-28
//                bibleString.append("Background Span\n"); // 28-45
//                bibleString.append("Clickable Span\n"); // 45 - 61
//                bibleString.append("Url Span: Google.com\n"); // 61-83
//                bibleString.append("S Subscript\n");
//                bibleString.append("S SuperScript\n");
//                bibleString.append("Underline Me\n");
//                bibleString.append("Relative TextSize span\n");
//                bibleString.append("Italicize me\n");
//                bibleString.append("Strike Me Though\n");
//
//                //build the spannable String
//                SpannableString spannableString = new SpannableString(bibleString.toString());
//                //add bold span
//                spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 10, 0);
//
//                //add fore ground span
//                spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),
//                10, 26, 0);
//
//                //adding background Span
//                spannableString.setSpan(new BackgroundColorSpan(Color.RED), 26, 42, 0);
//
//                //adding click span
//                ClickableSpan clickableSpan = new ClickableSpan() {
//@Override
//public void onClick(View view) {
//        //what happens whe i click
//
//        }
//        };
//        spannableString.setSpan(clickableSpan, 42, 56, 0);
//
//        //add Url Span
//        URLSpan urlSpan = new URLSpan("https://www.google.com") {
//@Override
//public void onClick(View widget) {
//        Intent urlIntent = new Intent(Intent.ACTION_VIEW);
//        urlIntent.setData(Uri.parse(getURL()));
//        startActivity(urlIntent);
//        }
//        };
//
//        spannableString.setSpan(urlSpan, 55, 78, 0);
//
//        //set the movement method for the TextView
//        vBodyText.setMovementMethod(LinkMovementMethod.getInstance());
//
//        //add subscript Span
//        spannableString.setSpan(new SubscriptSpan(), 79, 89, 0);
//
//        //add Superscript span
//        spannableString.setSpan(new SuperscriptSpan(), 92, 104, 0);
//
//        //add underline span
//        spannableString.setSpan(new UnderlineSpan(), 104, 116, 0);
//
//        //add relative size span
//        spannableString.setSpan(new RelativeSizeSpan(1.5f), 116, 139, 0);
//
//        //add italics span
//        spannableString.setSpan(new StyleSpan(Typeface.ITALIC), 139, 152, 0);
//
//        //add strike through span
//        spannableString.setSpan(new StrikethroughSpan(), 153, 170, 0);
//
//
//        vBodyText.setText(spannableString, TextView.BufferType.SPANNABLE);

//    private void hideSystemUI() {
//        // Enables regular immersive mode.
//        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
//        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_IMMERSIVE
//                        // Set the content to appear under the system bars so that the
//                        // content doesn't resize when the system bars hide and show.
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        // Hide the nav bar and status bar
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//        );
//    }



//        final ConstraintLayout layoutb =  findViewById(R.id.fragment_body);
//        final ViewTreeObserver observerbt= layoutT.getViewTreeObserver();
//        observerbt.addOnGlobalLayoutListener(
//            new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    setHeightBottom(layoutb.getHeight());
//                }
//            });
