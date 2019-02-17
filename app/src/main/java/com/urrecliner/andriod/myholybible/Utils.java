package com.urrecliner.andriod.myholybible;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

import static android.graphics.Typeface.BOLD;
import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_DIC;
import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_NEW;
import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_OLD;
import static com.urrecliner.andriod.myholybible.Vars.agpColorB;
import static com.urrecliner.andriod.myholybible.Vars.agpColorF;
import static com.urrecliner.andriod.myholybible.Vars.agpShow;
import static com.urrecliner.andriod.myholybible.Vars.alwaysOn;
import static com.urrecliner.andriod.myholybible.Vars.cevColorB;
import static com.urrecliner.andriod.myholybible.Vars.cevColorF;
import static com.urrecliner.andriod.myholybible.Vars.cevShow;
import static com.urrecliner.andriod.myholybible.Vars.dictColorF;
import static com.urrecliner.andriod.myholybible.Vars.dictWord;
import static com.urrecliner.andriod.myholybible.Vars.editor;
import static com.urrecliner.andriod.myholybible.Vars.fullBibleNames;
import static com.urrecliner.andriod.myholybible.Vars.hymnImageShow;
import static com.urrecliner.andriod.myholybible.Vars.hymnTextShow;
import static com.urrecliner.andriod.myholybible.Vars.hymnTitles;
import static com.urrecliner.andriod.myholybible.Vars.logFile;
import static com.urrecliner.andriod.myholybible.Vars.mActivity;
import static com.urrecliner.andriod.myholybible.Vars.mBody;
import static com.urrecliner.andriod.myholybible.Vars.mContext;
import static com.urrecliner.andriod.myholybible.Vars.nbrofChapters;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.nowChapter;
import static com.urrecliner.andriod.myholybible.Vars.nowHymn;
import static com.urrecliner.andriod.myholybible.Vars.nowVerse;
import static com.urrecliner.andriod.myholybible.Vars.numberColorF;
import static com.urrecliner.andriod.myholybible.Vars.packageFolder;
import static com.urrecliner.andriod.myholybible.Vars.paraColorF;
import static com.urrecliner.andriod.myholybible.Vars.referColorF;
import static com.urrecliner.andriod.myholybible.Vars.scrollView;
import static com.urrecliner.andriod.myholybible.Vars.shortBibleNames;
import static com.urrecliner.andriod.myholybible.Vars.sortedNumbers;
import static com.urrecliner.andriod.myholybible.Vars.stackMax;
import static com.urrecliner.andriod.myholybible.Vars.stackP;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBible66;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleNumber;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleRefer;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleText;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleTitle;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnKeypad;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnText;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnTitle;
import static com.urrecliner.andriod.myholybible.Vars.topTab;
import static com.urrecliner.andriod.myholybible.Vars.verseColorF;
import static com.urrecliner.andriod.myholybible.Vars.xPixels;
import static java.lang.Integer.parseInt;

public class Utils {

    private MainActivity mainActivity;
    public Utils (MainActivity activity) {
        mainActivity = activity;
    }
    private final String newLine = "\n";
    private final String new2Line = "\n\n";
    private final String new3Line = "\n\n\n";
    public String[] readBibleFile(String filename) {
        String file2read = packageFolder + "/" + filename;
        String[] lines;
        try {
            lines = readLines(file2read);
            return lines;
        }
        catch(IOException e)
        {
            String message = filename+" 이 없거나, 파일읽기가 거부되어 있습니다.";
            new AlertDialog.Builder(mActivity)
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .create().show();
        }
        return null;
    }

    private String[] readLines(String filename) throws IOException {
        String EUC_KR = "EUC-KR";
        int BUFFER_SIZE = 81920;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), EUC_KR),BUFFER_SIZE);

        List<String> lines = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
