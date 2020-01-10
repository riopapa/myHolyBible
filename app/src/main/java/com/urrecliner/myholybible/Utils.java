package com.urrecliner.myholybible;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.urrecliner.myholybible.Vars.packageFolder;
import static com.urrecliner.myholybible.Vars.sharedPreferences;

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
        return lines.toArray(new String[0]);
    }

    void log(String tag, String text) {
        StackTraceElement[] traces;
        traces = Thread.currentThread().getStackTrace();
        String where = " " + traces[5].getMethodName() + " > " + traces[4].getMethodName() + " > " + traces[3].getMethodName() + " #" + traces[3].getLineNumber();
        Log.w(tag , where + " " + text);
    }

    void savePrefers(String key, ArrayList arrayList) {

        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }

    ArrayList<GoBack> readGoBacks() {

        ArrayList<GoBack> list;
        Gson gson = new Gson();
        String json = sharedPreferences.getString("goBack", "");
        if (json.isEmpty()) {
            list = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<GoBack>>() {
            }.getType();
            list = gson.fromJson(json, type);
        }
        return list;
    }

    ArrayList<BookMark> readBookMarks() {

        ArrayList<BookMark> list;
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookMark", "");
        if (json.isEmpty()) {
            list = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<BookMark>>() {
            }.getType();
            list = gson.fromJson(json, type);
        }
        return list;
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
