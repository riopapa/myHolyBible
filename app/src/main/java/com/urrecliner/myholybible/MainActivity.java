package com.urrecliner.myholybible;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.urrecliner.myholybible.Vars.TAB_MODE_DIC;
import static com.urrecliner.myholybible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.myholybible.Vars.TAB_MODE_NEW;
import static com.urrecliner.myholybible.Vars.TAB_MODE_OLD;
import static com.urrecliner.myholybible.Vars.agpColorFore;
import static com.urrecliner.myholybible.Vars.agpShow;
import static com.urrecliner.myholybible.Vars.alwaysOn;
import static com.urrecliner.myholybible.Vars.bibleColorFore;
import static com.urrecliner.myholybible.Vars.biblePitch;
import static com.urrecliner.myholybible.Vars.bibleSpeed;
import static com.urrecliner.myholybible.Vars.blackMode;
import static com.urrecliner.myholybible.Vars.blank;
import static com.urrecliner.myholybible.Vars.bookMarks;
import static com.urrecliner.myholybible.Vars.cevColorFore;
import static com.urrecliner.myholybible.Vars.cevShow;
import static com.urrecliner.myholybible.Vars.dictColorFore;
import static com.urrecliner.myholybible.Vars.editor;
import static com.urrecliner.myholybible.Vars.fullBibleNames;
import static com.urrecliner.myholybible.Vars.goBacks;
import static com.urrecliner.myholybible.Vars.history;
import static com.urrecliner.myholybible.Vars.hymnImageFirst;
import static com.urrecliner.myholybible.Vars.hymnName;
import static com.urrecliner.myholybible.Vars.hymnShowWhat;
import static com.urrecliner.myholybible.Vars.hymnSpeed;
import static com.urrecliner.myholybible.Vars.hymnTitles;
import static com.urrecliner.myholybible.Vars.isReadingNow;
import static com.urrecliner.myholybible.Vars.mActivity;
import static com.urrecliner.myholybible.Vars.mBody;
import static com.urrecliner.myholybible.Vars.mContext;
import static com.urrecliner.myholybible.Vars.mainActivity;
import static com.urrecliner.myholybible.Vars.makeBible;
import static com.urrecliner.myholybible.Vars.maxVerse;
import static com.urrecliner.myholybible.Vars.nbrOfChapters;
import static com.urrecliner.myholybible.Vars.newName;
import static com.urrecliner.myholybible.Vars.normalMenuColor;
import static com.urrecliner.myholybible.Vars.nowBible;
import static com.urrecliner.myholybible.Vars.nowChapter;
import static com.urrecliner.myholybible.Vars.nowHymn;
import static com.urrecliner.myholybible.Vars.nowScrollView;
import static com.urrecliner.myholybible.Vars.nowVerse;
import static com.urrecliner.myholybible.Vars.numberColorFore;
import static com.urrecliner.myholybible.Vars.oldName;
import static com.urrecliner.myholybible.Vars.onSwipeTouchListener;
import static com.urrecliner.myholybible.Vars.packageFolder;
import static com.urrecliner.myholybible.Vars.paraColorFore;
import static com.urrecliner.myholybible.Vars.referColorFore;
import static com.urrecliner.myholybible.Vars.sharedPreferences;
import static com.urrecliner.myholybible.Vars.shortBibleNames;
import static com.urrecliner.myholybible.Vars.sortedNumbers;
import static com.urrecliner.myholybible.Vars.text2Speech;
import static com.urrecliner.myholybible.Vars.textSizeBible66;
import static com.urrecliner.myholybible.Vars.textSizeBibleBody;
import static com.urrecliner.myholybible.Vars.textSizeBibleRefer;
import static com.urrecliner.myholybible.Vars.textSizeHymnBody;
import static com.urrecliner.myholybible.Vars.textSizeKeyWord;
import static com.urrecliner.myholybible.Vars.textSizeSpace;
import static com.urrecliner.myholybible.Vars.topTab;
import static com.urrecliner.myholybible.Vars.utils;
import static com.urrecliner.myholybible.Vars.vCurrBible;
import static com.urrecliner.myholybible.Vars.verseColorFore;
import static com.urrecliner.myholybible.Vars.windowXCenter;
import static com.urrecliner.myholybible.Vars.windowYUpper;
import static com.urrecliner.myholybible.Vars.xPixels;
import static com.urrecliner.myholybible.Vars.yPixels;

