package com.urrecliner.andriod.myholybible;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.graphics.Typeface.BOLD;
import static com.urrecliner.andriod.myholybible.Vars.editor;
import static com.urrecliner.andriod.myholybible.Vars.hymnImageShow;
import static com.urrecliner.andriod.myholybible.Vars.hymnTextShow;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.nowChapter;
import static com.urrecliner.andriod.myholybible.Vars.nowVerse;
import static com.urrecliner.andriod.myholybible.Vars.paraColorF;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBible66;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleNumber;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleRefer;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleText;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleTitle;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnKeypad;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnText;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnTitle;
import static com.urrecliner.andriod.myholybible.Vars.textSizeKeyword;
import static com.urrecliner.andriod.myholybible.Vars.topTab;
import static java.lang.Integer.parseInt;

public class Utils {

    private MainActivity mainActivity;
    public Utils (MainActivity activity) {
        mainActivity = activity;
    }
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss sss", Locale.KOREA);

    public String[] readBibleFile(String filename) {
        String file2read = Vars.packageFolder + "/" + filename;
        String[] lines;
        try {
            lines = readLines(file2read);
            return lines;
        }
        catch(IOException e)
        {
            // Print out the exception that occurred
            logE("file read", "Unable to read "+filename+": "+e.getMessage());
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

    private String getTimeStamp() {
        return timeFormat.format(new Date());
    }

    public void append2file(String filename, String textLine) {

        Log.w(filename,textLine);
        File directoryDate = getTodayFolder();

        BufferedWriter bw = null;
        FileWriter fw = null;
        String fullName = directoryDate.toString() + "/" + filename;

        try {
            File file = new File(fullName);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    logE("createFile", " Error");
                }
            }
            StackTraceElement[] traces;
            traces = Thread.currentThread().getStackTrace();
            String outText = "\n" + getTimeStamp() + " " + traces[5].getMethodName() + " > " + traces[4].getMethodName() + " > " + traces[3].getMethodName() + " #" + traces[3].getLineNumber() + " [[" + textLine + "]]\n";
            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(outText);

//            for (int i = 3; i < 8; i++) {
//                bw.write("                ".substring(0, i ) + "+- " + i + traces[i].getMethodName() + "#" +traces[i].getLineNumber() + "\n");
//            }
            //            Toast.makeText(getApplicationContext(), "File wrote to " + fullName,
//                    Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) bw.close();
                if (fw != null) fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getTodayFolder() {
        try {
            if (!Vars.packageFolder.exists()) {
                Vars.packageFolder.mkdirs();
            }
        } catch (Exception e) {
            Log.e("creating Directory error", Vars.packageFolder.toString() + "_" + e.toString());
        }

        File directoryDate = new File(Vars.packageFolder, dateFormat.format(new Date()));
        try {
            if (!directoryDate.exists()) {
                if (directoryDate.mkdirs())
                    log("Directory", directoryDate.toString() + " created ");
            }
        } catch (Exception e) {
            logE("creating Folder error", directoryDate + "_" + e.toString());
        }
        return directoryDate;
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

    public void customToast  (String text, int length) {

        Toast toast = Toast.makeText(Vars.mContext, text, length);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0,0);
        View toastView = toast.getView(); // This'll return the default View of the Toast.

        /* And now you can get the TextView of the default View of the Toast. */
        TextView toastMessage = toastView.findViewById(android.R.id.message);
        toastMessage.setTextSize(12);
        toastMessage.setTextColor(Color.BLACK);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_launcher, 0, 0, 0);
        toastMessage.setGravity(Gravity.CENTER_VERTICAL);
        toastMessage.setCompoundDrawablePadding(8);
        toastMessage.setPadding(4,4,24,4);
        toastView.setBackgroundColor(Color.YELLOW);
        toast.show();
    }

    public void showBibleList() {

        ScrollView scrollView = new ScrollView(Vars.mContext);
        int loop = (topTab == Vars.TABMODE_OLD) ?  39: 27;
        int start = (topTab == Vars.TABMODE_OLD) ? 1 : 40;
        int count = 1;
        Button b;
        final int nbrColumn = 3;
        int buttonWidth = Vars.xPixels / nbrColumn;
        LinearLayout linearlayout = new LinearLayout(Vars.mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);
//        TextView tV = new TextView(Vars.mContext);
//        tV.setText(Vars.blank);
//        tV.setTextSize(textSizeBibleTitle);
//        tV.setWidth(Vars.xPixels);
//        tV.setTextColor(Color.parseColor("#000000"));
//        tV.setGravity(Gravity.CENTER);
//        linearlayout.addView(tV);
        for(int i = 0; i<15;i++) {
            LinearLayout rowLayout = new LinearLayout(Vars.mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearlayout.addView(rowLayout);
            for(int j = 0; j < nbrColumn; j++) {
                LinearLayout columnLayout = new LinearLayout(Vars.mContext);
                columnLayout.setOrientation(LinearLayout.VERTICAL);
                b = new Button(Vars.mContext);
                b.setBackgroundResource(R.drawable.button_bible);
                b.setText(Vars.fullBibleNames[start]);
                b.setId(start);
                b.setWidth(buttonWidth);
                b.setTextSize(textSizeBible66);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                columnLayout.addView(b);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Vars.nowBible = v.getId();
                        Vars.mContainerBody.removeAllViewsInLayout();
                        Vars.mContainerBody.addView(buildBibleNumber());
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
        TextView tVb = new TextView(Vars.mContext);
        tVb.setText("\n\n\n");
        tVb.setTextSize(textSizeBibleTitle);
        tVb.setWidth(Vars.xPixels);
        tVb.setTextColor(Color.parseColor("#000000"));
        tVb.setGravity(Gravity.CENTER);
        linearlayout.addView(tVb);

        Vars.mContainerBody.removeAllViewsInLayout();
        Vars.mContainerBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }

    private ScrollView buildBibleNumber() {
        int loop = Vars.nbrofChapters[Vars.nowBible];
        int count = 1;
        TextView b;
        ScrollView scrollView = new ScrollView(Vars.mContext);
        final int nbrColumn = 5;
        int buttonWidth = Vars.xPixels / nbrColumn;
        LinearLayout linearlayout = new LinearLayout(Vars.mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);
        TextView tV = new TextView(Vars.mContext);
        tV.setText(Vars.fullBibleNames[Vars.nowBible]);
        tV.setTextSize(textSizeBibleTitle);
        tV.setWidth(Vars.xPixels);
        tV.setTextColor(Color.parseColor("#000000"));
        tV.setGravity(Gravity.CENTER);
        linearlayout.addView(tV);
        for(int i = 0; i<31;i++) {
            LinearLayout rowLayout = new LinearLayout(Vars.mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearlayout.addView(rowLayout);
            for(int j = 0; j < nbrColumn; j++) {
                LinearLayout columnLayout = new LinearLayout(Vars.mContext);
                b = new TextView(Vars.mContext);
                b.setText(""+count);
                b.setTextColor(paraColorF);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                b.setWidth(buttonWidth);
                b.setGravity(Gravity.CENTER_HORIZONTAL);
                b.setPadding(0,16,0,16);
                b.setId(count);
                b.setTextSize(textSizeBibleNumber);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                columnLayout.addView(b);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Vars.nowChapter = v.getId();
                        Vars.nowVerse = 0;
                        generateBibleBody();
                    }
                });
                rowLayout.addView(columnLayout);
                count++;
                if (count > loop)
                    break;
            }
            if (count > loop)
                break;
        }
        tV = new TextView(Vars.mContext);
        tV.setText("\n\n");
        tV.setTextSize(28);
        tV.setWidth(10);
        tV.setTextColor(Color.parseColor("#000000"));
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

    public void generateBibleBody() {

        final ScrollView scrollView = new ScrollView(Vars.mContext);
        String file2read = "bible/" + Vars.nowBible + "/" + Vars.nowChapter + ".txt";
        final String bibleTexts[] = readBibleFile(file2read);
        if (bibleTexts == null) {
            Toast.makeText(Vars.mContext, "Bible source not found " + Vars.fullBibleNames[Vars.nowBible] + " " + Vars.nowChapter,Toast.LENGTH_LONG).show();
            return;
        }
        iVerse = 0;
        iRefer = 0;
        iKeyword = 0;
        iPara = 0;
        iAgp = 0;
        iCev = 0;
        LinearLayout linearlayout = new LinearLayout(Vars.mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.LEFT);
        scrollView.addView(linearlayout);
        final TextView tV = new TextView(Vars.mContext);
        tV.setTextSize(textSizeBibleText);
        tV.setGravity(Gravity.LEFT);
        tV.setWidth(Vars.xPixels);
        tV.setTextColor(Color.parseColor("#000000"));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) linearlayout.getLayoutParams();
        lp.setMargins(20,16,20,16);
        linearlayout.setLayoutParams(lp);
        linearlayout.addView(tV);

        bodyText = new StringBuilder();
        bodyText.append("\n");
        ptrBody = 1;
        generateBibleAllVerses(bibleTexts);
        bodyText.append("\n\n\n");
        SpannableString ss = settleSpannableString();

        pushHistory();
        tV.setText(ss);
        tV.setMovementMethod(LinkMovementMethod.getInstance());
        Vars.mContainerBody.removeAllViewsInLayout();
        Vars.mContainerBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
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
            ss.setSpan(new ForegroundColorSpan(Vars.verseColorF), verseF[i], verseT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(BOLD), verseF[i], verseT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < iPara; i++) {
            ss.setSpan(new ForegroundColorSpan(Vars.paraColorF), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new UnderlineSpan(), paraF[i], paraT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < iRefer; i++) {
            ss.setSpan(new referSpan(refers[i], referV[i]), referF[i], referT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < iCev; i++) {
            ss.setSpan(new ForegroundColorSpan(Vars.cevColorF), cevF[i], cevT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new BackgroundColorSpan(Vars.cevColorB), cevF[i], cevT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < iAgp; i++) {
            ss.setSpan(new ForegroundColorSpan(Vars.agpColorF), agpF[i], agpT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new BackgroundColorSpan(Vars.agpColorB), agpF[i], agpT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
// {천지 창조}[_태초_]에 [_하나님_]이 [_천지_]를 [_창조_]하시니라[_#v43#1:3_][_$58#1:10_]
// `a태초에 하나님께서 하늘과 땅을 창조하셨습니다.
// `cIn the beginning God created the heavens and the earth.
            String c = workLine.substring(0, 1);
            if (c.equals("{")) {    // first column might have paragraph name
                int endIdx = workLine.indexOf("}");
                str = workLine.substring(1, endIdx);
                workLine = workLine.substring(endIdx + 1);
                lenWorkLine = workLine.length();
                bodyText.append(str + "\n");
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
            if (Vars.agpShow) {
                agpText = " " + agpText;
                bodyText.append(agpText);
                agpF[iAgp] = ptrBody;
                agpT[iAgp] = ptrBody + agpText.length();
                iAgp++;
                ptrBody += agpText.length();
            }
            if (Vars.cevShow) {
                cevText = " " + cevText;
                bodyText.append(cevText);
                cevF[iCev] = ptrBody;
                cevT[iCev] = ptrBody + cevText.length();
                iCev++;
                ptrBody += cevText.length();
            }
            ptrBody++;
            bodyText.append("\n");
        }
    }

    private int generateBibleKeyword(String workLine, int verse) {
        int ptr;
        ptr = workLine.indexOf("_]");
        String keyword = workLine.substring(2, ptr);
        if (keyword.substring(0, 1).equals("$")) {   // reference
            String bibShort = Vars.shortBibleNames[parseInt(keyword.substring(1, 3))];
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
                    bodyText.append(keyword.substring(0, tilde));
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

        Typeface typeface = Typeface.create(Typeface.DEFAULT, BOLD);
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Vars.bibleColorF);
            ds.setTypeface(typeface);
            ds.setUnderlineText(false);    // this remove the underline
        }

        @Override
        public void onClick(View widget) {
            Vars.keyWord = key;
            Vars.nowVerse = verse;
            generateKeyWord();
        }
    }

    public class referSpan extends ClickableSpan{

        String key;
        int verse;
        public referSpan(String key, int verse) { this.key = key; this.verse = verse;}
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Vars.referColorF);
            ds.setTextSize(textSizeBibleRefer);
            ds.setUnderlineText(false);    // this remove the underline
        }

        @Override
        public void onClick(View widget) {
            Vars.keyWord = key;
            Vars.nowVerse = verse;
            generateRefer();
        }
    }

    TextView tVTitle;
    String hymnTitle = "";
    public void generateHymnKeypad() {

        ScrollView scrollView = new ScrollView(Vars.mContext);
        int ids[] = {7,8,9,4,5,6,1,2,3,0,100,-1,200,-1,-1};
        Button b;

        LinearLayout linearlayout = new LinearLayout(Vars.mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);
        TextView tV0 = new TextView(Vars.mContext);

        tV0.setText("\n");
        tV0.setTextSize(textSizeHymnTitle);
        tV0.setTextColor(Color.parseColor("#000000"));
        tV0.setGravity(Gravity.CENTER_HORIZONTAL);
        linearlayout.addView(tV0);

        tVTitle = new TextView(Vars.mContext);
        if (Vars.nowHymn != 0)
            hymnTitle = Vars.nowHymn + " : " + Vars.hymnTitles[Vars.nowHymn];
        else
            hymnTitle = "";
        tVTitle.setText(hymnTitle);
        tVTitle.setTextSize(textSizeHymnKeypad);
        tVTitle.setTextColor(Color.parseColor("#0b1849"));
        tVTitle.setGravity(Gravity.CENTER);
        tVTitle.setWidth(2000);
        linearlayout.addView(tVTitle);

        for(int i = 0; i<5;i++) {
            LinearLayout rowLayout = new LinearLayout(Vars.mContext);
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
                LinearLayout columnLayout = new LinearLayout(Vars.mContext);
                columnLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                b = new Button(Vars.mContext);
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
                        if (Vars.nowHymn *10 + nbr <= 645) {
                            Vars.nowHymn = Vars.nowHymn * 10 + nbr;
                            hymnTitle = Vars.nowHymn + " " + Vars.hymnTitles[Vars.nowHymn];
                            tVTitle.setText(hymnTitle);
                        }
                        }
                    });
                }
                else if (id==100) {   // clear
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        Vars.nowHymn = 0;
                        hymnTitle = "";
                        tVTitle.setText(hymnTitle);
                        }
                    });
                }
                else if (id==200) {    // go
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
        Vars.mContainerBody.removeAllViewsInLayout();
        Vars.mContainerBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }

    public void generateHymnBody() {

        ScrollView scrollView = new ScrollView(Vars.mContext);
        String txt = "Hymn/" + Vars.nowHymn + ".txt";
        String [] hymnTexts = readBibleFile(txt);
        if (hymnTexts == null) {
            Toast.makeText(Vars.mContext, "찬송가 " + Vars.nowHymn + " 파일 없음",Toast.LENGTH_LONG).show();
            return;
        }
        LinearLayout linearlayout = new LinearLayout(Vars.mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);

        if (hymnImageShow) {
            ImageView imV = new ImageView(Vars.mContext);
            linearlayout.addView(imV);

            File imgFile = new File(Vars.packageFolder, "hymn_png/" + Vars.nowHymn + ".pngz");

            if (imgFile.exists()) {
                Bitmap hymnBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imV.setImageBitmap(hymnBitmap);
            } else {
                Log.e("no image", imgFile.toString());
            }
        }
        if (hymnTextShow) {
            TextView tVBody = new TextView(Vars.mContext);
            tVBody.setTextSize(textSizeHymnText);
            tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
            tVBody.setWidth(Vars.xPixels);
            tVBody.setTextColor(Color.parseColor("#000000"));
            linearlayout.addView(tVBody);

            bodyText = new StringBuilder();
            for (String hymnText : hymnTexts) {
                String workLine = "\n" + hymnText;
                bodyText.append(workLine);
            }
            bodyText.append("\n\n\n\n");
            SpannableString ssBody = new SpannableString(bodyText);
            tVBody.setText(ssBody);
            tVBody.setMovementMethod(LinkMovementMethod.getInstance());
        }
        pushHistory();
        Vars.mContainerBody.removeAllViewsInLayout();
        Vars.mContainerBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }

    public void generateKeyWord() {

        int verse = nowVerse;
        String txt = "dict/" + Vars.keyWord + ".txt";
        popHistory();
        nowVerse = verse;
        pushHistory();
        ScrollView scrollView = new ScrollView(Vars.mContext);
        String [] dicTexts = readBibleFile(txt);
        if (dicTexts != null) {
            LinearLayout linearlayout = new LinearLayout(Vars.mContext);
            linearlayout.setOrientation(LinearLayout.VERTICAL);
            linearlayout.setGravity(Gravity.LEFT);
            scrollView.addView(linearlayout);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) linearlayout.getLayoutParams();
            lp.setMargins(20,16,20,16);
            linearlayout.setLayoutParams(lp);
            for (String line : dicTexts) {
                if (line.substring(0, 1).equals("@")) {      // contains image file name
                    File imgFile = new File(Vars.packageFolder, "dict_img/" + line.substring(1));
                    if (imgFile.exists()) {
                        Bitmap dictBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        int height = 1000 * dictBitmap.getHeight() / dictBitmap.getWidth();
                        ImageView imV = new ImageView(Vars.mContext);
                        linearlayout.addView(imV);
                        imV.setImageBitmap(Bitmap.createScaledBitmap(dictBitmap, 1000, height, false));
                        //                        imV.setImageBitmap(dictBitmap);
//                        imV.getLayoutParams().width = xPixels;
                        imV.requestLayout();
                    } else {
                        log("no image", imgFile.toString());
                    }
                } else if (line.substring(0, 1).equals("~")) { // contains subject name
                    TextView tVLine = new TextView(Vars.mContext);
                    tVLine.setTextSize(textSizeKeyword + 3);
                    tVLine.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                    tVLine.setGravity(Gravity.CENTER_HORIZONTAL);
                    tVLine.setWidth(Vars.xPixels);
                    linearlayout.addView(tVLine);
                    tVLine.setText(line.substring(1));
                } else {
                    TextView tVLine = new TextView(Vars.mContext);
                    tVLine.setTextSize(textSizeKeyword);
                    tVLine.setGravity(Gravity.LEFT);
                    tVLine.setWidth(Vars.xPixels);
                    linearlayout.addView(tVLine);
                    tVLine.setText(line);
                }
            }
            TextView tVBottom = new TextView(Vars.mContext);
            tVBottom.setTextSize(textSizeKeyword);
            tVBottom.setGravity(Gravity.LEFT);
            tVBottom.setWidth(Vars.xPixels);
            linearlayout.addView(tVBottom);
            tVBottom.setText("\n\n\n");
        }
        else {
            String errText = "[" + Vars.keyWord + "] not found";
            Toast.makeText(Vars.mContext,errText,Toast.LENGTH_LONG).show();
            log(Vars.logFile, errText);
        }
        topTab = Vars.TABMODE_DIC;

        pushHistory();
        Vars.mContainerBody.removeAllViewsInLayout();
        Vars.mContainerBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }

    public void generateRefer() {

        String refer = Vars.keyWord; // 41#4:18
        int verse = nowVerse;
        popHistory();
        nowVerse = verse;
        pushHistory();
        nowBible = parseInt(refer.substring(0,2));
        Vars.topTab = (nowBible < 40) ? Vars.TABMODE_OLD : Vars.TABMODE_NEW;
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
    final int textSizeBible66Decrease = 1001;
    final int textSizeBible66Increase = 1002;
    final int textSizeBibleDecrease = 1011;
    final int textSizeBibleIncrease = 1012;
    final int textSizeReferDecrease = 1021;
    final int textSizeReferIncrease = 1022;
    final int textSizeHymnDecrease = 1031;
    final int textSizeHymnIncrease = 1032;
    final int hymnImageOnOff = 2005;
    final int hymnTextOnOff = 2006;
    final String yesShow = " 예 ";
    final String noShow = " 아니오 ";

    public void generateSettingBody() {

        String text;
        ScrollView scrollView = new ScrollView(Vars.mContext);
        LinearLayout linearlayout = new LinearLayout(Vars.mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);

        TextView tVBody = new TextView(Vars.mContext);
        tVBody.setTextSize(20); // fixed
        tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
        tVBody.setWidth(Vars.xPixels);
        tVBody.setTextColor(Color.parseColor("#000000"));
        linearlayout.addView(tVBody);

        iRefer = 0;
        bodyText = new StringBuilder();
        ptrBody = 0;
        text = "\n\n성경종류 크기  "; bodyText.append(text); ptrBody += text.length();
        appendSetting("  -   ",textSizeBible66Decrease);
        text = "  " + textSizeBible66 + "  ";  bodyText.append(text); ptrBody += text.length();
        appendSetting("   +   ",textSizeBible66Increase);

        text = "\n\n성경말씀 크기  "; bodyText.append(text); ptrBody += text.length();
        appendSetting("  -   ",textSizeBibleDecrease);
        text = "  " + textSizeBibleText + "  ";  bodyText.append(text); ptrBody += text.length();
        appendSetting("   +   ",textSizeBibleIncrease);

        text = "\n\n성경관주 크기  "; bodyText.append(text); ptrBody += text.length();
        appendSetting("  -   ",textSizeReferDecrease);
        text = "  " + textSizeBibleRefer + "  ";  bodyText.append(text); ptrBody += text.length();
        appendSetting("   +   ",textSizeReferIncrease);

        text = "\n\n찬송악보 보이기  "; bodyText.append(text); ptrBody += text.length();
        if (hymnImageShow)
            appendSetting(noShow,hymnImageOnOff);
        else
            appendSetting(yesShow,hymnImageOnOff);

        text = "\n\n찬송가사 보이기  "; bodyText.append(text); ptrBody += text.length();
        if (hymnTextShow) {
            appendSetting(noShow, hymnTextOnOff);
            text = "\n\n찬송가사 크기   "; bodyText.append(text); ptrBody += text.length();
            appendSetting("  -   ",textSizeHymnDecrease);
            text = "  " + textSizeHymnText + "  ";  bodyText.append(text); ptrBody += text.length();
            appendSetting("   +   ",textSizeHymnIncrease);
        }
        else
            appendSetting(yesShow,hymnTextOnOff);

        bodyText.append("\n\n\n");
        SpannableString ss = settleSettingSpan();
        tVBody.setText(ss);
        tVBody.setMovementMethod(LinkMovementMethod.getInstance());
        pushHistory();

        Vars.mContainerBody.removeAllViewsInLayout();
        Vars.mContainerBody.addView(scrollView);
        mainActivity.clearBottomMenu();
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
            ss.setSpan(new BackgroundColorSpan(Vars.cevColorB), referF[i], referT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new settingSpan(referV[i]), referF[i], referT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    public class settingSpan extends ClickableSpan{

        int what;
        Typeface typeface = Typeface.create(Typeface.DEFAULT, BOLD);
        public settingSpan(int what) { this.what = what;}
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setTypeface(typeface);
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(false);    // this remove the underline
        }

        @Override
        public void onClick(View widget) {
            switch (what) {

                case textSizeBible66Decrease:
                    textSizeBible66--;
                    editor.putInt("textSizeBible66", textSizeBible66).apply();
                    break;
                case textSizeBible66Increase:
                    textSizeBible66++;
                    editor.putInt("textSizeBible66", textSizeBible66).apply();
                    break;
                case textSizeBibleDecrease:
                    textSizeBibleText--;
                    editor.putInt("textSizeBibleText", textSizeBibleText).apply();
                    break;
                case textSizeBibleIncrease:
                    textSizeBibleText++;
                    editor.putInt("textSizeBibleText", textSizeBibleText).apply();
                    break;
                case textSizeReferDecrease:
                    textSizeBibleRefer--;
                    editor.putInt("textSizeBibleRefer", textSizeBibleRefer).apply();
                    break;
                case textSizeReferIncrease:
                    textSizeBibleRefer++;
                    editor.putInt("textSizeBibleRefer", textSizeBibleRefer).apply();
                    break;
                case textSizeHymnDecrease:
                    textSizeHymnText--;
                    editor.putInt("textSizeHymnText", textSizeHymnText).apply();
                    break;
                case textSizeHymnIncrease:
                    textSizeHymnText++;
                    editor.putInt("textSizeHymnText", textSizeHymnText).apply();
                    break;
                case hymnImageOnOff:
                    hymnImageShow ^= true;
                    editor.putBoolean("hymnImageShow", hymnImageShow).apply();
                    break;
                case hymnTextOnOff:
                    hymnTextShow ^= true;
                    editor.putBoolean("hymnImageShow", hymnImageShow).apply();
                    break;
            }
            generateSettingBody();
        }
    }

    private int STACK_SIZE = 20;
    private int topTabStack [] = new int[STACK_SIZE];
    private int bibleStack [] = new int[STACK_SIZE];
    private int chapterStack [] = new int[STACK_SIZE];
    private int verseStack [] = new int[STACK_SIZE];
    private int hymnStack[] = new int[STACK_SIZE];
    private String keyStack[] = new String[STACK_SIZE];
    private int stackP = 0;
    public void pushHistory() {
        if (stackP > 18) {
            for (int i = 0; i < 15; i++) {
                topTabStack[i] = topTabStack[i+4];
                bibleStack[i] = bibleStack[i+4];
                chapterStack[i] = chapterStack[i+4];
                verseStack[i] = verseStack[i+4];
                hymnStack[i] = hymnStack[i+4];
                keyStack[i] = keyStack[i+4];
            }
            stackP -= 4;
        }
        topTabStack[stackP] = Vars.topTab;
        bibleStack[stackP] = Vars.nowBible;
        chapterStack[stackP] = Vars.nowChapter;
        verseStack[stackP] = Vars.nowVerse;
        hymnStack[stackP] = Vars.nowHymn;
        keyStack[stackP] = Vars.keyWord;
        stackP++;
    }
    public void popHistory() {
        if (stackP > 0)
            stackP--;
        Vars.topTab = topTabStack[stackP];
        Vars.nowBible = bibleStack[stackP];
        Vars.nowChapter = chapterStack[stackP];
        Vars.nowVerse = verseStack[stackP];
        Vars.nowHymn = hymnStack[stackP];
        Vars.keyWord = keyStack[stackP];
//        for (int i = 0; i < stackP; i++) {
//            Log.w("pop "+i, topTabStack[i]+" b "+bibleStack[i]+" c "+chapterStack[i]+" v "+verseStack[i]+" h "+hymnStack[i]);
//        }
    }
}
