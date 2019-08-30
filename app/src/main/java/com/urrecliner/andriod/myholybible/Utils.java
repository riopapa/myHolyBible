package com.urrecliner.andriod.myholybible;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.urrecliner.andriod.myholybible.Vars.packageFolder;
import static com.urrecliner.andriod.myholybible.Vars.sharePrefer;

class Utils {

    private MainActivity mActivity;

    Utils(MainActivity activity) { mActivity = activity; }

    String[] readBibleFile(String filename) {
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

    void log(String tag, String text) {
        StackTraceElement[] traces;
        traces = Thread.currentThread().getStackTrace();
        String where = " " + traces[5].getMethodName() + " > " + traces[4].getMethodName() + " > " + traces[3].getMethodName() + " #" + traces[3].getLineNumber();
        Log.w(tag , where + " " + text);
    }

    void setStringArrayPref(String key, ArrayList<String> values) {
        SharedPreferences.Editor editor = sharePrefer.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    ArrayList<String> getStringArrayPref(String key) {
        String json = sharePrefer.getString(key, null);
        ArrayList<String> urls = new ArrayList<>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    void setIntArrayPref(String key, ArrayList<Integer> values) {
        SharedPreferences.Editor editor = sharePrefer.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    ArrayList<Integer> getIntArrayPref(String key) {
        String json = sharePrefer.getString(key, null);
        ArrayList<Integer> urls = new ArrayList<>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    int url = a.optInt(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    ArrayList<String> readRawTextFile(Context ctx, int resId)
    {
        ArrayList<String> lines = new ArrayList<String>();
        InputStream inputStream = ctx.getResources().openRawResource(resId);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream,"UTF16"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String line = "";
        while (line != null) {
            try {
                line = reader.readLine();
                if (line != null)
                    lines.add(line);
                else
                    break;
            } catch (IOException e) {
                line = null;
            }
        }
        return lines;
    }
}