public class MainActivity extends Activity {

    ImageView vSetting;
    TextView vOldBible, vNewBible, vHymn;
    TextView vAgpBible, vLeftAction, vRightAction, vCevBible;
    long backKeyPressedTime;
    private com.urrecliner.myholybible.MakeHymn makeHymn;
    int highLiteMenuColor, readNowColor;
    final static String cevVersion = "cev";
    final static String agpVersion = "agp";
    private boolean bookMarkNow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mContext = this;
        mActivity = this;
        mainActivity = this;

        makeHymn = new com.urrecliner.myholybible.MakeHymn();
        makeBible = new com.urrecliner.myholybible.MakeBible();
        utils = new com.urrecliner.myholybible.Utils(this);
        history = new com.urrecliner.myholybible.History();

        askPermission();
        mBody = (ViewGroup) findViewById(R.id.fragment_body);
        sharedPreferences = getApplicationContext().getSharedPreferences("bible", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        getSharedValues();
        goBacks = utils.readGoBacks();
        bookMarks = utils.readBookMarks();

        history.init();
        packageFolder = new File(Environment.getExternalStorageDirectory(), "myHolyBible");

        final ViewGroup fTop = (ViewGroup) findViewById(R.id.fragment_top);
        final ViewGroup fBtm = (ViewGroup) findViewById(R.id.fragment_bottom);
        vSetting = (ImageView) fTop.findViewById(R.id.setting);
        vOldBible = (TextView) fTop.findViewById(R.id.oldBible);
        vNewBible = (TextView) fTop.findViewById(R.id.newBible);
        vHymn = (TextView) fTop.findViewById(R.id.hymn);
        vAgpBible = (TextView) fTop.findViewById(R.id.agpBible);
        vCevBible = (TextView) findViewById(R.id.cevBible);

        vLeftAction = (TextView) fBtm.findViewById(R.id.leftAction);
        vCurrBible = (TextView) fBtm.findViewById(R.id.currBible);
        vRightAction = (TextView) fBtm.findViewById(R.id.rightAction);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
        try {
            assert windowManager != null;
            windowManager.getDefaultDisplay().getMetrics(dm);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        xPixels = dm.widthPixels;
        yPixels = dm.heightPixels;

        if (alwaysOn)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setColors();

        assignAllButtonListeners();
        vSetting.post(new Runnable() {
            @Override
            public void run() {
                int width = vSetting.getWidth();
                int height = vNewBible.getHeight();
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width, height);
                vSetting.setLayoutParams(layoutParams);
            }
        });

        onSwipeTouchListener = new com.urrecliner.myholybible.OnSwipeTouchListener(MainActivity.this) {

            @Override
            public void onSwipeGoBack() {
                goBackward();
            }

            @Override
            public void onSwipeGoFore() {
//                goForward();
            }

            @Override
            public void onSwipePrev() {
                if (vLeftAction.getText().toString().equals(blank))
                    return;
                if (topTab < TAB_MODE_HYMN)
                    goBibleLeft();
                else if (topTab == TAB_MODE_HYMN)
                    goHymnLeft();
            }

            @Override
            public void onSwipeNext() {
                if (vRightAction.getText().toString().equals(blank))
                    return;
                if (topTab < TAB_MODE_HYMN)
                    goBibleRight();
                else if (topTab == TAB_MODE_HYMN)
                    goHymnRight();
            }
        };
        mBody.setOnTouchListener(onSwipeTouchListener);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        windowYUpper = size.y * 6f / 10f;
        windowXCenter = size.x / 2f;

        if (topTab < TAB_MODE_HYMN) {
            if (nowBible > 0)
                makeBible.MakeBibleBody();
            else
                makeBible.showBibleList();
        } else {
            if (nowHymn > 0)
                makeHymn.makeHymnBody();
            else
                makeHymn.makeHymnKeypad();
        }
        text2Speech = new com.urrecliner.myholybible.Text2Speech();
        text2Speech.setReady(getApplicationContext());
    }

