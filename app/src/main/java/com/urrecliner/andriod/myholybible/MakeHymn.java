package com.urrecliner.andriod.myholybible;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;

import static com.urrecliner.andriod.myholybible.Vars.LYRIC_ONLY;
import static com.urrecliner.andriod.myholybible.Vars.LYRIC_THEN_SHEET;
import static com.urrecliner.andriod.myholybible.Vars.SHEET_ONLY;
import static com.urrecliner.andriod.myholybible.Vars.SHEET_THEN_LYRIC;
import static com.urrecliner.andriod.myholybible.Vars.history;
import static com.urrecliner.andriod.myholybible.Vars.hymnShowWhat;
import static com.urrecliner.andriod.myholybible.Vars.hymnTitles;
import static com.urrecliner.andriod.myholybible.Vars.mBody;
import static com.urrecliner.andriod.myholybible.Vars.mContext;
import static com.urrecliner.andriod.myholybible.Vars.mainActivity;
import static com.urrecliner.andriod.myholybible.Vars.normalMenuColor;
import static com.urrecliner.andriod.myholybible.Vars.nowHymn;
import static com.urrecliner.andriod.myholybible.Vars.packageFolder;
import static com.urrecliner.andriod.myholybible.Vars.paraColorFore;
import static com.urrecliner.andriod.myholybible.Vars.sortedNumbers;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleTitle;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnBody;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnKeypad;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnTitle;
import static com.urrecliner.andriod.myholybible.Vars.utils;
import static com.urrecliner.andriod.myholybible.Vars.xPixels;

class MakeHymn {

    private String new2Line = "\n\n";

    private TextView tVTitle;
    private String hymnTitle = "";
    private ScrollView scrollView;

    void makeHymnKeypad() {

        scrollView = new ScrollView(mContext);
        int [] ids = {7,8,9,4,5,6,1,2,3,0,100,-1,200,-1,-1};
        Button b;

        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);
        TextView tV0 = new TextView(mContext);

//        tV0.setText(newLine);
        tV0.setTextSize(textSizeHymnTitle);
        tV0.setTextColor(ContextCompat.getColor(mContext,R.color.Black));
        tV0.setGravity(Gravity.CENTER_HORIZONTAL);
        linearlayout.addView(tV0);

        tVTitle = new TextView(mContext);
        if (nowHymn != 0)
            hymnTitle = nowHymn + " : " + hymnTitles[nowHymn];
        else
            hymnTitle = "";
        tVTitle.setText(hymnTitle);
        tVTitle.setTextSize(textSizeHymnKeypad);
        tVTitle.setTextColor(ContextCompat.getColor(mContext,R.color.hymnTitle));
        tVTitle.setGravity(Gravity.CENTER);
        tVTitle.setWidth(2000);
        linearlayout.addView(tVTitle);

        for(int row = 0; row<5;row++) {
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearlayout.addView(rowLayout);
            for(int col = 0; col < 3; col++) {
                int id = ids[row*3+col];
                if (id == -1)
                    break;
                String buttonText;
                int buttonWidth;
                switch (id) {
                    case 100:
                        buttonWidth = 300;
                        buttonText = "Clear";
                        break;
                    case 200:
                        buttonWidth = 400;
                        buttonText = "Go";
                        break;
                    default:
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
                else if (id==100) { // code is clear
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        nowHymn = 0;
                        hymnTitle = "";
                        tVTitle.setText(hymnTitle);
                        }
                    });
                }
                else {    // code is go
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            makeHymnBody();
                        }
                    });
                }
                rowLayout.addView(columnLayout);
            }
        }
        TextView tVSort = new TextView(mContext);
        tVSort.setTextSize(textSizeHymnKeypad);
        tVSort.setTextColor(ContextCompat.getColor(mContext,R.color.Black));
        tVSort.setGravity(Gravity.CENTER);
        tVSort.setWidth(2000);
        linearlayout.addView(tVSort);

        for(int row = 0; row<8;row++) {   // 4
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearlayout.addView(rowLayout);
            for(int col = 0; col < 2; col++) {
                String text = hymnTitles[sortedNumbers[(row+row+col)*41]].substring(0,6)+".~";     // 81
                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                b = new Button(mContext);
                b.setTextSize(textSizeHymnBody);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                b.setWidth(440);
                b.setText(text);
                columnLayout.addView(b);
                b.setId((row+row+col)*41);    // 81
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    int nbr = v.getId();
                    makeSortedHymnList(nbr);
                    }
                });
                rowLayout.addView(columnLayout);
            }
        }

        TextView tTail = new TextView(mContext);
//        tVSort.setText(newLine);
        tTail.setTextSize(textSizeHymnKeypad);
        tTail.setWidth(2000);
        tTail.setText(new2Line);
        linearlayout.addView(tTail);

        nowHymn = 0;
