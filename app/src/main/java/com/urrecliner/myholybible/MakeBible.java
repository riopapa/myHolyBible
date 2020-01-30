package com.urrecliner.myholybible;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;

import static android.graphics.Typeface.BOLD;
import static com.urrecliner.myholybible.Vars.TAB_MODE_DIC;
import static com.urrecliner.myholybible.Vars.TAB_MODE_NEW;
import static com.urrecliner.myholybible.Vars.TAB_MODE_OLD;
import static com.urrecliner.myholybible.Vars.agpColorFore;
import static com.urrecliner.myholybible.Vars.agpShow;
import static com.urrecliner.myholybible.Vars.bibleColorFore;
import static com.urrecliner.myholybible.Vars.bibleTexts;
import static com.urrecliner.myholybible.Vars.blackMode;
import static com.urrecliner.myholybible.Vars.cevColorFore;
import static com.urrecliner.myholybible.Vars.cevShow;
import static com.urrecliner.myholybible.Vars.dicColorFore;
import static com.urrecliner.myholybible.Vars.fullBibleNames;
import static com.urrecliner.myholybible.Vars.history;
import static com.urrecliner.myholybible.Vars.logFile;
import static com.urrecliner.myholybible.Vars.mActivity;
import static com.urrecliner.myholybible.Vars.mBody;
import static com.urrecliner.myholybible.Vars.mContext;
import static com.urrecliner.myholybible.Vars.mainActivity;
import static com.urrecliner.myholybible.Vars.mainScreen;
import static com.urrecliner.myholybible.Vars.makeBible;
import static com.urrecliner.myholybible.Vars.maxVerse;
import static com.urrecliner.myholybible.Vars.nbrOfChapters;
import static com.urrecliner.myholybible.Vars.nowBible;
import static com.urrecliner.myholybible.Vars.nowChapter;
import static com.urrecliner.myholybible.Vars.nowDic;
import static com.urrecliner.myholybible.Vars.nowHymn;
import static com.urrecliner.myholybible.Vars.nowScrollView;
import static com.urrecliner.myholybible.Vars.nowVerse;
import static com.urrecliner.myholybible.Vars.numberColorFore;
import static com.urrecliner.myholybible.Vars.packageFolder;
import static com.urrecliner.myholybible.Vars.paraColorFore;
import static com.urrecliner.myholybible.Vars.referColorFore;
import static com.urrecliner.myholybible.Vars.shortBibleNames;
import static com.urrecliner.myholybible.Vars.textColorBack;
import static com.urrecliner.myholybible.Vars.textSizeBible66;
import static com.urrecliner.myholybible.Vars.textSizeBibleBody;
import static com.urrecliner.myholybible.Vars.textSizeBibleNumber;
import static com.urrecliner.myholybible.Vars.textSizeBibleRefer;
import static com.urrecliner.myholybible.Vars.textSizeBibleTitle;
import static com.urrecliner.myholybible.Vars.textSizeKeyWord;
import static com.urrecliner.myholybible.Vars.textSizeSpace;
import static com.urrecliner.myholybible.Vars.topTab;
import static com.urrecliner.myholybible.Vars.utils;
import static com.urrecliner.myholybible.Vars.verseColorFore;
import static com.urrecliner.myholybible.Vars.xPixels;
import static java.lang.Integer.parseInt;

class MakeBible {


    private final String newLine = "\n";
    private final String new3Line = "\n\n\n";
    private ScrollView scrollView;

    void showBibleList() {
        if (makeBible == null)
            makeBible = this;
        scrollView = new ScrollView(mContext);
        scrollView.setBackgroundColor(textColorBack);
        mainScreen.setBackgroundColor(textColorBack);
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
                b.setBackgroundResource((blackMode)? R.drawable.button_bible_dark:R.drawable.button_bible);
                b.setText(fullBibleNames[start]);
                b.setId(start);
                b.setWidth(buttonWidth);
                b.setTextSize(textSizeBible66);
                b.setTextColor((blackMode)? mActivity.getColor(R.color.TextBackColor) : mActivity.getColor(R.color.bibleColorFore));
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
        int verseMax = nbrOfChapters[nowBible];
        int verse = 1;
        TextView tVNbr;
        scrollView = new ScrollView(mContext);
        scrollView.setBackgroundColor(textColorBack);
        mainScreen.setBackgroundColor(textColorBack);
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
                tVNbr.setTextColor(numberColorFore);
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
                        MakeBibleBody();
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
        return scrollView;
    }