    private void getSharedValues() {
        textSizeBible66 = sharedPreferences.getInt("textSizeBible66", 24);
        textSizeBibleBody = sharedPreferences.getInt("textSizeBibleBody", 20);
        textSizeBibleRefer = sharedPreferences.getInt("textSizeBibleRefer", 10);
        textSizeHymnBody = sharedPreferences.getInt("textSizeHymnBody", 20);
        textSizeKeyWord = sharedPreferences.getInt("textSizeKeyWord", 22);
        textSizeSpace = sharedPreferences.getInt("textSizeSpace", 15);
        hymnImageFirst = sharedPreferences.getBoolean("hymnImageFirst", true);
        blackMode = sharedPreferences.getBoolean("blackMode", false);
        hymnShowWhat = sharedPreferences.getInt("hymnShowWhat", 0);
        alwaysOn = sharedPreferences.getBoolean("alwaysOn",true);
        bibleSpeed = sharedPreferences.getFloat("bibleSpeed", 0.8f);
        biblePitch = sharedPreferences.getFloat("biblePitch",1.0f);
        hymnSpeed = sharedPreferences.getFloat("hymnSpeed", 0.8f);
        agpShow = sharedPreferences.getBoolean("agpShow", false);
        cevShow = sharedPreferences.getBoolean("cevShow", false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        onSwipeTouchListener.getGestureDetector().onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
    private void setColors() {

        ColorDrawable cd = (ColorDrawable) vCurrBible.getBackground();
        normalMenuColor = cd.getColor();
        highLiteMenuColor = normalMenuColor ^ 0x444444;
        readNowColor = normalMenuColor ^ 0x777777;

        bibleColorFore = ContextCompat.getColor(mContext,R.color.bibleColorFore);
        verseColorFore = ContextCompat.getColor(mContext,R.color.verseColorFore);
        paraColorFore = ContextCompat.getColor(mContext,R.color.paraColorFore);
        referColorFore = ContextCompat.getColor(mContext,R.color.referColorFore);
        numberColorFore = ContextCompat.getColor(mContext,R.color.numberColorFore);

        cevColorFore = ContextCompat.getColor(mContext,R.color.cevColorFore);
        agpColorFore = ContextCompat.getColor(mContext,R.color.agpColorFore);
        dictColorFore = ContextCompat.getColor(mContext,R.color.dictColorFore);
    }

    public void makeTopBottomMenu() {

        vOldBible.setBackgroundColor((topTab == TAB_MODE_OLD)? highLiteMenuColor:normalMenuColor);
        vNewBible.setBackgroundColor((topTab == TAB_MODE_NEW)? highLiteMenuColor:normalMenuColor);
        vHymn.setBackgroundColor((topTab == TAB_MODE_HYMN)? highLiteMenuColor:normalMenuColor);
        vAgpBible.setText(blank);
        vCevBible.setText(blank);
        vAgpBible.setBackgroundColor(normalMenuColor);
        vCevBible.setBackgroundColor(normalMenuColor);

        if (topTab < 4)
            makeBibleMenu();
        else if (topTab == TAB_MODE_HYMN)
            makeHymnMenu();
        else
            clearMenu();
    }

    public void makeBibleMenu() {
        if (nowBible == 0) {
            vLeftAction.setText((blank));
            vRightAction.setText((blank));
            vCurrBible.setText((topTab == TAB_MODE_OLD) ? oldName : newName);
        }
        else
            makeBibleMenuNormal();
    }

    public void makeBibleMenuNormal() {
        String txt;
        if (nowChapter==0) {
            vAgpBible.setText(blank);
            vLeftAction.setText(blank);
            vCurrBible.setText(fullBibleNames[nowBible]);
            vRightAction.setText(blank);
            vCevBible.setText(blank);
        }
        else {
            vAgpBible.setText(agpVersion);
            vAgpBible.setBackgroundColor((agpShow)? highLiteMenuColor:normalMenuColor);
            int chapter = nowChapter - 1;
            if (chapter > 0)
                txt = shortBibleNames[nowBible] + chapter;
            else {
                int prev = nowBible - 1;
                if (prev > 0)
                    txt = shortBibleNames[prev] + nbrOfChapters[prev];
                else
                    txt = blank;
            }
            vLeftAction.setText(txt);
            txt = fullBibleNames[nowBible] + nowChapter;
            vCurrBible.setText(txt);
            chapter = nowChapter + 1;
            if (chapter <= nbrOfChapters[nowBible])
                txt = shortBibleNames[nowBible] + chapter;
            else {
                int next = nowBible + 1;
                txt = (next > 66) ? blank:shortBibleNames[next] + "1";
            }
            vRightAction.setText(txt);
            vCevBible.setText(cevVersion);
            vCevBible.setBackgroundColor((cevShow)? highLiteMenuColor:normalMenuColor);
        }
    }

    public void clearMenu() {
        vLeftAction.setText(blank);
        vCurrBible.setText(blank);
        vRightAction.setText(blank);
    }

    public void assignAllButtonListeners() {
        vSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadingNow)
                    text2Speech.stopRead();
                Intent i = new Intent(MainActivity.this, com.urrecliner.myholybible.SetActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        vLeftAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadingNow)
                    text2Speech.stopRead();
                if (vLeftAction.getText().toString().equals(blank))
                    return;
                if (topTab < TAB_MODE_HYMN)
                    goBibleLeft();
                else if (topTab == TAB_MODE_HYMN)
                    goHymnLeft();
            }
        });
        vCurrBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vCurrBible.getText().toString().equals(blank))
                    return;
                if (topTab < TAB_MODE_HYMN && nowBible > 0 && nowChapter > 0)
                    bookMarkThis();
            }
        });
        vCurrBible.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (vCurrBible.getText().toString().equals(blank))
                    return false;
                if (topTab < TAB_MODE_HYMN && nowBible > 0 && nowChapter > 0)
                    readBible();
                else if (topTab == TAB_MODE_HYMN && nowHymn > 0)
                    playStopHymn();
                return false;
            }
        });
        vRightAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadingNow)
                    text2Speech.stopRead();
                if (vRightAction.getText().toString().equals(blank))
                    return;
                if (topTab < TAB_MODE_HYMN)
                    goBibleRight();
                else if (topTab == TAB_MODE_HYMN)
                    goHymnRight();
            }
        });
        vOldBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadingNow)
                    text2Speech.stopRead();
                topTab = TAB_MODE_OLD;
