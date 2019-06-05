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

import static com.urrecliner.andriod.myholybible.Vars.history;
import static com.urrecliner.andriod.myholybible.Vars.hymnImageShow;
import static com.urrecliner.andriod.myholybible.Vars.hymnTextShow;
import static com.urrecliner.andriod.myholybible.Vars.hymnTitles;
import static com.urrecliner.andriod.myholybible.Vars.mBody;
import static com.urrecliner.andriod.myholybible.Vars.mContext;
import static com.urrecliner.andriod.myholybible.Vars.mainActivity;
import static com.urrecliner.andriod.myholybible.Vars.nowHymn;
import static com.urrecliner.andriod.myholybible.Vars.packageFolder;
import static com.urrecliner.andriod.myholybible.Vars.paraColorF;
import static com.urrecliner.andriod.myholybible.Vars.scrollView;
import static com.urrecliner.andriod.myholybible.Vars.sortedNumbers;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleTitle;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnBody;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnKeypad;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnTitle;
import static com.urrecliner.andriod.myholybible.Vars.utils;
import static com.urrecliner.andriod.myholybible.Vars.xPixels;

class MakeHymn {

    private String new3Line = "\n\n\n";

    private TextView tVTitle;
    private String hymnTitle = "";

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
                            makeHymnBody();
                        }
                    });
                }
                rowLayout.addView(columnLayout);
            }
        }
        TextView tVSort = new TextView(mContext);
//        tVSort.setText(newLine);
        tVSort.setTextSize(textSizeHymnKeypad);
        tVSort.setTextColor(ContextCompat.getColor(mContext,R.color._Black));
        tVSort.setGravity(Gravity.CENTER);
        tVSort.setWidth(2000);
        linearlayout.addView(tVSort);

        for(int i = 0; i<8;i++) {   // 4
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearlayout.addView(rowLayout);
            for(int j = 0; j < 2; j++) {
                String text = hymnTitles[sortedNumbers[(i+i+j)*41]].substring(0,6);     // 81
                LinearLayout columnLayout = new LinearLayout(mContext);
                columnLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                b = new Button(mContext);
                b.setTextSize(textSizeHymnBody);
                b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                b.setWidth(440);
                b.setText(text);
                columnLayout.addView(b);
                b.setId((i+i+j)*41);    // 81
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
        tTail.setText(new3Line);
        linearlayout.addView(tTail);

        nowHymn = 0;
        history.push();
        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
    }

    void makeHymnBody() {

        scrollView = new ScrollView(mContext);
        String txt = "Hymn/" + nowHymn + ".txt";
        String [] hymnTexts = utils.readBibleFile(txt);
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
            tVBody.setTextSize(textSizeHymnBody);
            tVBody.setGravity(Gravity.CENTER_HORIZONTAL);
            tVBody.setWidth(xPixels);
            tVBody.setTextColor(ContextCompat.getColor(mContext,R.color._Black));
            linearlayout.addView(tVBody);

            StringBuilder bodyText = new StringBuilder();
            for (String hymnText : hymnTexts) {
                String newLine = "\n";
                String workLine = newLine + hymnText;
                bodyText.append(workLine);
            }
            String new2Line = "\n\n";
            bodyText.append(new3Line);
            bodyText.append(new2Line);
            SpannableString ssBody = new SpannableString(bodyText);
            tVBody.setText(ssBody);
            tVBody.setMovementMethod(LinkMovementMethod.getInstance());
        }
        history.push();
        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        mainActivity.makeTopBottomMenu();
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

        for(int i = 0; i<41;i++) {  // 81
            LinearLayout rowLayout = new LinearLayout(mContext);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            linearlayout.addView(rowLayout);

            LinearLayout columnLayout = new LinearLayout(mContext);
            columnLayout.setOrientation(LinearLayout.HORIZONTAL);
//            columnLayout.setGravity(Gravity.CENTER);
            titleTV = new TextView(mContext);
            titleTV.setText(hymnTitles[sortedNumbers[start]]);
            titleTV.setTextColor(Color.BLACK);
            titleTV.setTextSize(textSizeHymnBody);
            columnLayout.addView(titleTV);
            numberTV = new TextView(mContext);
            text = "  " + sortedNumbers[start] + " ";
            numberTV.setText(text);
            numberTV.setId(sortedNumbers[start]);
            numberTV.setTextColor(paraColorF);
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
        tVb.setText(new3Line);
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
