package com.urrecliner.andriod.myholybible;

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
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static android.graphics.Typeface.BOLD;
import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_NEW;
import static com.urrecliner.andriod.myholybible.Vars.TAB_MODE_OLD;
import static com.urrecliner.andriod.myholybible.Vars.alwaysOn;
import static com.urrecliner.andriod.myholybible.Vars.cevColorB;
import static com.urrecliner.andriod.myholybible.Vars.editor;
import static com.urrecliner.andriod.myholybible.Vars.fullBibleNames;
import static com.urrecliner.andriod.myholybible.Vars.history;
import static com.urrecliner.andriod.myholybible.Vars.hymnImageShow;
import static com.urrecliner.andriod.myholybible.Vars.hymnTextShow;
import static com.urrecliner.andriod.myholybible.Vars.mActivity;
import static com.urrecliner.andriod.myholybible.Vars.mBody;
import static com.urrecliner.andriod.myholybible.Vars.mContext;
import static com.urrecliner.andriod.myholybible.Vars.makeBible;
import static com.urrecliner.andriod.myholybible.Vars.markBibles;
import static com.urrecliner.andriod.myholybible.Vars.markChapters;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.nowChapter;
import static com.urrecliner.andriod.myholybible.Vars.nowHymn;
import static com.urrecliner.andriod.myholybible.Vars.nowVerse;
import static com.urrecliner.andriod.myholybible.Vars.scrollView;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBible66;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleRefer;
import static com.urrecliner.andriod.myholybible.Vars.textSizeBibleText;
import static com.urrecliner.andriod.myholybible.Vars.textSizeHymnText;
import static com.urrecliner.andriod.myholybible.Vars.textSizeKeyWord;
import static com.urrecliner.andriod.myholybible.Vars.textSizeSpace;
import static com.urrecliner.andriod.myholybible.Vars.topTab;
import static com.urrecliner.andriod.myholybible.Vars.utils;
import static com.urrecliner.andriod.myholybible.Vars.xPixels;

public class Setting {

    final private int TEXT_SIZE_BIBLE66_DECREASE = 1001;
    final private int TEXT_SIZE_BIBLE66_INCREASE = 1002;
    final private int TEXT_SIZE_BIBLE_DECREASE = 1011;
    final private int TEXT_SIZE_BIBLE_INCREASE = 1012;
    final private int TEXT_SIZE_KEYWORD_DECREASE = 1021;
    final private int TEXT_SIZE_KEYWORD_INCREASE = 1022;
    final private int TEXT_SIZE_REFER_DECREASE = 1031;
    final private int TEXT_SIZE_REFER_INCREASE = 1032;
    final private int TEXT_SIZE_HYMN_DECREASE = 1051;
    final private int TEXT_SIZE_HYMN_INCREASE = 1052;
    final private int TEXT_SIZE_SPACE_DECREASE = 1061;
    final private int TEXT_SIZE_SPACE_INCREASE = 1062;
    final private int HYMN_IMAGE_ON_OFF = 2005;
    final private int HYMN_TEXT_ON_OFF = 2006;
    final private int ALWAYS_ON_OFF = 3007;
    final private int BOOKMARK_0 = 4000;


    private int TABLE_SIZE = 500;

    private int [] referF = new int[TABLE_SIZE];
    private int [] referT = new int[TABLE_SIZE];
    private int [] referV = new int[TABLE_SIZE];
    private String [] refers = new String[TABLE_SIZE];
    private int iRefer;

    private int ptrBody;
    private StringBuilder bodyText;
    private final String newLine = "\n";
    private final String new2Line = "\n\n";

    private MakeHymn makeHymn = null;