//        history.push();   // haha
        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }

    void makeHymnBody() {

        scrollView = new ScrollView(mContext);
        String txt = "Hymn/" + nowHymn + ".txt";
        String [] hymnTexts = utils.readBibleFile(txt);
        if (hymnTexts == null) {
            Toast.makeText(mContext, "찬송가 " + nowHymn + " 가사 파일 없음",Toast.LENGTH_LONG).show();
            return;
        }
        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);

        TextView tVBody = new TextView(mContext);
        txt = nowHymn+" : "+hymnTitles[nowHymn];
        tVBody.setText(txt);
        tVBody.setTextSize(textSizeHymnBody+textSizeHymnBody/5);
        tVBody.setPadding(0,20,0,20);
        tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
        tVBody.setWidth(xPixels);
        tVBody.setTextColor(ContextCompat.getColor(mContext,R.color.Black));
        tVBody.setBackgroundColor(normalMenuColor | 0x777777);
        linearlayout.addView(tVBody);

        switch (hymnShowWhat) {
            case SHEET_THEN_LYRIC:
                show_hymnImage(linearlayout);
                show_HymnText(hymnTexts, linearlayout);
                break;
            case LYRIC_THEN_SHEET:
                show_HymnText(hymnTexts, linearlayout);
                show_hymnImage(linearlayout);
                break;
            case SHEET_ONLY:
                show_hymnImage(linearlayout);
                break;
            case LYRIC_ONLY:
                show_HymnText(hymnTexts, linearlayout);
                break;
        }
        history.push();
        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }

    private void show_HymnText(String[] hymnTexts, LinearLayout linearlayout) {
        TextView tVBody = new TextView(mContext);
        tVBody.setTextSize(textSizeHymnBody);
        tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
        tVBody.setWidth(xPixels);
        tVBody.setTextColor(ContextCompat.getColor(mContext,R.color.Black));
        linearlayout.addView(tVBody);

        StringBuilder bodyText = new StringBuilder();
        for (String hymnText : hymnTexts) {
            String workLine = "\n"+hymnText;
            bodyText.append(workLine);
        }
        bodyText.append("\n\n");
        if (hymnShowWhat == SHEET_THEN_LYRIC || hymnShowWhat == LYRIC_ONLY)
                    bodyText.append("\n\n\n");
        SpannableString ssBody = new SpannableString(bodyText);
        tVBody.setText(ssBody);
        tVBody.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void show_hymnImage(LinearLayout linearlayout) {
        File imgFile = new File(packageFolder, "hymn_png/" + nowHymn + ".pngz");
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            int height = xPixels * bitmap.getHeight() / bitmap.getWidth();
            ImageView imV = new ImageView(mContext);
            imV.setBackgroundColor(ContextCompat.getColor(mContext,R.color.hymnImageBack));
            linearlayout.addView(imV);
            imV.setImageBitmap(Bitmap.createScaledBitmap(bitmap, xPixels, height, false));
            imV.requestLayout();
            PhotoViewAttacher pA;
            pA = new PhotoViewAttacher(imV);
            pA.update();
        }
        TextView tVBody = new TextView(mContext);
        tVBody.setTextSize(textSizeHymnBody);
        tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
        tVBody.setWidth(xPixels);
        tVBody.setTextColor(ContextCompat.getColor(mContext,R.color.Black));
        linearlayout.addView(tVBody);

        StringBuilder bodyText = new StringBuilder();
        if (hymnShowWhat == SHEET_ONLY || hymnShowWhat == LYRIC_THEN_SHEET)
            bodyText.append(new2Line);
        SpannableString ssBody = new SpannableString(bodyText);
        tVBody.setText(ssBody);
        tVBody.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void makeSortedHymnList(int start) {

        scrollView = new ScrollView(mContext);
        nowHymn = -1 - start;

        TextView titleTV; TextView numberTV;
        String text;
        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        linearlayout.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(linearlayout);
        TextView tV = new TextView(mContext);
        tV.setText("");
        tV.setTextSize(textSizeHymnBody);
        tV.setWidth(xPixels);
        linearlayout.addView(tV);

        for(int row = 0; row<41;row++) {  // 81
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearlayout.addView(rowLayout);

            LinearLayout columnLayout = new LinearLayout(mContext);
            columnLayout.setOrientation(LinearLayout.HORIZONTAL);
            titleTV = new TextView(mContext);
            titleTV.setText(hymnTitles[sortedNumbers[start]]);
            titleTV.setTextColor(Color.BLACK);
            titleTV.setTextSize(textSizeHymnBody);
            columnLayout.addView(titleTV);
            numberTV = new TextView(mContext);
            text = "  " + sortedNumbers[start] + " ";
            numberTV.setText(text);
            numberTV.setId(sortedNumbers[start]);
            numberTV.setTextColor(paraColorFore);
            numberTV.setTextSize(textSizeHymnBody);
            numberTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            columnLayout.addView(numberTV);
            numberTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nowHymn = v.getId();
                    makeHymnBody();
                }
            });
            rowLayout.addView(columnLayout);
            start++;
            if (start > 644)
                break;
        }
        TextView tVb = new TextView(mContext);
        tVb.setText(new2Line);
        tVb.setTextSize(textSizeBibleTitle);
        tVb.setWidth(xPixels);
//        tVb.setGravity(Gravity.CENTER);
        linearlayout.addView(tVb);

        history.push();
        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();

    }

}
