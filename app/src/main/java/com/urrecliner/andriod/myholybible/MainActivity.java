package com.urrecliner.andriod.myholybible;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static com.urrecliner.andriod.myholybible.Vars.TABMODE_DIC;
import static com.urrecliner.andriod.myholybible.Vars.TABMODE_HYMN;
import static com.urrecliner.andriod.myholybible.Vars.agpShow;
import static com.urrecliner.andriod.myholybible.Vars.cevShow;
import static com.urrecliner.andriod.myholybible.Vars.mActivity;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.nowHymn;
import static com.urrecliner.andriod.myholybible.Vars.nowVerse;
import static com.urrecliner.andriod.myholybible.Vars.topTab;

public class MainActivity extends Activity {

    ImageView vSetting;
    TextView vOldBible, vNewBible, vHymn, vGoBack;
    TextView vAgpBible, vLeftAction, vCurrBible, vRightAction, vCevBible;
    long backKeyPressedTime;
    private Utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vars.mContext = getApplicationContext();
        Vars.mActivity = this;
        //        MainActivity mainActivity = new MainActivity();
        utils = new Utils(this);
        Log.w("main", "started");

        setContentView(R.layout.activity_main);

        initializeVariables();

        Vars.packageFolder = new File(Environment.getExternalStorageDirectory(), "myHolyBible");
        Vars.topTab = Vars.TABMODE_NEW;
        Vars.nowBible = 0;
        Vars.nowChapter = 0;
        Vars.nowVerse = 0;
        Vars.nowHymn = 0;

