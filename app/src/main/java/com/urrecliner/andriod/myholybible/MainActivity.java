package com.urrecliner.andriod.myholybible;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
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

import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_DIC;
import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_HYMN;
import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_NEW;
import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_OLD;
import static com.urrecliner.andriod.myholybible.Vars.agpColorB;
import static com.urrecliner.andriod.myholybible.Vars.agpColorF;
import static com.urrecliner.andriod.myholybible.Vars.agpShow;
import static com.urrecliner.andriod.myholybible.Vars.alwaysOn;
import static com.urrecliner.andriod.myholybible.Vars.bibleColorF;
import static com.urrecliner.andriod.myholybible.Vars.blank;
import static com.urrecliner.andriod.myholybible.Vars.cevColorB;
import static com.urrecliner.andriod.myholybible.Vars.cevColorF;
import static com.urrecliner.andriod.myholybible.Vars.cevShow;
import static com.urrecliner.andriod.myholybible.Vars.dictColorF;
import static com.urrecliner.andriod.myholybible.Vars.editor;
import static com.urrecliner.andriod.myholybible.Vars.fullBibleNames;
import static com.urrecliner.andriod.myholybible.Vars.hymnImageShow;
import static com.urrecliner.andriod.myholybible.Vars.hymnName;
import static com.urrecliner.andriod.myholybible.Vars.hymnTextShow;
import static com.urrecliner.andriod.myholybible.Vars.hymnTitles;
import static com.urrecliner.andriod.myholybible.Vars.mActivity;
import static com.urrecliner.andriod.myholybible.Vars.mBody;
import static com.urrecliner.andriod.myholybible.Vars.mContext;
import static com.urrecliner.andriod.myholybible.Vars.mSettings;
import static com.urrecliner.andriod.myholybible.Vars.nbrofChapters;
import static com.urrecliner.andriod.myholybible.Vars.newName;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.nowChapter;
import static com.urrecliner.andriod.myholybible.Vars.nowHymn;
import static com.urrecliner.andriod.myholybible.Vars.nowVerse;
import static com.urrecliner.andriod.myholybible.Vars.numberColorF;
import static com.urrecliner.andriod.myholybible.Vars.oldName;
import static com.urrecliner.andriod.myholybible.Vars.onSwipeTouchListener;
import static com.urrecliner.andriod.myholybible.Vars.packageFolder;
import static com.urrecliner.andriod.myholybible.Vars.paraColorF;
import static com.urrecliner.andriod.myholybible.Vars.referColorF;
import static com.urrecliner.andriod.myholybible.Vars.shortBibleNames;
import static com.urrecliner.andriod.myholybible.Vars.stackMax;
import static com.urrecliner.andriod.myholybible.Vars.stackP;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBible66;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleRefer;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleText;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnText;
import static com.urrecliner.andriod.myholybible.Vars.textSizeKeyWord;
import static com.urrecliner.andriod.myholybible.Vars.topTab;
import static com.urrecliner.andriod.myholybible.Vars.verseColorF;
import static com.urrecliner.andriod.myholybible.Vars.windowXCenter;
import static com.urrecliner.andriod.myholybible.Vars.windowYUpper;
import static com.urrecliner.andriod.myholybible.Vars.xPixels;
import static com.urrecliner.andriod.myholybible.Vars.yPixels;

public class MainActivity extends Activity {

    ImageView vSetting;
    TextView vOldBible, vNewBible, vHymn;
    TextView vAgpBible, vLeftAction, vCurrBible, vRightAction, vCevBible;
    long backKeyPressedTime;
    private Utils utils;
    int highLiteMenuColor;
    int normalMenuColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        mActivity = this;
        mBody = (ViewGroup) findViewById(R.id.fragment_body);
        utils = new Utils(this);
        askPermission();
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        editor = mSettings.edit();
        textSizeBible66 = mSettings.getInt("textSizeBible66", 24);
        textSizeBibleText = mSettings.getInt("textSizeBibleText", 20);
        textSizeBibleRefer = mSettings.getInt("textSizeBibleRefer", 10);
        textSizeHymnText = mSettings.getInt("textSizeHymnText", 20);
        textSizeKeyWord = mSettings.getInt("textSizeKeyWord", 22);
        hymnImageShow = mSettings.getBoolean("hymnImageShow", true);
        hymnTextShow = mSettings.getBoolean("hymnTextShow", true);
        alwaysOn = mSettings.getBoolean("alwaysOn",true);

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

        initializeVariables();

        topTab = TAB_MODE_NEW;
        nowBible = 0;
        nowChapter = 0;
        nowVerse = 0;
        nowHymn = 0;