    void generateSettingBody() {

        if (makeHymn == null)
            makeHymn = new MakeHymn();
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
        text = newLine + mActivity.getString(R.string.bible_name_size); bodyText.append(text); ptrBody += text.length();
        appendSetting("  -   ", TEXT_SIZE_BIBLE66_DECREASE);
        text = "  " + textSizeBible66 + "  ";  bodyText.append(text); ptrBody += text.length();
        appendSetting("   +   ", TEXT_SIZE_BIBLE66_INCREASE);

        text = newLine + mActivity.getString(R.string.scripture_size); bodyText.append(text); ptrBody += text.length();
        appendSetting("  -   ", TEXT_SIZE_BIBLE_DECREASE);
        text = "  " + textSizeBibleText + "  ";  bodyText.append(text); ptrBody += text.length();
        appendSetting("   +   ", TEXT_SIZE_BIBLE_INCREASE);


        text = newLine + mActivity.getString(R.string.keyword_size); bodyText.append(text); ptrBody += text.length();
        appendSetting("  -   ", TEXT_SIZE_KEYWORD_DECREASE);
        text = "  " + textSizeKeyWord + "  ";  bodyText.append(text); ptrBody += text.length();
        appendSetting("   +   ", TEXT_SIZE_KEYWORD_INCREASE);

        text = newLine + mActivity.getString(R.string.bible_crossing_size); bodyText.append(text); ptrBody += text.length();
        appendSetting("  -   ", TEXT_SIZE_REFER_DECREASE);
        text = "  " + textSizeBibleRefer + "  ";  bodyText.append(text); ptrBody += text.length();
        appendSetting("   +   ", TEXT_SIZE_REFER_INCREASE);

        text = newLine + mActivity.getString(R.string.verse_space_size); bodyText.append(text); ptrBody += text.length();
        appendSetting("  -   ", TEXT_SIZE_SPACE_DECREASE);
        text = "  " + textSizeSpace + "  ";  bodyText.append(text); ptrBody += text.length();
        appendSetting("   +   ", TEXT_SIZE_SPACE_INCREASE);

        text = newLine + mActivity.getString(R.string.wanna_show_hymn_sheet); bodyText.append(text); ptrBody += text.length();
        appendSetting((hymnImageShow)? noShow:yesShow, HYMN_IMAGE_ON_OFF);

        text = newLine + mActivity.getString(R.string.wanna_show_hymn_lyric); bodyText.append(text); ptrBody += text.length();
        if (hymnTextShow) {
            appendSetting(noShow, HYMN_TEXT_ON_OFF);
            text = newLine + mActivity.getString(R.string.hymn_lyric_size); bodyText.append(text); ptrBody += text.length();
            appendSetting("  -   ", TEXT_SIZE_HYMN_DECREASE);
            text = "  " + textSizeHymnText + "  ";  bodyText.append(text); ptrBody += text.length();
            appendSetting("   +   ", TEXT_SIZE_HYMN_INCREASE);
        }
        else
            appendSetting(yesShow, HYMN_TEXT_ON_OFF);
        text = newLine + mActivity.getString(R.string.keep_screen_on); bodyText.append(text); ptrBody += text.length();
        appendSetting((alwaysOn)? noShow:yesShow, ALWAYS_ON_OFF);

        text = newLine + mActivity.getString(R.string.bookmark_saved); bodyText.append(text); ptrBody += text.length();

        for (int i = 0; i < markBibles.size(); i++) {
            text = newLine + fullBibleNames[Integer.parseInt(markBibles.get(i))] + " " + markChapters.get(i);
            bodyText.append(text); ptrBody += text.length();
            appendSetting("  읽기  ", BOOKMARK_0 + i);
        }

        SpannableString ss = settleSettingSpan();
        tVBody.setText(ss);
        tVBody.setMovementMethod(LinkMovementMethod.getInstance());
        history.push();

        mBody.removeAllViewsInLayout();
        mBody.addView(scrollView);
        utils.clearBottomMenu();
    }

    private void appendSetting(String text, int what2do) {
        bodyText.append(text); referV[iRefer] = what2do; referF[iRefer] = ptrBody;
        ptrBody += text.length(); referT[iRefer] = ptrBody;
        iRefer++;
    }

    @NonNull
    SpannableString settleSettingSpan() {
        SpannableString ss = new SpannableString(bodyText);
        for (int i = 0; i < iRefer; i++) {
            ss.setSpan(new BackgroundColorSpan(cevColorB), referF[i], referT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new settingSpan(referV[i]), referF[i], referT[i], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    public class settingSpan extends ClickableSpan {

        int what;
        Typeface typeface = Typeface.create(Typeface.DEFAULT, BOLD);
        settingSpan(int what) { this.what = what;}
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
                case TEXT_SIZE_KEYWORD_DECREASE:
                    textSizeKeyWord--;
                    editor.putInt("textSizeKeyWord", textSizeKeyWord).apply();
                    break;
                case TEXT_SIZE_KEYWORD_INCREASE:
                    textSizeKeyWord++;
                    editor.putInt("textSizeKeyWord", textSizeKeyWord).apply();
                    break;
                case TEXT_SIZE_REFER_DECREASE:
                    textSizeBibleRefer--;
                    editor.putInt("textSizeBibleRefer", textSizeBibleRefer).apply();
                    break;
                case TEXT_SIZE_REFER_INCREASE:
                    textSizeBibleRefer++;
                    editor.putInt("textSizeBibleRefer", textSizeBibleRefer).apply();
                    break;
                case TEXT_SIZE_SPACE_DECREASE:
                    textSizeSpace--;
                    editor.putInt("textSizeSpace", textSizeBibleRefer).apply();
                    break;
                case TEXT_SIZE_SPACE_INCREASE:
                    textSizeSpace++;
                    editor.putInt("textSizeSpace", textSizeBibleRefer).apply();
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
                        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    else
                        mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    break;
                case BOOKMARK_0:
                case BOOKMARK_0+1:
                case BOOKMARK_0+2:
                case BOOKMARK_0+3:
                case BOOKMARK_0+4:
                    return2BookMark(what - BOOKMARK_0);
                    return;
            }
            generateSettingBody();
        }
    }

    private void return2BookMark(int i) {
        nowBible = Integer.parseInt(markBibles.get(i));
        nowChapter = Integer.parseInt(markChapters.get(i));
        nowVerse = 0;
        nowHymn = 0;
        topTab = (nowBible < 40) ? TAB_MODE_OLD : TAB_MODE_NEW;
        makeBible.generateBibleBody();
    }

}