        makeTopBottomMenu();
        makeBibleList();
        assignButtonListeners();
    }

    private void initializeVariables() {
        Vars.mContainerBody = findViewById(R.id.fragment_body);
        vSetting = findViewById(R.id.setting);
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
            assert windowManager != null;
            windowManager.getDefaultDisplay().getMetrics(dm);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Vars.xPixels = dm.widthPixels;
        Vars.yPixels = dm.heightPixels;
    }

    public void makeTopBottomMenu() {
        makeTopMenu();
        makeBottomMenu();
    }

    public void makeTopMenu() {
        int highLight = getResources().getColor(R.color.MenuHighLight, mActivity.getTheme());
        ColorDrawable cd = (ColorDrawable) vCurrBible.getBackground();
        int normal = cd.getColor();
        if (Vars.topTab == Vars.TABMODE_OLD)
            vOldBible.setBackgroundColor(highLight);
        else
            vOldBible.setBackgroundColor(normal);
        if (Vars.topTab == Vars.TABMODE_NEW)
            vNewBible.setBackgroundColor(highLight);
        else
            vNewBible.setBackgroundColor(normal);
        if (Vars.topTab == Vars.TABMODE_HYMN)
            vHymn.setBackgroundColor(highLight);
        else
            vHymn.setBackgroundColor(normal);
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
        if (nowBible == 0)
            makeBibleBottomClear();
        else
            makeBibleBottomNormal();
    }

    public void makeBibleBottomClear() {
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
        int highLight = getResources().getColor(R.color.MenuHighLight, mActivity.getTheme());
        ColorDrawable cd = (ColorDrawable) vCurrBible.getBackground();
        int normal = cd.getColor();
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
            if (agpShow)
                vAgpBible.setBackgroundColor(highLight);
            else
                vAgpBible.setBackgroundColor(normal);
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
            if (cevShow)
                vCevBible.setBackgroundColor(highLight);
            else
                vCevBible.setBackgroundColor(normal);
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
        vSetting.setOnClickListener(new View.OnClickListener() {
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
            }
        });
        vNewBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vars.topTab = Vars.TABMODE_NEW;
                nowBible = 0;
                makeBibleList();
            }
        });
        vHymn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vars.topTab = Vars.TABMODE_HYMN;
                nowBible = 0;
                Vars.nowHymn = 0;
                utils.generateHymnKeypad();
            }
        });
        vGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack2Prev();
            }
        });
        vAgpBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vAgpBible.getText().toString().equals(Vars.blank))
                    return;
                Vars.agpShow ^= true;
                utils.popHistory();
                utils.generateBibleBody();
            }
        });
        vCevBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vCevBible.getText().toString().equals(Vars.blank))
                    return;
                Vars.cevShow ^= true;
                utils.popHistory();
                utils.generateBibleBody();
            }
        });
    }

    public void makeBibleLeft() {
        int prevChapter = Vars.nowChapter - 1;
        if (prevChapter == 0) {   // prev bible required
            int prevBible = nowBible - 1;
            if (prevBible == 0)
                return;
            else {
                nowBible = prevBible;
                Vars.nowChapter = Vars.nbrofChapters[prevBible];
            }
        } else
            Vars.nowChapter = prevChapter;
        nowVerse = 1;
        utils.generateBibleBody();
    }

    public void makeHymnLeft() {
        Vars.nowHymn--;
        utils.generateHymnBody();
    }

    public void makeBibleRight() {
        int prevChapter = Vars.nowChapter + 1;
        if (prevChapter > Vars.nbrofChapters[nowBible]) {   // next bible required
            int prevBible = nowBible + 1;
            if (prevBible > 66) {
                return;
            } else {
                nowBible = prevBible;
                Vars.nowChapter = 1;
            }
        } else {
            Vars.nowChapter = prevChapter;
        }
        nowVerse = 1;
        utils.generateBibleBody();
    }

    public void makeHymnRight() {
        Vars.nowHymn++;
        utils.generateHymnBody();
    }

    public void makeHymnBottomMenu() {
        String txt = "";
        vAgpBible.setText(txt);
        vCevBible.setText(txt);
        ColorDrawable cd = (ColorDrawable) vCurrBible.getBackground();
        int normal = cd.getColor();
        vAgpBible.setBackgroundColor(normal);
        vCevBible.setBackgroundColor(normal);
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
        utils.showBibleList();
    }

    private void goBack2Prev() {
        utils.popHistory();
        utils.popHistory();
        if (topTab < TABMODE_HYMN && nowBible > 0) {
            utils.generateBibleBody();
        } else if (topTab == TABMODE_HYMN && nowHymn > 0) {
            utils.generateHymnBody();
        }
        else if (topTab == TABMODE_DIC) {
            utils.generateKeyWord();
        }
        else
            makeTopBottomMenu();
    }

//    private final int textSize_Bible = 1;
//    private final int textSize_Hymn = 2;
//    private final int show_HymnImage = 3;
//    private final int show_HymnText = 4;
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(Menu.NONE, textSize_Bible, Menu.NONE, "성경 본문 크기 설정");
//        menu.add(Menu.NONE, textSize_Hymn, Menu.NONE, "찬송 본문 크기 설정");
//        menu.add(Menu.NONE, show_HymnImage, Menu.NONE, "찬송 악보 보이기");
//        menu.add(Menu.NONE, show_HymnText, Menu.NONE, "찬송 가사 보이기");
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Toast.makeText(getApplicationContext(), item.getItemId() + " selected",Toast.LENGTH_LONG).show();
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {

//        Log.w("timegap", " " + (System.currentTimeMillis()-backKeyPressedTime));
//        Log.w("back", " topTab " + Vars.topTab + " bible " + nowBible+" chap "+ Vars.nowChapter+" hymn "+ Vars.nowHymn);
        if(System.currentTimeMillis()>backKeyPressedTime+500){
            backKeyPressedTime = System.currentTimeMillis();
            goBack2Prev();
        }
        else{
            quitApp();
        }
    }
    private void quitApp()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.icon_myface);
        builder.setMessage("리오파파 성경찬송 이젠 그만 볼래요?");
        builder.setPositiveButton("다음에 또 만나요",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
        builder.setNegativeButton("앗, 잘못 눌렀군",
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