        makeTopBottomMenu();
        makeBibleList();
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

        onSwipeTouchListener = new OnSwipeTouchListener(MainActivity.this) {

            @Override
            public void onSwipeGoBack() {
                goBack2Back();
            }

            @Override
            public void onSwipeGoFore() {
                goBack2Fore();
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
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        onSwipeTouchListener.getGestureDetector().onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
    private void initializeVariables() {

        ColorDrawable cd = (ColorDrawable) vCurrBible.getBackground();
        normalMenuColor = cd.getColor();
        highLiteMenuColor = normalMenuColor ^ 0x444444;

        bibleColorF = ContextCompat.getColor(mContext,R.color.Black);
        verseColorF = ContextCompat.getColor(mContext,R.color.EarthBlue);
        paraColorF = ContextCompat.getColor(mContext,R.color.Indigo);
        referColorF = ContextCompat.getColor(mContext,R.color.RoyalBlue);
        numberColorF = ContextCompat.getColor(mContext,R.color.MidnightBlue);

        cevColorF = ContextCompat.getColor(mContext,R.color.Navy);
        cevColorB = ContextCompat.getColor(mContext,R.color.PowderBlue);
        agpColorF = ContextCompat.getColor(mContext,R.color.DarkOrchid);
        agpColorB = ContextCompat.getColor(mContext,R.color.Lavender);
        dictColorF = ContextCompat.getColor(mContext,R.color.Blue);
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
            vAgpBible.setText("AGP");
            vAgpBible.setBackgroundColor((agpShow)? highLiteMenuColor:normalMenuColor);
            int chapter = nowChapter - 1;
            if (chapter > 0)
                txt = shortBibleNames[nowBible] + chapter;
            else {
                int prev = nowBible - 1;
                if (prev > 0)
                    txt = shortBibleNames[prev] + nbrofChapters[prev];
                else
                    txt = blank;
            }
            vLeftAction.setText(txt);
            txt = fullBibleNames[nowBible] + nowChapter;
            vCurrBible.setText(txt);
            chapter = nowChapter + 1;
            if (chapter <= nbrofChapters[nowBible])
                txt = shortBibleNames[nowBible] + chapter;
            else {
                int next = nowBible + 1;
                txt = (next > 66) ? blank:shortBibleNames[next] + "1";
            }
            vRightAction.setText(txt);
            vCevBible.setText("CEV");
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
                utils.generateSettingBody();
            }
        });
        vLeftAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vLeftAction.getText().toString().equals(blank))
                    return;
                if (topTab < TAB_MODE_HYMN)
                    goBibleLeft();
                else if (topTab == TAB_MODE_HYMN)
                    goHymnLeft();
            }
        });
        vRightAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                saveScrollY();
                topTab = TAB_MODE_OLD;
                nowBible = 0;
                makeBibleList();
            }
        });
        vNewBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScrollY();
                topTab = TAB_MODE_NEW;
                nowBible = 0;
                makeBibleList();
            }
        });
        vHymn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScrollY();
                topTab = TAB_MODE_HYMN;
                nowBible = 0;
                nowHymn = 0;
                utils.generateHymnKeypad();
            }
        });
        vAgpBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScrollY();
                if (vAgpBible.getText().toString().equals(blank))
                    return;
                agpShow ^= true;
                utils.popHistory();
                utils.generateBibleBody();
            }
        });
        vCevBible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScrollY();
                if (vCevBible.getText().toString().equals(blank))
                    return;
                cevShow ^= true;
                utils.popHistory();
                utils.generateBibleBody();
            }
        });
    }

    public void goBibleLeft() {
        int prevChapter = nowChapter - 1;
        if (prevChapter == 0) {   // prev bible required
            int prevBible = nowBible - 1;
            if (prevBible == 0)
                return;
            else {
                nowBible = prevBible;
                nowChapter = nbrofChapters[prevBible];
            }
        } else
            nowChapter = prevChapter;
        nowVerse = 1;
        utils.generateBibleBody();
    }

    public void goHymnLeft() {
        nowHymn--;
        utils.generateHymnBody();
    }

    public void goBibleRight() {
        int prevChapter = nowChapter + 1;
        if (prevChapter > nbrofChapters[nowBible]) {   // next bible required
            int prevBible = nowBible + 1;
            if (prevBible > 66) {
                return;
            } else {
                nowBible = prevBible;
                nowChapter = 1;
            }
        } else {
            nowChapter = prevChapter;
        }
        nowVerse = 1;
        utils.generateBibleBody();
    }

    public void goHymnRight() {
        nowHymn++;
        utils.generateHymnBody();
    }

    public void makeHymnMenu() {
        String txt = blank;
        if (nowHymn == 0) {
            vLeftAction.setText(txt);
            vRightAction.setText(txt);
            vCurrBible.setText(hymnName);
        }
        else {
            vLeftAction.setText((nowHymn > 1) ? "" + (nowHymn - 1):blank);
            vCurrBible.setText(hymnTitles[nowHymn]);
            vRightAction.setText((nowHymn < 645) ? "" + (nowHymn + 1):blank);
        }
    }

    public void makeBibleList() {
        nowChapter = 0;
        utils.showBibleList();
    }

    private void goBack2Back() {
        if (stackP == 1) {
            Toast.makeText(mContext,"맨 처음 입니다." , Toast.LENGTH_LONG).show();
        }
        else {
            utils.popHistory();
            utils.popHistory();
            if (topTab < TAB_MODE_HYMN && nowBible > 0) {
                utils.generateBibleBody();
            } else if (topTab == TAB_MODE_HYMN && nowHymn > 0) {
                utils.generateHymnBody();
            } else if (topTab == TAB_MODE_DIC) {
                utils.generateKeyWord();
            } else
                makeTopBottomMenu();
        }
    }

    private void goBack2Fore() {
        if (stackP == stackMax || !utils.shiftHistory()) {
            Toast.makeText(mContext,"맨 마지막 입니다" , Toast.LENGTH_LONG).show();
            return;
        }
        if (topTab < TAB_MODE_HYMN && nowBible > 0) {
            utils.generateBibleBody();
        } else if (topTab == TAB_MODE_HYMN && nowHymn > 0) {
            utils.generateHymnBody();
        } else if (topTab == TAB_MODE_DIC) {
            utils.generateKeyWord();
        } else
            makeTopBottomMenu();
    }

    void saveScrollY() {
//        nowVerse = - scrollView.getScrollY();
//        Log.w("saving ","Y "+nowVerse);
//        if (topTab == TAB_MODE_NEW || topTab == TAB_MODE_OLD) {
//            utils.setHistoryVerse();
//        }
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
        ArrayList result = new ArrayList();
        for (String perm : wanted) if (hasPermission(perm)) result.add(perm);
        return result;
    }
    private boolean hasPermission(@NonNull String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED);
        else
           return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            String msg = "These permissions are mandatory for the application. Please allow access.";
                            showDialog(msg);
                            return;
                        }
                    }
                } else {
                    Toast.makeText(mContext, "Permissions garanted.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    private void showDialog(String msg) {
        showMessageOKCancel(msg,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(permissionsRejected.toArray(
                                new String[0]), ALL_PERMISSIONS_RESULT);
                    }
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
    public void onBackPressed() {

        MainDialog mMainDialog;
//        Log.w("timegap", " " + (System.currentTimeMillis()-backKeyPressedTime));
//        Log.w("back", " topTab " + topTab + " bible " + nowBible+" chap "+ nowChapter+" hymn "+ nowHymn);
        if(System.currentTimeMillis()>backKeyPressedTime+500){
            backKeyPressedTime = System.currentTimeMillis();
            goBack2Back();
        }
        else{
            mMainDialog = new MainDialog();
            mMainDialog.show(getFragmentManager(), null);

//            quitApp();
        }
    }
   public static class MainDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(
                    getActivity());
            LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
            mBuilder.setView(mLayoutInflater
                    .inflate(R.layout.dialog_quit, null));
//            mBuilder.setTitle("by riopapa 2019/01/19");
//            mBuilder.setMessage("Dialog Messageeeeeee");
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

//
//        //add Url Span
//        URLSpan urlSpan = new URLSpan("https://www.google.com") {
//        spannableString.setSpan(urlSpan, 55, 78, 0);
//        SubscriptSpan(), 79, 89, 0);
//        SuperscriptSpan(), 92, 104, 0);
//        UnderlineSpan(), 104, 116, 0);
//        RelativeSizeSpan(1.5f), 116, 139, 0);
//        StrikethroughSpan(), 153, 170, 0);

//    private void quitApp()
//    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setIcon(R.mipmap.icon_riopapa_face);
//        builder.setMessage("리오파파 성경찬송 이젠 그만 볼래요?");
//        builder.setPositiveButton("그래요, 다음에 또 봅시다",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                        System.exit(0);
//                        android.os.Process.killProcess(android.os.Process.myPid());
//                    }
//                });
//        builder.setNegativeButton("앗, 잘못 눌렀군요",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
//                    }
//                });
//        builder.show();
//    }

