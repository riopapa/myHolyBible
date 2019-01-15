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
import static com.urrecliner.andriod.myholybible.Vars.topTab;

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
        return lines.toArray(new String[lines.size()]);
    }

    public String getTimeStamp() {
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
        TextView tV = new TextView(Vars.mContext);
        tV.setText(Vars.blank);
        tV.setTextSize(28);
        tV.setWidth(Vars.xPixels);
        tV.setTextColor(Color.parseColor("#000000"));
        tV.setGravity(Gravity.CENTER);
        linearlayout.addView(tV);
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
                b.setTextSize(20);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                columnLayout.addView(b);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Vars.nowBible = v.getId();
                        Log.w("biblelist","bible " + Vars.nowBible);
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
        Vars.mContainerBody.removeAllViewsInLayout();
        Vars.mContainerBody.addView(scrollView);
    }
    int textSizeBibleTitle = 24;
    int textSizeBibleNumber = 20;
    private ScrollView buildBibleNumber() {
        int loop = Vars.nbrofChapters[Vars.nowBible];
        int count = 1;
        Button b;
        ScrollView scrollView = new ScrollView(Vars.mContext);
        final int nbrColumn = 5;
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
                columnLayout.setOrientation(LinearLayout.VERTICAL);
                b = new Button(Vars.mContext);
                b.setText(""+count);
                b.setId(count);
                b.setTextSize(textSizeBibleNumber);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                columnLayout.addView(b);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Vars.nowChapter = v.getId();
                        Vars.nowVerse = 0;
                        Log.w("nowBible"," is "+ Vars.nowBible);
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
        tV.setText("\n");
        tV.setTextSize(28);
        tV.setWidth(10);
        tV.setTextColor(Color.parseColor("#000000"));
        linearlayout.addView(tV);

        return scrollView;
    }

    float textSize;
    float referSize;
    private int TABLE_SIZE = 500;

    private int keywordF[] = new int[TABLE_SIZE];
    private int keywordT[] = new int[TABLE_SIZE];
    private String keywords[] = new String[TABLE_SIZE];
    private int iKeyword;

    private int VERSE_SIZE = 177; // max verse number is 176
    private int verseF[] = new int[VERSE_SIZE];
    private int verseT[] = new int[VERSE_SIZE];
    private int iVerse;

    private int referF[] = new int[TABLE_SIZE];
    private int referT[] = new int[TABLE_SIZE];
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

    private int bodyP;
    StringBuilder bodyText;

    public void generateBibleBody() {

        ScrollView scrollView = new ScrollView(Vars.mContext);
        textSize = 20;                  // text size is dp, refersize is pixel
        referSize = textSize * 2f;
        String file2read = "bible/" + Vars.nowBible + "/" + Vars.nowChapter + ".txt";
        String bibleTexts[] = readBibleFile(file2read);
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
        TextView tV = new TextView(Vars.mContext);
        tV.setTextSize(textSize);
        tV.setGravity(Gravity.LEFT);
        tV.setWidth(Vars.xPixels);
        tV.setTextColor(Color.parseColor("#000000"));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) linearlayout.getLayoutParams();
        lp.setMargins(20,16,20,16);
        linearlayout.setLayoutParams(lp);
        linearlayout.addView(tV);

        bodyText = new StringBuilder();
        bodyText.append("\n");
        bodyP = 1;
        generateBibleAllVerses(bibleTexts);
        bodyText.append("\n\n\n");
        SpannableString ss = settleSpannableString();

        pushHistory();
        tV.setText(ss);
        tV.setMovementMethod(LinkMovementMethod.getInstance());
        Vars.mContainerBody.removeAllViewsInLayout();
        Vars.mContainerBody.addView(scrollView);
        scrollView.scrollTo(0,3000);
        mainActivity.makeTopBottomMenu();
    }

    @NonNull
    private SpannableString settleSpannableString() {
        SpannableString ss = new SpannableString(bodyText);
        for (int i = 0; i < iKeyword; i++) {
            ss.setSpan(new keywordSpan(keywords[i]), keywordF[i], keywordT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
            ss.setSpan(new referSpan(refers[i]), referF[i], referT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < iCev; i++) {
            ss.setSpan(new ForegroundColorSpan(Vars.cevColorF), cevF[i], cevT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i = 0; i < iAgp; i++) {
            ss.setSpan(new ForegroundColorSpan(Vars.agpColorF), agpF[i], agpT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    private void generateBibleAllVerses(String bibleTexts[]) {
        int lines = bibleTexts.length;
        for (int line = 0; line < lines; line++) {
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
                paraF[iPara] = bodyP;
                paraT[iPara] = bodyP + str.length() + 1;
                iPara++;
                bodyP += str.length() + 1;
            }
            str = " " + (line + 1) + " ";
            verseF[iVerse] = bodyP;
            verseT[iVerse] = bodyP + str.length();
            iVerse++;
            bodyP += str.length();
            bodyText.append(str);

            while (lenWorkLine > 0) {
                idx = workLine.indexOf("[_");
                if (idx == -1) { // no more keyword
                    bodyText.append(workLine);
                    bodyP += lenWorkLine;
                    break;
                } else {  // contains keyword
                    if (idx != 0) {   // has string before keyword
                        bodyText.append(workLine, 0, idx);
                        bodyP += idx;
                        workLine = workLine.substring(idx);
                    }
                    // string starts with [_ & keyword
                    idx2nd = generateBibleKeyword(workLine);
                    workLine = workLine.substring(idx2nd + 2);
                    lenWorkLine = workLine.length();
                }
            }
            if (Vars.agpShow) {
                agpText = " " + agpText;
                bodyText.append(agpText);
                agpF[iAgp] = bodyP;
                agpT[iAgp] = bodyP + agpText.length();
                iAgp++;
                bodyP += agpText.length();
            }
            if (Vars.cevShow) {
                cevText = " " + cevText;
                bodyText.append(cevText);
                cevF[iCev] = bodyP;
                cevT[iCev] = bodyP + cevText.length();
                iCev++;
                bodyP += cevText.length();
            }
            bodyP++;
            bodyText.append("\n");
        }
    }

    private int generateBibleKeyword(String workLine) {
        int ptr;
        ptr = workLine.indexOf("_]");
        String keyword = workLine.substring(2, ptr);
        if (keyword.substring(0, 1).equals("$")) {   // reference
            String bibShort = Vars.shortBibleNames[Integer.parseInt(keyword.substring(1, 3))];
            String showWord = " (" + bibShort + keyword.substring(4) + ") ";      // $01#12:34 -> (창12:34) 로 표시
            bodyText.append(showWord);
            referF[iRefer] = bodyP;
            referT[iRefer] = bodyP + showWord.length();
            refers[iRefer] = keyword.substring(1);  // save 01#12:34
            iRefer++;
            bodyP += showWord.length();
        } else {  // keyword case
            int tilde = keyword.indexOf("~");
            if (isNewKeyword(keyword, keywords, iKeyword)) {
                keywordF[iKeyword] = bodyP;
                if (tilde != -1) {
                    keywordT[iKeyword] = bodyP + tilde;
                    keywords[iKeyword] = keyword.substring(0, tilde) + keyword.substring(tilde + 1);
                    bodyP += tilde;
                    bodyText.append(keyword.substring(0, tilde));
                } else {
                    keywordT[iKeyword] = bodyP + keyword.length();
                    keywords[iKeyword] = keyword;
                    bodyP += keyword.length();
                    bodyText.append(keyword);
                }
                iKeyword++;
            } else {
                bodyText.append(keyword);
                bodyP += keyword.length();
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
        public keywordSpan(String key) { this.key = key;}

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
            generateKeyWord();
        }
    }

    public class referSpan extends ClickableSpan{

        String key;
        public referSpan(String key) { this.key = key;}
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Vars.referColorF);
            ds.setTextSize(referSize);
            ds.setUnderlineText(false);    // this remove the underline
        }

        @Override
        public void onClick(View widget) {
            Toast.makeText(Vars.mContext, "Refer "  + key + " clicked!", Toast.LENGTH_LONG).show();
        }
    }

    TextView tVTitle;
    String hymnTitle = "";
    int textSizeHymnTitle = 20;
    int textSizeHymnKeypad = 24;
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
    }

    int textSizeHymnText = 20;                  // text size is dp, refersize is pixel
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

        ImageView imV = new ImageView(Vars.mContext);
        linearlayout.addView(imV);

        File imgFile = new  File(Vars.packageFolder, "hymn_png/" + Vars.nowHymn + ".pngz");

        if(imgFile.exists()){
            Bitmap hymnBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imV.setImageBitmap(hymnBitmap);
        }
        else {
            Log.e("no image", imgFile.toString());
        }
        TextView tVBody = new TextView(Vars.mContext);
        tVBody.setTextSize(textSizeHymnText);
        tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
        tVBody.setWidth(Vars.xPixels);
        tVBody.setTextColor(Color.parseColor("#000000"));
        linearlayout.addView(tVBody);

        StringBuilder bodyText = new StringBuilder();
        for (String hymnText : hymnTexts) {
            String workLine = hymnText + "\n";
            bodyText.append(workLine);
        }
        bodyText.append("\n\n\n");
        SpannableString ssBody = new SpannableString(bodyText);
        tVBody.setText(ssBody);
        tVBody.setMovementMethod(LinkMovementMethod.getInstance());
        pushHistory();
        Vars.mContainerBody.removeAllViewsInLayout();
        Vars.mContainerBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }
    int textSizeKeyword = 20;                  // text size is dp, refersize is pixel
    public void generateKeyWord() {

        ScrollView scrollView = new ScrollView(Vars.mContext);
        String txt = "dict/" + Vars.keyWord + ".txt";
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
                    tVLine.setTextSize(textSize + 3);
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
            tVBottom.setTextSize(textSize);
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
                keyStack[i] = keyStack[i-4];
                stackP -= 4;
            }
        }
        topTabStack[stackP] = Vars.topTab;
        bibleStack[stackP] = Vars.nowBible;
        chapterStack[stackP] = Vars.nowChapter;
        verseStack[stackP] = Vars.nowVerse;
        hymnStack[stackP] = Vars.nowHymn;
        keyStack[stackP] = Vars.keyWord;
        stackP++;
//        for (int i = 0; i < stackP; i++) {
//            Log.w("push "+i, topTabStack[i]+" b "+bibleStack[i]+" c "+chapterStack[i]+" v "+verseStack[i]+" h "+hymnStack[i]);
//        }
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