//                nowVerse = getNowTopVerse();
                nowBible = 0;
                makeBibleList();
            }
        });
        vNewBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadingNow)
                    text2Speech.stopRead();
                topTab = TAB_MODE_NEW;
//                nowVerse = getNowTopVerse();
                nowBible = 0;
                makeBibleList();
            }
        });
        vHymn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadingNow)
                    text2Speech.stopRead();
                topTab = TAB_MODE_HYMN;
                nowBible = 0;
                nowHymn = 0;
                makeHymn.makeHymnKeypad();
            }
        });
        vAgpBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vAgpBible.getText().toString().equals(blank))
                    return;
                int currVerse = getNowTopVerse();
                agpShow = !agpShow;
                editor.putBoolean("agpShow", agpShow).apply();
                history.pop();
                nowVerse = currVerse;
                makeBible.MakeBibleBody();
            }
        });
        vCevBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vCevBible.getText().toString().equals(blank))
                    return;
                int currVerse = getNowTopVerse();
                cevShow = !cevShow;
                editor.putBoolean("cevShow", cevShow).apply();
                history.pop();
                nowVerse = currVerse;
                makeBible.MakeBibleBody();
            }
        });
    }

    private final Handler aHandler = new Handler() {
        public void handleMessage(Message msg) { vCurrBible.setEnabled(true); }};

    void readBible() {
        if (isReadingNow) {
            isReadingNow = false;
            bookMarkNow = true;
            Toast.makeText(mContext,"성경읽기를 끝냅니다",Toast.LENGTH_SHORT).show();
            history.push();
            text2Speech.stopRead();
        }
        else {
            isReadingNow = true;
            bookMarkNow = false;
            Toast.makeText(mContext,"성경읽기를 시작합니다",Toast.LENGTH_SHORT).show();
            text2Speech.readVerse();
        }
        vCurrBible.setEnabled(false);
        vCurrBible.setBackgroundColor((isReadingNow)? readNowColor :normalMenuColor);

        new Timer().schedule(new TimerTask() {
            public void run() {
                aHandler.sendEmptyMessage(0);
            }
        }, 500);
    }

    void playStopHymn() {
        if (isReadingNow) {
            isReadingNow = false;
            Toast.makeText(mContext,"찬송부르기를 끝냅니다",Toast.LENGTH_SHORT).show();
            history.push();
            text2Speech.stopRead();
        }
        else {
            isReadingNow = true;
            Toast.makeText(mContext,"찬송 부르기를 시작합니다",Toast.LENGTH_SHORT).show();
            text2Speech.playHymn();
        }
        vCurrBible.setEnabled(false);
        vCurrBible.setBackgroundColor((isReadingNow)? readNowColor :normalMenuColor);

        new Timer().schedule(new TimerTask() {
            public void run() {
                aHandler.sendEmptyMessage(0);
            }
        }, 800);
    }

    public void goBibleLeft() {
        int prevChapter = nowChapter - 1;
        if (prevChapter == 0) {   // prev bible required
            int prevBible = nowBible - 1;
            if (prevBible == 0)
                return;
            else {
                nowBible = prevBible;
                nowChapter = nbrOfChapters[prevBible];
            }
        } else
            nowChapter = prevChapter;
        nowVerse = 1;
        makeBible.MakeBibleBody();
    }

    void bookMarkThis() {
        if (!bookMarkNow)
            return;
        com.urrecliner.myholybible.BookMark bookMark = new com.urrecliner.myholybible.BookMark(nowBible, nowChapter, System.currentTimeMillis(), false);
        bookMarks.add(0, bookMark);
        utils.savePrefers("bookMark", bookMarks);
        Toast.makeText(mContext, fullBibleNames[nowBible]+" "+nowChapter+" 장이\n북마크 되었습니다",Toast.LENGTH_LONG).show();
    }

    public void goHymnLeft() {
        nowHymn--;
        makeHymn.makeHymnBody();
    }

    final Handler nextHandler = new Handler() {
        public void handleMessage(Message msg) { goBibleRight(); }};
    public void handleBibleRight() {
        nextHandler.sendEmptyMessage(0);
    }

    public void goBibleRight() {
        int nextChapter = nowChapter + 1;
        if (nextChapter > nbrOfChapters[nowBible]) {   // next bible required
            int prevBible = nowBible + 1;
            if (prevBible > 66) {
                return;
            } else {
                nowBible = prevBible;
                nowChapter = 1;
            }
        } else {
            nowChapter = nextChapter;
        }
        nowVerse = 1;
        makeBible.MakeBibleBody();
    }

    public void goHymnRight() {
        nowHymn++;
        makeHymn.makeHymnBody();
    }

    public void makeHymnMenu() {
        if (nowHymn == 0) {
            vLeftAction.setText(blank);
            vRightAction.setText(blank);
            vCurrBible.setText(hymnName);
        }
        else if (nowHymn < 0) {
            vLeftAction.setText(blank);
            vRightAction.setText(blank);
            vCurrBible.setText(hymnTitles[sortedNumbers[-nowHymn - 1]].substring(0,8));
        }
        else {
            vLeftAction.setText((nowHymn > 1) ? "" + (nowHymn - 1):blank);
            vCurrBible.setText(hymnTitles[nowHymn]);
            vRightAction.setText((nowHymn < 645) ? "" + (nowHymn + 1):blank);
        }
    }

    public void makeBibleList() {
        nowChapter = 0;
        makeBible.showBibleList();
    }

    private void goBackward() {
        if (goBacks.size() == 1) {
            Toast.makeText(mContext,"맨 처음 입니다." , Toast.LENGTH_LONG).show();
        }
        else {
            history.pop();
            history.pop();
            if (topTab < TAB_MODE_HYMN) {
                if (nowBible > 0)
                    makeBible.MakeBibleBody();
                else
                    makeBible.showBibleList();
            } else if (topTab == TAB_MODE_HYMN) {
                if (nowHymn > 0)
                    makeHymn.makeHymnBody();
                else {
                    nowHymn = 0;
                    makeHymn.makeHymnKeypad();
                }
            } else if (topTab == TAB_MODE_DIC) {
                makeBible.makeKeyWord();
            } else
                makeTopBottomMenu();
        }
    }

    private int getNowTopVerse() {
        if (topTab == TAB_MODE_NEW || topTab == TAB_MODE_OLD)
            return maxVerse *  nowScrollView.getScrollY() / nowScrollView.getChildAt(0).getHeight() + 2;
        else
            return 0;
    }