    private int TABLE_SIZE = 500;

    private int [] keywordF = new int[TABLE_SIZE];           // ..F from byte pointer
    private int [] keywordT = new int[TABLE_SIZE];           // ..T to byte pointer
    private int [] keywordV = new int[TABLE_SIZE];           // ..V now Verse
    private String [] keywords = new String[TABLE_SIZE];
    private int idxKeyword;

    private int VERSE_SIZE = 180; // max verse number is 176
    private int []verseF = new int[VERSE_SIZE];
    private int []verseT = new int[VERSE_SIZE];
    private int idxVerse;

    private int [] crossF = new int[TABLE_SIZE];
    private int [] crossT = new int[TABLE_SIZE];
    private int [] crossV = new int[TABLE_SIZE];
    private String [] crossS = new String[TABLE_SIZE];
    private int idxRefer;

    private int [] paraF = new int[30];
    private int [] paraT = new int[30];
    private int idxPara;

    private int [] agpF = new int[VERSE_SIZE];
    private int [] agpT = new int[VERSE_SIZE];
    private int idxAgp;

    private int [] cevF = new int[VERSE_SIZE];
    private int [] cevT = new int[VERSE_SIZE];
    private int idxCev;

    private int [] spaceF = new int[VERSE_SIZE];
    private int [] spaceT = new int[VERSE_SIZE];
    private int idxSpace;

    private int versePtr;

    private int ptrBody;
    private StringBuilder bodyText;

