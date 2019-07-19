package com.urrecliner.andriod.myholybible;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.urrecliner.andriod.myholybible.Vars.biblePitch;
import static com.urrecliner.andriod.myholybible.Vars.bibleSpeed;
import static com.urrecliner.andriod.myholybible.Vars.bibleTexts;
import static com.urrecliner.andriod.myholybible.Vars.fullBibleNames;
import static com.urrecliner.andriod.myholybible.Vars.hymnSpeed;
import static com.urrecliner.andriod.myholybible.Vars.isReadingNow;
import static com.urrecliner.andriod.myholybible.Vars.mContext;
import static com.urrecliner.andriod.myholybible.Vars.mainActivity;
import static com.urrecliner.andriod.myholybible.Vars.maxVerse;
import static com.urrecliner.andriod.myholybible.Vars.normalMenuColor;
import static com.urrecliner.andriod.myholybible.Vars.nowBible;
import static com.urrecliner.andriod.myholybible.Vars.nowChapter;
import static com.urrecliner.andriod.myholybible.Vars.nowHymn;
import static com.urrecliner.andriod.myholybible.Vars.packageFolder;
import static com.urrecliner.andriod.myholybible.Vars.utils;
import static com.urrecliner.andriod.myholybible.Vars.vCurrBible;

class Text2Speech {

    private TextToSpeech mTTS = null;
    private int ttsVerseNow = 0;

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
            mTTS.setPitch(biblePitch);
            mTTS.setSpeechRate(bibleSpeed);
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
            if (isReadingNow && ttsVerseNow < maxVerse)
                readVerseByTTS(ttsVerseNow);
            else if (isReadingNow) {
                mainActivity.handleBibleRight();
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        ttsVerseNow =0;
                        readVerseByTTS(ttsVerseNow);
                    }
                }, 2000);
            }
        }
    };

    private MediaPlayer mediaPlayer = null;

    void readVerse() {

        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();
        String fileName = packageFolder.getAbsolutePath()+"/bible_mp3/"+nowBible+"_"+nowChapter+".mp3z";
//        utils.log("mp3file",fileName);
        File file = new File(fileName);
        FileDescriptor fd;
        if (file.exists()) {
            try {
                FileInputStream fs = new FileInputStream(file);
                fd = fs.getFD();
                mediaPlayer.setDataSource(fd);
                fs.close();
                mediaPlayer.setLooping(false);
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(bibleSpeed));
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setPitch(biblePitch));
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    if (isReadingNow) {
                        mainActivity.handleBibleRight();
                        new Timer().schedule(new TimerTask() {
                            public void run() {
                                readVerse();
                            }
                        }, 2000);
                    }
                }
            });
            mediaPlayer.start();
        } else {
            readVerseByTTS(0);
        }
    }

    private void readVerseByTTS(int v) {
        String text;
        String para = null;
        ttsVerseNow = v;
        text = bibleTexts[v].substring(0, bibleTexts[v].indexOf("`a"));
        if (text.substring(0,0).equals("{")) {
            para = text.substring(1,(text.indexOf("}")-1));
            text = text.substring(text.indexOf("}"+1));
        }
        String match = "[^\uAC00-\uD7A3xfe./,\\s]"; // 한글만 OK
        text = text.replaceAll(match, "");
        text = (v + 1) + "절.. " + text;
        if (para != null)
            text = para + ". . "+ text;
        if (v == 0) {
            text = fullBibleNames[nowBible] + ". " + nowChapter + " " + ((nowBible == 19) ? "편" : "장") + " 말씀입니다.." + text;
        }
//        utils.log("text", text);
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, map);
        } catch (Exception e) {
            String logId = "tts";
            utils.log(logId, "ttsSpeak\n" + e.toString());
        }
    }

    void playHymn() {

        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();
        String fileName = packageFolder.getAbsolutePath()+"/hymn_mp3/"+nowHymn+".mp3z";
//        utils.log("mp3file",fileName);
        File file = new File(fileName);
        FileDescriptor fd;
        if (file.exists()) {
            try {
                FileInputStream fs = new FileInputStream(file);
                fd = fs.getFD();
                mediaPlayer.setDataSource(fd);
                fs.close();
                mediaPlayer.setLooping(false);
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(hymnSpeed));
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            });
            mediaPlayer.start();
        } else {
            Toast.makeText(mContext, "찬송가 "+nowHymn+"장 음악 파일이 없습니다.", Toast.LENGTH_LONG).show();
            isReadingNow = false;
        }
    }

    void stopRead() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mTTS.stop();
        isReadingNow = false;
        vCurrBible.setBackgroundColor(normalMenuColor);
    }
}