// ↓ ↓ ↓ P E R M I S S I O N    RELATED /////// ↓ ↓ ↓ ↓
    ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();

    private void askPermission() {
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if (permissionsToRequest.size() != 0) {
            requestPermissions(permissionsToRequest.toArray(new String[0]),
//            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                    ALL_PERMISSIONS_RESULT);
        }
    }

    private ArrayList findUnAskedPermissions(@NonNull ArrayList<String> wanted) {
        ArrayList <String> result = new ArrayList<String>();
        for (String perm : wanted) if (hasPermission(perm)) result.add(perm);
        return result;
    }
    private boolean hasPermission(@NonNull String permission) {
        return (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ALL_PERMISSIONS_RESULT) {
            for (String perms : permissionsToRequest) {
                if (hasPermission(perms)) {
                    permissionsRejected.add(perms);
                }
            }
            if (permissionsRejected.size() > 0) {
                if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                    String msg = "These permissions are mandatory for the application. Please allow access.";
                    showDialog(msg);
                }
            }
            else
                Toast.makeText(mContext, "Permissions not granted.", Toast.LENGTH_LONG).show();
        }
    }
    private void showDialog(String msg) {
        showMessageOKCancel(msg,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestPermissions(permissionsRejected.toArray(
                            new String[0]), ALL_PERMISSIONS_RESULT);
                }
            });
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
// ↑ ↑ ↑ ↑ P E R M I S S I O N    RELATED /////// ↑ ↑ ↑


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    final long BACK_DELAY = 1000;
    @Override
    public void onBackPressed() {

        if (isReadingNow)
            text2Speech.stopRead();

        MainDialog mMainDialog;
        if(System.currentTimeMillis()>backKeyPressedTime+BACK_DELAY){
            backKeyPressedTime = System.currentTimeMillis();
            if (goBacks.size() == 1) {
                Toast.makeText(getApplicationContext(),"\n맨 처음 입니다.\n혹시 종료하려면 뒤로가기를 두번 눌러주세요\n", Toast.LENGTH_LONG).show();
            }
            else {
                saveNow();
                goBackward();
            }
        }
        else {
            reloadNow();
            history.push();
            utils.savePrefers("goBack", goBacks);
            utils.savePrefers("bookMark", bookMarks);
            mMainDialog = new MainDialog();
            mMainDialog.show(getFragmentManager(), null);
        }
    }

    int saveTab, saveBible, saveChapter, saveVerse, saveHymn;

    private void saveNow() {
        saveTab = topTab;
        saveBible = nowBible;
        saveChapter = nowChapter;
        saveVerse = nowVerse;
        saveHymn = nowHymn;
    }
    private void reloadNow() {
        topTab = saveTab;
        nowBible = saveBible;
        nowChapter = saveChapter;
        nowVerse = saveVerse;
        nowHymn = saveHymn;
    }
    public static class MainDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
            mBuilder.setView(mLayoutInflater.inflate(R.layout.dialog_quit, null));
            mBuilder.setTitle(mActivity.getResources().getString(R.string.wanna_quit));

//            mBuilder.setMessage();
            return mBuilder.create();
        }

        @Override
        public void onStop() {
            super.onStop();
        }

    }

    public void finish_bye(View v) {
        finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
