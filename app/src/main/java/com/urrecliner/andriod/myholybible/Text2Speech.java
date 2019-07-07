package com.urrecliner.andriod.myholybible;

import android.annotation.SuppressLint;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.urrecliner.andriod.myholybible.Vars.bibleTexts;
import static com.urrecliner.andriod.myholybible.Vars.fullBibleNames;
import static com.urrecliner.andriod.myholybible.Vars.isSaying;
import static com.urrecliner.andriod.myholybible.Vars.mainActivity;
import static com.urrecliner.andriod.myholybible.Vars.maxVerse;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.nowChapter;
import static com.urrecliner.andriod.myholybible.Vars.utils;

class Text2Speech {

    private TextToSpeech mTTS = null;
    private String logId = "tts";
    private float ttsPitch = 1.0f;
    private float ttsSpeed = 1.0f;
    private int ttsVerseNow = 0;

//    @Override
//    public void onInit(int status) {
//        Log.w("onInit","Status = "+status);
//    }

    void setReady(Context context) {
        mTTS = new TextToSpeech(context, ttsInitListener);
    }
    // It's callback
    private TextToSpeech.OnInitListener ttsInitListener = new TextToSpeech.OnInitListener()
    {
        @SuppressLint("NewApi")
        @Override
        public void onInit(int status)
        {
            if (status != TextToSpeech.SUCCESS)
                return;
            mTTS.setPitch(ttsPitch);
            mTTS.setSpeechRate(ttsSpeed);
            mTTS.setOnUtteranceCompletedListener(completedListener);
        }
    };

    @SuppressWarnings("deprecation")
    private OnUtteranceCompletedListener completedListener = new OnUtteranceCompletedListener()
    {
        @Override
        public void onUtteranceCompleted(String utteranceId)
        {
            ttsVerseNow++;
            if (isSaying && ttsVerseNow < maxVerse)
                readVerse(ttsVerseNow);
            else if (isSaying) {
                mainActivity.handleBibleRight();
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        ttsVerseNow =0;
                        readVerse(ttsVerseNow);
                    }
                }, 2000);
            }
        }
    };

    void setPitch(float p) {
        ttsPitch = p;
    }

    void setSpeed(float s) {
        ttsSpeed = s;
    }

    void setTtsVerseNow(int v) {
        ttsVerseNow = v;
    }

    @SuppressWarnings("deprecation")
    void readVerse(int v) {
        String text;
        ttsVerseNow = v;
        text = bibleTexts[v].substring(0, bibleTexts[v].indexOf("`a"));
        String match = "[^\uAC00-\uD7A3xfe./,\\s]"; // 한글, 영문, 숫자만 OK
        text = (v + 1) + "절.. " + text.replaceAll(match, "");
        if (v == 0) {
            text = fullBibleNames[nowBible] + ". " + nowChapter + " " + ((nowBible == 19) ? "편" : "장") + " 말씀입니다.." + text;
            Log.w("text",text);
        }
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, map);
        } catch (Exception e) {
            utils.log(logId, "ttsSpeak\n" + e.toString());
        }
    }

    void stopRead() {
        mTTS.stop();
    }
}
