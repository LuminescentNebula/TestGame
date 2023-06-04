package com.example.testgame.data;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class JSONSaver {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public JSONSaver(){
    }

    public static void save(Ships ship,Context context){

    }

    void writeFile(String text,Context context) {
        try {
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(context
                            .openFileOutput("myfile", Context.MODE_PRIVATE)));
            bw.write(text);
            bw.close();
            Log.d("FILE", "Файл записан");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readFile(Context context) {
        StringBuilder output = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(context.openFileInput("myfile")));
            String str = "";
            while ((str = br.readLine()) != null) {
                output.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