    void MakeBibleBody() {
        scrollView = new ScrollView(mContext);
        scrollView.setBackgroundColor(textColorBack);
        mainScreen.setBackgroundColor(textColorBack);
        String file2read = "bible/" + nowBible + "/" + nowChapter + ".txt";
        bibleTexts = utils.readBibleFile(file2read);
        if (bibleTexts == null) {
            Toast.makeText(mContext, "Bible source not found " + fullBibleNames[nowBible] + " " + nowChapter,Toast.LENGTH_LONG).show();
            return;
        }
        idxVerse = 0;
        idxRefer = 0;
        idxKeyword = 0;
        idxPara = 0;
        idxAgp = 0;
        idxCev = 0;
        idxSpace = 0;
        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.START);
        scrollView.addView(linearlayout);
        final TextView tV = new TextView(mContext);
        tV.setTextSize(textSizeBibleBody);
        tV.setGravity(Gravity.START);
        tV.setWidth(xPixels);
        tV.setTextColor(bibleColorFore);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) linearlayout.getLayoutParams();
        lp.setMargins(20,16,20,16);
        linearlayout.setLayoutParams(lp);
        linearlayout.addView(tV);

        bodyText = new StringBuilder();
        bodyText.append(newLine);
        ptrBody = 1;
        makeBibleAllVerses();
        bodyText.append(new3Line + new3Line);
        SpannableString ss = settleSpannableString();

        history.push();
        tV.setText(ss);
        tV.setMovementMethod(LinkMovementMethod.getInstance());
        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        nowHymn = 0;
        mainActivity.makeTopBottomMenu();
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, tV.getBottom() * versePtr / ptrBody);
            }
        });
        nowScrollView = scrollView;
    }

    @NonNull
    private SpannableString settleSpannableString() {
        SpannableString ss = new SpannableString(bodyText);
        for (int i = 0; i < idxKeyword; i++) {
            ss.setSpan(new keywordSpan(keywords[i], keywordV[i]), keywordF[i], keywordT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxVerse; i++) {
            ss.setSpan(new ForegroundColorSpan(verseColorFore), verseF[i], verseT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(BOLD), verseF[i], verseT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxPara; i++) {
            ss.setSpan(new ForegroundColorSpan(paraColorFore), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ss.setSpan(new UnderlineSpan(), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(BOLD), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxRefer; i++) {
            ss.setSpan(new crossSpan(crossS[i], crossV[i]), crossF[i], crossT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxCev; i++) {
            ss.setSpan(new ForegroundColorSpan(cevColorFore), cevF[i], cevT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ss.setSpan(new BackgroundColorSpan(cevColorBlack), cevF[i], cevT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxAgp; i++) {
            ss.setSpan(new ForegroundColorSpan(agpColorFore), agpF[i], agpT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ss.setSpan(new BackgroundColorSpan(agpColorBlack), agpF[i], agpT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < idxSpace; i++) {
            ss.setSpan(new AbsoluteSizeSpan(textSizeSpace), spaceF[i], spaceT[i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    private void makeBibleAllVerses() {
        maxVerse  = bibleTexts.length;
        versePtr = 0;
        for (int line = 0; line < maxVerse; line++) {
            if (line == (nowVerse-2))
                versePtr = ptrBody;
            String str;
            String workLine = bibleTexts[line] + "~";   // "~" is end character
            int lenWorkLine = workLine.length() - 1;
            int idx = workLine.indexOf("`a");
            int idx2nd = workLine.indexOf("`c");
            String agpText = workLine.substring(idx + 2, idx2nd);
            if (agpText.length() == 0)
                agpText = " ";
            String cevText = workLine.substring(idx2nd + 2, lenWorkLine);
            if (cevText.length() == 0)
                cevText = " ";
            workLine = workLine.substring(0, idx);
            String verseString = "" + (line+1);
            if (line < maxVerse-1) {
                String nextLine = bibleTexts[line+1].substring(0, bibleTexts[line+1].indexOf("`a")).trim();
                if (nextLine.length() == 0)
                    verseString += "-" +(line+2);
            }
            lenWorkLine = workLine.length();
            if (lenWorkLine > 0) {

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
                    paraF[idxPara] = ptrBody;
                    paraT[idxPara] = ptrBody + str.length() + 1;
                    idxPara++;
                    ptrBody += str.length() + 1;
                    spaceF[idxSpace] = ptrBody;
                    bodyText.append(" " + newLine);
                    spaceT[idxSpace] = ptrBody + 2;
                    ptrBody += 2;
                    idxSpace++;
                }
                str = " " + verseString + " ";
                verseF[idxVerse] = ptrBody;
                verseT[idxVerse] = ptrBody + str.length();
                idxVerse++;
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
                        idx2nd = makeBibleKeyword(workLine, line + 1);
                        workLine = workLine.substring(idx2nd + 2);
                        lenWorkLine = workLine.length();
                    }
                }
                if (agpShow) {
                    agpText = newLine + agpText;
                    bodyText.append(agpText);
                    agpF[idxAgp] = ptrBody;
                    agpT[idxAgp] = ptrBody + agpText.length();
                    idxAgp++;
                    ptrBody += agpText.length();
                }
                if (cevShow) {
                    cevText = newLine + cevText;
                    bodyText.append(cevText);
                    cevF[idxCev] = ptrBody;
                    cevT[idxCev] = ptrBody + cevText.length();
                    idxCev++;
                    ptrBody += cevText.length();
                }
                ptrBody++;
                bodyText.append(newLine);
                spaceF[idxSpace] = ptrBody;
                bodyText.append(" " + newLine);
                spaceT[idxSpace] = ptrBody + 2;
                ptrBody += 2;
                idxSpace++;
            }
        }
    }

    private int makeBibleKeyword(String workLine, int verse) {
        int ptr;
        ptr = workLine.indexOf("_]");
        String keyword = workLine.substring(2, ptr);
        if (keyword.substring(0, 1).equals("$")) {   // reference
            String bibShort = shortBibleNames[parseInt(keyword.substring(1, 3))];
            String showWord = " (" + bibShort + keyword.substring(4) + ") ";      // $01#12:34 -> (창12:34) 로 표시
            bodyText.append(showWord);
            crossF[idxRefer] = ptrBody;
            crossT[idxRefer] = ptrBody + showWord.length();
            crossV[idxRefer] = verse;
            crossS[idxRefer] = keyword.substring(1);  // save 01#12:34
            idxRefer++;
            ptrBody += showWord.length();
        } else {  // keyword case
            int tilde = keyword.indexOf("~");
            String searchKey = (tilde != -1)? keyword.substring(0, tilde) + keyword.substring(tilde + 1) : keyword;
            if (isNewKeyword(searchKey, keywords, idxKeyword)) {
                keywordF[idxKeyword] = ptrBody;
                keywordV[idxKeyword] = verse;
                if (tilde != -1) {
                    keywordT[idxKeyword] = ptrBody + tilde;
                    keywords[idxKeyword] = searchKey;
                    ptrBody += tilde;
                    bodyText.append(keyword,0, tilde);
                } else {
                    keywordT[idxKeyword] = ptrBody + keyword.length();
                    keywords[idxKeyword] = keyword;
                    ptrBody += keyword.length();
                    bodyText.append(keyword);
                }
                idxKeyword++;
            } else {
                if (tilde != -1)
                    searchKey = searchKey.substring(0,searchKey.length()-1);
                bodyText.append(searchKey);
                ptrBody += searchKey.length();
            }
        }
        return ptr;
    }

    private boolean isNewKeyword(String s, String [] keywords, int iKeyword) {
        for (int i = 0 ; i < iKeyword; i++) {
            if (s.equals(keywords[i]))
                return false;
        }
        return true;
    }

    public class keywordSpan extends ClickableSpan {

        String key;
        int verse;
        keywordSpan(String key, int verse) { this.key = key; this.verse = verse;}

        Typeface boldface = Typeface.create(Typeface.DEFAULT, BOLD);
        Float dicTextSize = textSizeKeyWord * 2.6f;
        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(dicColorFore);
            ds.setTypeface(boldface);
            ds.setTextSize(dicTextSize);
        }

        @Override
        public void onClick(@NonNull View widget) {
            nowDic = key;
            nowVerse = verse;
            makeKeyWord();
        }
    }

    public class crossSpan extends ClickableSpan {

        String key;
        int verse;
        crossSpan(String key, int verse) { this.key = key; this.verse = verse;}
        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(referColorFore);
            ds.setTextSize(textSizeBibleRefer+textSizeBibleRefer);  // double size
            ds.setUnderlineText(false);    // this remove the underline
        }

        @Override
        public void onClick(@NonNull View widget) {
            nowDic = key;
            nowVerse = verse;
            makeCrossing();
        }
    }

    void makeKeyWord() {

        int verse = nowVerse;
        String txt = "dict/" + nowDic + ".txt";
        history.pop();
        nowVerse = verse;
        history.push();
        scrollView = new ScrollView(mContext);
        scrollView.setBackgroundColor(textColorBack);
        mainScreen.setBackgroundColor(textColorBack);
        String [] dicTexts = utils.readBibleFile(txt);
        if (dicTexts != null) {
            LinearLayout linearlayout = new LinearLayout(mContext);
            linearlayout.setOrientation(LinearLayout.VERTICAL);
            linearlayout.setGravity(Gravity.START);
            scrollView.addView(linearlayout);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) linearlayout.getLayoutParams();
            lp.setMargins(60,36,60,40);
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
                        tVLine.setTextSize(textSizeBibleBody + 3);
                        tVLine.setTextColor(bibleColorFore);
                        tVLine.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                        tVLine.setGravity(Gravity.CENTER_HORIZONTAL);
                        tVLine.setWidth(xPixels);
                        linearlayout.addView(tVLine);
                        tVLine.setText(line.substring(1));
                        break;
                    }
                    default: {
                        TextView tVLine = new TextView(mContext);
                        tVLine.setTextSize(textSizeBibleBody);
                        tVLine.setTextColor(bibleColorFore);
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
            String errText = "[" + nowDic + "] not found";
            Toast.makeText(mContext,errText,Toast.LENGTH_LONG).show();
            utils.log(logFile, errText);
        }
        topTab = TAB_MODE_DIC;

        history.push();
        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }

    private void makeCrossing() {

        String cross = nowDic; // 41#4:18
        int verse = nowVerse;
        history.pop();
        nowVerse = verse;
        history.push();
        nowBible = parseInt(cross.substring(0,2));
        topTab = (nowBible < 40) ? TAB_MODE_OLD : TAB_MODE_NEW;
        cross = cross.substring(3) + "z";
        char[] chars  = cross.toCharArray();
        int ptr = getNumberPtr(chars,0);
        nowChapter = parseInt(cross.substring(0,ptr));
        int ptr2 = getNumberPtr(chars,ptr+2);
        nowVerse = parseInt(cross.substring(ptr+1,ptr2));
        MakeBibleBody();
    }

    private int getNumberPtr(char[] chars, int ptr) {
        for (int i = ptr ; i < chars.length; i++) {
            if (!Character.isDigit(chars[i]))
                return i;
        }
        return 0;
    }

}