//        return lines.toArray(new String[lines.size()]);
        return lines.toArray(new String[0]);
    }

    public void log(String tag, String text) {
        StackTraceElement[] traces;
        traces = Thread.currentThread().getStackTrace();
        String where = " " + traces[5].getMethodName() + " > " + traces[4].getMethodName() + " > " + traces[3].getMethodName() + " #" + traces[3].getLineNumber();
        Log.w(tag , where + " " + text);
    }

    public void logE(String tag, String text) {
        StackTraceElement[] traces;
        traces = Thread.currentThread().getStackTrace();
        String where = " " + traces[5].getMethodName() + " > " + traces[4].getMethodName() + " > " + traces[3].getMethodName() + " #" + traces[3].getLineNumber();
        Log.e("<" + tag + ">" , where + " " + text);
    }

    public void showBibleList() {

        scrollView = new ScrollView(mContext);
        int loop = (topTab == TAB_MODE_OLD) ?  39: 27;
        int start = (topTab == TAB_MODE_OLD) ? 1 : 40;
        int count = 1;
        Button b;
        final int nbrColumn = 3;
        int buttonWidth = xPixels / nbrColumn;
        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);
        for(int i = 0; i<15;i++) {
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearlayout.addView(rowLayout);
            for(int j = 0; j < nbrColumn; j++) {
                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setOrientation(LinearLayout.VERTICAL);
                b = new Button(mContext);
                b.setBackgroundResource(R.drawable.button_bible);
                b.setText(fullBibleNames[start]);
                b.setId(start);
                b.setWidth(buttonWidth);
                b.setTextSize(textSizeBible66);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                columnLayout.addView(b);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nowBible = v.getId();
                        mBody.removeAllViewsInLayout();
                        mBody.addView(buildBibleNumber());
                        mainActivity.makeTopBottomMenu();
                    }
                });
                rowLayout.addView(columnLayout);
                start++;
                count++;
                if (count > loop)
                    break;
            }
            if (count > loop)
                break;
        }
        TextView tVb = new TextView(mContext);
        tVb.setText(new3Line);
        tVb.setTextSize(textSizeBibleTitle);
        tVb.setWidth(xPixels);
        tVb.setTextColor(0);
        tVb.setGravity(Gravity.CENTER);
        linearlayout.addView(tVb);

        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }

    private ScrollView buildBibleNumber() {
        int verseMax = nbrofChapters[nowBible];
        int verse = 1;
        TextView tVNbr;
        scrollView = new ScrollView(mContext);
        final int nbrColumn = 5;
        int buttonWidth = xPixels / nbrColumn;
        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);
        TextView tV = new TextView(mContext);
        tV.setText(fullBibleNames[nowBible]);
        tV.setTextSize(textSizeBibleTitle);
        tV.setWidth(xPixels);
        tV.setTextColor(0);
        tV.setGravity(Gravity.CENTER);
        linearlayout.addView(tV);
        for(int i = 0; i<31;i++) {
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearlayout.addView(rowLayout);
            for(int j = 0; j < nbrColumn; j++) {
                LinearLayout columnLayout = new LinearLayout(mContext);
                tVNbr = new TextView(mContext);
                String text = ""+verse;
                tVNbr.setText(text);
                tVNbr.setTextColor(numberColorF);
                tVNbr.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                tVNbr.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                tVNbr.setWidth(buttonWidth);
                tVNbr.setGravity(Gravity.CENTER_HORIZONTAL);
                tVNbr.setPadding(0,16,0,16);
                tVNbr.setId(verse);
                tVNbr.setTextSize(textSizeBibleNumber);
                tVNbr.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                columnLayout.addView(tVNbr);
                tVNbr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nowChapter = v.getId();
                        nowVerse = 0;
                        generateBibleBody();
                    }
                });
                rowLayout.addView(columnLayout);
                verse++;
                if (verse > verseMax)
                    break;
            }
            if (verse > verseMax)
                break;
        }
        tV = new TextView(mContext);
        tV.setText(new2Line);
        tV.setTextSize(28);
        tV.setWidth(10);
        tV.setTextColor(0);
        linearlayout.addView(tV);

        return scrollView;
    }

    private int TABLE_SIZE = 500;

    private int keywordF[] = new int[TABLE_SIZE];           // ..F from byte pointer
    private int keywordT[] = new int[TABLE_SIZE];           // ..T to byte pointer
    private int keywordV[] = new int[TABLE_SIZE];           // ..V now Verse
    private String keywords[] = new String[TABLE_SIZE];
    private int iKeyword;

    private int VERSE_SIZE = 177; // max verse number is 176
    private int verseF[] = new int[VERSE_SIZE];
    private int verseT[] = new int[VERSE_SIZE];
    private int iVerse;

    private int referF[] = new int[TABLE_SIZE];
    private int referT[] = new int[TABLE_SIZE];
    private int referV[] = new int[TABLE_SIZE];
    private String refers[] = new String[TABLE_SIZE];
    private int iRefer;

    private int paraF[] = new int[30];
    private int paraT[] = new int[30];
    private int iPara;

    private int agpF[] = new int[VERSE_SIZE];
    private int agpT[] = new int[VERSE_SIZE];
    private int iAgp;

    private int cevF[] = new int[VERSE_SIZE];
    private int cevT[] = new int[VERSE_SIZE];
    private int iCev;

    private int versePtr;

    private int ptrBody;
    StringBuilder bodyText;

    void generateBibleBody() {

        final ScrollView scrollView = new ScrollView(mContext);
        String file2read = "bible/" + nowBible + "/" + nowChapter + ".txt";
        final String bibleTexts[] = readBibleFile(file2read);
        if (bibleTexts == null) {
            Toast.makeText(mContext, "Bible source not found " + fullBibleNames[nowBible] + " " + nowChapter,Toast.LENGTH_LONG).show();
            return;
        }
        iVerse = 0;
        iRefer = 0;
        iKeyword = 0;
        iPara = 0;
        iAgp = 0;
        iCev = 0;
        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.START);
        scrollView.addView(linearlayout);
        final TextView tV = new TextView(mContext);
        tV.setTextSize(textSizeBibleText);
        tV.setGravity(Gravity.START);
        tV.setWidth(xPixels);
        tV.setTextColor(ContextCompat.getColor(mContext,R.color._Black));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) linearlayout.getLayoutParams();
        lp.setMargins(20,16,20,16);
        linearlayout.setLayoutParams(lp);
        linearlayout.addView(tV);

        bodyText = new StringBuilder();
        bodyText.append(newLine);
        ptrBody = 1;
        generateBibleAllVerses(bibleTexts);
        bodyText.append(new3Line + new3Line);
        SpannableString ss = settleSpannableString();

        pushHistory();
        tV.setText(ss);
        tV.setMovementMethod(LinkMovementMethod.getInstance());
        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        nowHymn = 0;
        mainActivity.makeTopBottomMenu();
        Vars.scrollView = scrollView;
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, tV.getBottom() * versePtr / ptrBody);
            }
        });
    }

    @NonNull
    private SpannableString settleSpannableString() {
        SpannableString ss = new SpannableString(bodyText);
        for (int i = 0; i < iKeyword; i++) {
            ss.setSpan(new keywordSpan(keywords[i], keywordV[i]), keywordF[i], keywordT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < iVerse; i++) {
            ss.setSpan(new ForegroundColorSpan(verseColorF), verseF[i], verseT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(BOLD), verseF[i], verseT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < iPara; i++) {
            ss.setSpan(new ForegroundColorSpan(paraColorF), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new UnderlineSpan(), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(BOLD), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < iRefer; i++) {
            ss.setSpan(new referSpan(refers[i], referV[i]), referF[i], referT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < iCev; i++) {
            ss.setSpan(new ForegroundColorSpan(cevColorF), cevF[i], cevT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new BackgroundColorSpan(cevColorB), cevF[i], cevT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < iAgp; i++) {
            ss.setSpan(new ForegroundColorSpan(agpColorF), agpF[i], agpT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new BackgroundColorSpan(agpColorB), agpF[i], agpT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    private void generateBibleAllVerses(String bibleTexts[]) {
        int lines = bibleTexts.length;
        versePtr = 0;
        for (int line = 0; line < lines; line++) {
            if (line == (nowVerse-2))
                versePtr = ptrBody;
            String str;
            String workLine = bibleTexts[line] + "~";   // "~" is end character
            int lenWorkLine = workLine.length() - 1;
            int idx = workLine.indexOf("`a");
            int idx2nd = workLine.indexOf("`c");
            String agpText = workLine.substring(idx + 2, idx2nd);
            String cevText = workLine.substring(idx2nd + 2, lenWorkLine);
            workLine = workLine.substring(0, idx);
            lenWorkLine = workLine.length();
// bible script sample 
// {천지 창조}[_태초_]에 [_하나님_]이 [_천지_]를 [_창조_]하시니라[_#v43#1:3_][_$58#1:10_]
// `a태초에 하나님께서 하늘과 땅을 창조하셨습니다.
// `cIn the beginning God created the heavens and the earth.
            String c = workLine.substring(0, 1);
            if (c.equals("{")) {    // first column might have paragraph name
                int endIdx = workLine.indexOf("}");
                str = workLine.substring(1, endIdx);
                workLine = workLine.substring(endIdx + 1);
                lenWorkLine = workLine.length();
                bodyText.append(str);
                bodyText.append(newLine);
                paraF[iPara] = ptrBody;
                paraT[iPara] = ptrBody + str.length() + 1;
                iPara++;
                ptrBody += str.length() + 1;
            }
            str = " " + (line + 1) + " ";
            verseF[iVerse] = ptrBody;
            verseT[iVerse] = ptrBody + str.length();
            iVerse++;
            ptrBody += str.length();
            bodyText.append(str);

            while (lenWorkLine > 0) {
                idx = workLine.indexOf("[_");
                if (idx == -1) { // no more keyword
                    bodyText.append(workLine);
                    ptrBody += lenWorkLine;
                    break;
                } else {  // contains keyword
                    if (idx != 0) {   // has string before keyword
                        bodyText.append(workLine, 0, idx);
                        ptrBody += idx;
                        workLine = workLine.substring(idx);
                    }
                    // string starts with [_ & keyword
                    idx2nd = generateBibleKeyword(workLine, line + 1);
                    workLine = workLine.substring(idx2nd + 2);
                    lenWorkLine = workLine.length();
                }
            }
            if (agpShow) {
                agpText = " " + agpText;
                bodyText.append(agpText);
                agpF[iAgp] = ptrBody;
                agpT[iAgp] = ptrBody + agpText.length();
                iAgp++;
                ptrBody += agpText.length();
            }
            if (cevShow) {
                cevText = " " + cevText;
                bodyText.append(cevText);
                cevF[iCev] = ptrBody;
                cevT[iCev] = ptrBody + cevText.length();
                iCev++;
                ptrBody += cevText.length();
            }
            ptrBody++;
            bodyText.append(newLine);
        }
    }

    private int generateBibleKeyword(String workLine, int verse) {
        int ptr;
        ptr = workLine.indexOf("_]");
        String keyword = workLine.substring(2, ptr);
        if (keyword.substring(0, 1).equals("$")) {   // reference
            String bibShort = shortBibleNames[parseInt(keyword.substring(1, 3))];
            String showWord = " (" + bibShort + keyword.substring(4) + ") ";      // $01#12:34 -> (창12:34) 로 표시
            bodyText.append(showWord);
            referF[iRefer] = ptrBody;
            referT[iRefer] = ptrBody + showWord.length();
            referV[iRefer] = verse;
            refers[iRefer] = keyword.substring(1);  // save 01#12:34
            iRefer++;
            ptrBody += showWord.length();
        } else {  // keyword case
            int tilde = keyword.indexOf("~");
            if (isNewKeyword(keyword, keywords, iKeyword)) {
                keywordF[iKeyword] = ptrBody;
                keywordV[iKeyword] = verse;
                if (tilde != -1) {
                    keywordT[iKeyword] = ptrBody + tilde;
                    keywords[iKeyword] = keyword.substring(0, tilde) + keyword.substring(tilde + 1);
                    ptrBody += tilde;
                    bodyText.append(keyword,0, tilde);
                } else {
                    keywordT[iKeyword] = ptrBody + keyword.length();
                    keywords[iKeyword] = keyword;
                    ptrBody += keyword.length();
                    bodyText.append(keyword);
                }
                iKeyword++;
            } else {
                bodyText.append(keyword);
                ptrBody += keyword.length();
            }
        }
        return ptr;
    }

    private boolean isNewKeyword(String s, String keywords [], int iKeyword) {
        for (int i = 0 ; i < iKeyword; i++) {
            if (s.equals(keywords[i]))
                return false;
        }
        return true;
    }

    public class keywordSpan extends ClickableSpan {

        String key;
        int verse;
        public keywordSpan(String key, int verse) { this.key = key; this.verse = verse;}

        Typeface boldface = Typeface.create(Typeface.DEFAULT, BOLD);
        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(dictColorF);
            ds.setTypeface(boldface);
            ds.setTextSize(ds.getTextSize()+2);
        }

        @Override
        public void onClick(@NonNull View widget) {
            dictWord = key;
            nowVerse = verse;
            generateKeyWord();
        }
    }

    public class referSpan extends ClickableSpan{

        String key;
        int verse;
        public referSpan(String key, int verse) { this.key = key; this.verse = verse;}
        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(referColorF);
            ds.setTextSize(textSizeBibleRefer+textSizeBibleRefer);  // double size
            ds.setUnderlineText(false);    // this remove the underline
        }

        @Override
        public void onClick(@NonNull View widget) {
            dictWord = key;
            nowVerse = verse;
            generateRefer();
        }
    }

    private TextView tVTitle;
    private String hymnTitle = "";
    public void generateHymnKeypad() {

        scrollView = new ScrollView(mContext);
        int ids[] = {7,8,9,4,5,6,1,2,3,0,100,-1,200,-1,-1};
        Button b;

        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);
        TextView tV0 = new TextView(mContext);

        tV0.setText(newLine);
        tV0.setTextSize(textSizeHymnTitle);
        tV0.setTextColor(ContextCompat.getColor(mContext,R.color._Black));
        tV0.setGravity(Gravity.CENTER_HORIZONTAL);
        linearlayout.addView(tV0);

        tVTitle = new TextView(mContext);
        if (nowHymn != 0)
            hymnTitle = nowHymn + " : " + hymnTitles[nowHymn];
        else
            hymnTitle = "";
        tVTitle.setText(hymnTitle);
        tVTitle.setTextSize(textSizeHymnKeypad);
        tVTitle.setTextColor(ContextCompat.getColor(mContext,R.color.Charcoal));
        tVTitle.setGravity(Gravity.CENTER);
        tVTitle.setWidth(2000);
        linearlayout.addView(tVTitle);

        for(int i = 0; i<5;i++) {
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearlayout.addView(rowLayout);
            for(int j = 0; j < 3; j++) {
                int id = ids[i*3+j];
                if (id == -1)
                    break;
                String buttonText;
                int buttonWidth;
                if (id == 100) {
                    buttonWidth = 300;
                    buttonText = "Clear";
                }
                else if (id == 200) {
                    buttonWidth = 400;
                    buttonText = "Go";
                }
                else {
                    buttonText = "" + id;
                    buttonWidth = 100;
                }
                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                b = new Button(mContext);
                b.setBackgroundResource(R.drawable.button_number);
                b.setTextSize(textSizeHymnKeypad);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                b.setWidth(buttonWidth);
                b.setText(buttonText);
                columnLayout.addView(b);
                b.setId(id);
                if (id < 10) {
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        int nbr = v.getId();
                        if (nowHymn *10 + nbr <= 645) {
                            nowHymn = nowHymn * 10 + nbr;
                            hymnTitle = nowHymn + " " + hymnTitles[nowHymn];
                            tVTitle.setText(hymnTitle);
                        }
                        }
                    });
                }
                else if (id==100) { // clear
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        nowHymn = 0;
                        hymnTitle = "";
                        tVTitle.setText(hymnTitle);
                        }
                    });
                }
                else {    // go
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            generateHymnBody();
                        }
                    });
                }
                rowLayout.addView(columnLayout);
            }
        }
        TextView tVSort = new TextView(mContext);
        tVSort.setText(newLine);
        tVSort.setTextSize(textSizeHymnKeypad);
        tVSort.setTextColor(ContextCompat.getColor(mContext,R.color._Black));
        tVSort.setGravity(Gravity.CENTER);
        tVSort.setWidth(2000);
        linearlayout.addView(tVSort);

        for(int i = 0; i<4;i++) {
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearlayout.addView(rowLayout);
            for(int j = 0; j < 2; j++) {
                String text = hymnTitles[sortedNumbers[(i+i+j)*81]].substring(0,5);
                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                b = new Button(mContext);
                b.setTextSize(textSizeHymnText);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                b.setWidth(400);
                b.setText(text);
                columnLayout.addView(b);
                b.setId((i+i+j)*81);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    int nbr = v.getId();
                    generateSortedHymnList(nbr);
                    }
                });
                rowLayout.addView(columnLayout);
            }
        }

        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }

    public void generateHymnBody() {

        scrollView = new ScrollView(mContext);
        String txt = "Hymn/" + nowHymn + ".txt";
        String [] hymnTexts = readBibleFile(txt);
        if (hymnTexts == null) {
            Toast.makeText(mContext, "찬송가 " + nowHymn + " 파일 없음",Toast.LENGTH_LONG).show();
            return;
        }
        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);

        if (hymnImageShow) {
            File imgFile = new File(packageFolder, "hymn_png/" + nowHymn + ".pngz");
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                int height = xPixels * bitmap.getHeight() / bitmap.getWidth();
                ImageView imV = new ImageView(mContext);
                linearlayout.addView(imV);
                imV.setImageBitmap(Bitmap.createScaledBitmap(bitmap, xPixels, height, false));
                imV.requestLayout();
                PhotoViewAttacher pA;
                pA = new PhotoViewAttacher(imV);
                pA.update();
            }
        }
        if (hymnTextShow) {
            TextView tVBody = new TextView(mContext);
            tVBody.setTextSize(textSizeHymnText);
            tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
            tVBody.setWidth(xPixels);
            tVBody.setTextColor(ContextCompat.getColor(mContext,R.color._Black));
            linearlayout.addView(tVBody);

            bodyText = new StringBuilder();
            for (String hymnText : hymnTexts) {
                String workLine = newLine + hymnText;
                bodyText.append(workLine);
            }
            bodyText.append(new3Line + new2Line);
            SpannableString ssBody = new SpannableString(bodyText);
            tVBody.setText(ssBody);
            tVBody.setMovementMethod(LinkMovementMethod.getInstance());
        }
        pushHistory();
        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }

    public void generateSortedHymnList(int start) {

        scrollView = new ScrollView(mContext);

        TextView titleTV; TextView numberTV;
        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);
        TextView tV = new TextView(mContext);
        tV.setText("");
        tV.setTextSize(textSizeHymnText);
        tV.setWidth(xPixels);
        linearlayout.addView(tV);

        for(int i = 0; i<81;i++) {
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearlayout.addView(rowLayout);

            LinearLayout columnLayout = new LinearLayout(mContext);
            columnLayout.setOrientation(LinearLayout.HORIZONTAL);
            titleTV = new TextView(mContext);
            titleTV.setText(hymnTitles[sortedNumbers[start]]);
            titleTV.setTextColor(Color.BLACK);
            titleTV.setTextSize(textSizeHymnText);
            columnLayout.addView(titleTV);
            numberTV = new TextView(mContext);
            String text = "  " + sortedNumbers[start] + " ";
            numberTV.setText(text);
            numberTV.setId(sortedNumbers[start]);
            numberTV.setTextColor(paraColorF);
            numberTV.setTextSize(textSizeHymnText);
            numberTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            columnLayout.addView(numberTV);
            numberTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nowHymn = v.getId();
                    generateHymnBody();
                }
            });
            rowLayout.addView(columnLayout);
            start++;
            if (start > 644)
                break;
        }
        TextView tVb = new TextView(mContext);
        tVb.setText(new3Line);
        tVb.setTextSize(textSizeBibleTitle);
        tVb.setWidth(xPixels);
        tVb.setGravity(Gravity.CENTER);
        linearlayout.addView(tVb);

        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();

    }
    public void generateKeyWord() {

        int verse = nowVerse;
        String txt = "dict/" + dictWord + ".txt";
        popHistory();
        nowVerse = verse;
        pushHistory();
        scrollView = new ScrollView(mContext);
        String [] dicTexts = readBibleFile(txt);
        if (dicTexts != null) {
            LinearLayout linearlayout = new LinearLayout(mContext);
            linearlayout.setOrientation(LinearLayout.VERTICAL);
            linearlayout.setGravity(Gravity.START);
            scrollView.addView(linearlayout);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) linearlayout.getLayoutParams();
            lp.setMargins(20,36,20,16);
            linearlayout.setLayoutParams(lp);
            for (String line : dicTexts) {
                switch (line.substring(0, 1)) {
                    case "@":       // contains image file name
                        File imgFile = new File(packageFolder, "dict_img/" + line.substring(1));
                        if (imgFile.exists()) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            int height = xPixels * bitmap.getHeight() / bitmap.getWidth();
                            ImageView imV = new ImageView(mContext);
                            linearlayout.addView(imV);
                            imV.setImageBitmap(Bitmap.createScaledBitmap(bitmap, xPixels, height, false));
                            imV.requestLayout();
                            PhotoViewAttacher pA;
                            pA = new PhotoViewAttacher(imV);
                            pA.update();
                        }
                        break;
                    case "~": { // contains subject name
                        TextView tVLine = new TextView(mContext);
                        tVLine.setTextSize(textSizeBibleText + 3);
                        tVLine.setTextColor(Color.BLACK);
                        tVLine.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                        tVLine.setGravity(Gravity.CENTER_HORIZONTAL);
                        tVLine.setWidth(xPixels);
                        linearlayout.addView(tVLine);
                        tVLine.setText(line.substring(1));
                        break;
                    }
                    default: {
                        TextView tVLine = new TextView(mContext);
                        tVLine.setTextSize(textSizeBibleText);
                        tVLine.setTextColor(Color.BLACK);
                        tVLine.setGravity(Gravity.START);
                        tVLine.setWidth(xPixels);
                        linearlayout.addView(tVLine);
                        tVLine.setText(line);
                        break;
                    }
                }
            }
            TextView tVLine = new TextView(mContext);
            linearlayout.addView(tVLine);
            tVLine.setText(new3Line);
            TextView tVBottom = new TextView(mContext);
            linearlayout.addView(tVBottom);
            tVBottom.setText(new3Line);
        }
        else {
            String errText = "[" + dictWord + "] not found";
            Toast.makeText(mContext,errText,Toast.LENGTH_LONG).show();
            log(logFile, errText);
        }
        topTab = TAB_MODE_DIC;

        pushHistory();
        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }

    public void generateRefer() {

        String refer = dictWord; // 41#4:18
        int verse = nowVerse;
        popHistory();
        nowVerse = verse;
        pushHistory();
        nowBible = parseInt(refer.substring(0,2));
        topTab = (nowBible < 40) ? TAB_MODE_OLD : TAB_MODE_NEW;
        refer = refer.substring(3) + "z";
        char[] chars  = refer.toCharArray();
        int ptr = getNumberPtr(chars,0);
        nowChapter = parseInt(refer.substring(0,ptr));
        int ptr2 = getNumberPtr(chars,ptr+2);
        nowVerse = parseInt(refer.substring(ptr+1,ptr2));
        generateBibleBody();
    }

    private int getNumberPtr(char[] chars, int ptr) {
        for (int i = ptr ; i < chars.length; i++) {
            if (!Character.isDigit(chars[i]))
                return i;
        }
        return 0;
    }
    final private int TEXT_SIZE_BIBLE66_DECREASE = 1001;
    final private int TEXT_SIZE_BIBLE66_INCREASE = 1002;
    final private int TEXT_SIZE_BIBLE_DECREASE = 1011;
    final private int TEXT_SIZE_BIBLE_INCREASE = 1012;
    final private int TEXT_SIZE_REFER_DECREASE = 1021;
    final private int TEXT_SIZE_REFER_INCREASE = 1022;
    final private int TEXT_SIZE_HYMN_DECREASE = 1031;
    final private int TEXT_SIZE_HYMN_INCREASE = 1032;
    final private int HYMN_IMAGE_ON_OFF = 2005;
    final private int HYMN_TEXT_ON_OFF = 2006;
    final private int ALWAYS_ON_OFF = 3007;

    public void generateSettingBody() {

        final String yesShow = " 예 ";
        final String noShow = " 아니오 ";
        String text;
        scrollView = new ScrollView(mContext);
        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);

        TextView tVBody = new TextView(mContext);
        tVBody.setTextSize(20); // fixed
        tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
        tVBody.setWidth(xPixels);
        tVBody.setTextColor(ContextCompat.getColor(mContext,R.color._Black));
        linearlayout.addView(tVBody);

        iRefer = 0;
        bodyText = new StringBuilder();
        ptrBody = 0;
        text = new2Line + mainActivity.getString(R.string.bible_name_size); bodyText.append(text); ptrBody += text.length();
        appendSetting("  -   ", TEXT_SIZE_BIBLE66_DECREASE);
        text = "  " + textSizeBible66 + "  ";  bodyText.append(text); ptrBody += text.length();
        appendSetting("   +   ", TEXT_SIZE_BIBLE66_INCREASE);

        text = new2Line + mainActivity.getString(R.string.scripture_size); bodyText.append(text); ptrBody += text.length();
        appendSetting("  -   ", TEXT_SIZE_BIBLE_DECREASE);
        text = "  " + textSizeBibleText + "  ";  bodyText.append(text); ptrBody += text.length();
        appendSetting("   +   ", TEXT_SIZE_BIBLE_INCREASE);

        text = new2Line + mainActivity.getString(R.string.bible_crossing_size); bodyText.append(text); ptrBody += text.length();
        appendSetting("  -   ", TEXT_SIZE_REFER_DECREASE);
        text = "  " + textSizeBibleRefer + "  ";  bodyText.append(text); ptrBody += text.length();
        appendSetting("   +   ", TEXT_SIZE_REFER_INCREASE);

        text = new2Line + mainActivity.getString(R.string.wanna_show_hymn_sheet); bodyText.append(text); ptrBody += text.length();
        appendSetting((hymnImageShow)? noShow:yesShow, HYMN_IMAGE_ON_OFF);

        text = new2Line + mainActivity.getString(R.string.wanna_show_hymn_lyric); bodyText.append(text); ptrBody += text.length();
        if (hymnTextShow) {
            appendSetting(noShow, HYMN_TEXT_ON_OFF);
            text = new2Line + mainActivity.getString(R.string.hymn_lyric_size); bodyText.append(text); ptrBody += text.length();
            appendSetting("  -   ", TEXT_SIZE_HYMN_DECREASE);
            text = "  " + textSizeHymnText + "  ";  bodyText.append(text); ptrBody += text.length();
            appendSetting("   +   ", TEXT_SIZE_HYMN_INCREASE);
        }
        else
            appendSetting(yesShow, HYMN_TEXT_ON_OFF);
        text = new2Line + mainActivity.getString(R.string.keep_screen_on); bodyText.append(text); ptrBody += text.length();
        appendSetting((alwaysOn)? noShow:yesShow, ALWAYS_ON_OFF);

        bodyText.append(new3Line);
        SpannableString ss = settleSettingSpan();
        tVBody.setText(ss);
        tVBody.setMovementMethod(LinkMovementMethod.getInstance());
        pushHistory();

        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        mainActivity.clearMenu();
    }

    private void appendSetting(String text, int what2do) {
        bodyText.append(text); referV[iRefer] = what2do; referF[iRefer] = ptrBody;
        ptrBody += text.length(); referT[iRefer] = ptrBody;
        iRefer++;
    }

    @NonNull
    private SpannableString settleSettingSpan() {
        SpannableString ss = new SpannableString(bodyText);
        for (int i = 0; i < iRefer; i++) {
            ss.setSpan(new BackgroundColorSpan(cevColorB), referF[i], referT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new settingSpan(referV[i]), referF[i], referT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    public class settingSpan extends ClickableSpan{

        int what;
        Typeface typeface = Typeface.create(Typeface.DEFAULT, BOLD);
        public settingSpan(int what) { this.what = what;}
        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setTypeface(typeface);
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(false);    // this remove the underline
        }

        @Override
        public void onClick(@NonNull View widget) {
            switch (what) {

                case TEXT_SIZE_BIBLE66_DECREASE:
                    textSizeBible66--;
                    editor.putInt("textSizeBible66", textSizeBible66).apply();
                    break;
                case TEXT_SIZE_BIBLE66_INCREASE:
                    textSizeBible66++;
                    editor.putInt("textSizeBible66", textSizeBible66).apply();
                    break;
                case TEXT_SIZE_BIBLE_DECREASE:
                    textSizeBibleText--;
                    editor.putInt("textSizeBibleText", textSizeBibleText).apply();
                    break;
                case TEXT_SIZE_BIBLE_INCREASE:
                    textSizeBibleText++;
                    editor.putInt("textSizeBibleText", textSizeBibleText).apply();
                    break;
                case TEXT_SIZE_REFER_DECREASE:
                    textSizeBibleRefer--;
                    editor.putInt("textSizeBibleRefer", textSizeBibleRefer).apply();
                    break;
                case TEXT_SIZE_REFER_INCREASE:
                    textSizeBibleRefer++;
                    editor.putInt("textSizeBibleRefer", textSizeBibleRefer).apply();
                    break;
                case TEXT_SIZE_HYMN_DECREASE:
                    textSizeHymnText--;
                    editor.putInt("textSizeHymnText", textSizeHymnText).apply();
                    break;
                case TEXT_SIZE_HYMN_INCREASE:
                    textSizeHymnText++;
                    editor.putInt("textSizeHymnText", textSizeHymnText).apply();
                    break;
                case HYMN_IMAGE_ON_OFF:
                    hymnImageShow ^= true;
                    editor.putBoolean("hymnImageShow", hymnImageShow).apply();
                    break;
                case HYMN_TEXT_ON_OFF:
                    hymnTextShow ^= true;
                    editor.putBoolean("hymnTextShow", hymnTextShow).apply();
                    break;
                case ALWAYS_ON_OFF:
                    alwaysOn ^= true;
                    editor.putBoolean("alwaysOn", alwaysOn).apply();
                    if (alwaysOn)
                        mainActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    else
                        mainActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    break;
            }
            generateSettingBody();
        }
    }

    private int STACK_SIZE = 40;
    private int topTabStack [] = new int[STACK_SIZE];
    private int bibleStack [] = new int[STACK_SIZE];
    private int chapterStack [] = new int[STACK_SIZE];
    private int verseStack [] = new int[STACK_SIZE];
    private int hymnStack[] = new int[STACK_SIZE];
    private String keyStack[] = new String[STACK_SIZE];
    
    void pushHistory() {
        if (stackP > 38) {
            for (int i = 0; i < 35; i++) {
                topTabStack[i] = topTabStack[i+4];
                bibleStack[i] = bibleStack[i+4];
                chapterStack[i] = chapterStack[i+4];
                verseStack[i] = verseStack[i+4];
                hymnStack[i] = hymnStack[i+4];
                keyStack[i] = keyStack[i+4];
            }
            stackP -= 4;
            stackMax -= 4;
        }
        topTabStack[stackP] = topTab;
        bibleStack[stackP] = nowBible;
        chapterStack[stackP] = nowChapter;
        verseStack[stackP] = nowVerse;
        hymnStack[stackP] = nowHymn;
        keyStack[stackP] = dictWord;
        stackP++;
        if (stackP > stackMax)
            stackMax++;
    }
    void popHistory() {
        if (stackP > 0) {
            stackP--;
        }
        topTab = topTabStack[stackP];
        nowBible = bibleStack[stackP];
        nowChapter = chapterStack[stackP];
        nowVerse = verseStack[stackP];
        nowHymn = hymnStack[stackP];
        dictWord = keyStack[stackP];
    }
    boolean shiftHistory() {
        if (topTabStack[stackP+1] > 0) {
            stackP++;
            topTab = topTabStack[stackP];
            nowBible = bibleStack[stackP];
            nowChapter = chapterStack[stackP];
            nowVerse = verseStack[stackP];
            nowHymn = hymnStack[stackP];
            dictWord = keyStack[stackP];
            return true;
        }
        return false;
    }
}
