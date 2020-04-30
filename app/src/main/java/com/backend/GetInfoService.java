package com.backend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GetInfoService{

    private final static String TAG = "GetInfoService";

    // SingleTone pattern
    private static GetInfoService mInstance;
    public static GetInfoService getInstance() {
        if (mInstance == null) {
            mInstance = new GetInfoService();
        }
        return mInstance;
    }

    //TODO: rewrite
    public ArrayList<HashMap<String, String>> getSchools() {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap hashMap;
        hashMap = new HashMap<>();
        hashMap.put("Name", "School1363");
        arrayList.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("Name", "School1539");
        arrayList.add(hashMap);
        hashMap = new HashMap<>();
        hashMap.put("Name", "Synergy");
        arrayList.add(hashMap);
        return arrayList;
    }

    //TODO: rewrite
    public ArrayList<HashMap<String, String>> getStudents(String schoolName, String classNum) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        if(schoolName.equals("School1363") && classNum.equals("10")){
            HashMap<String, String> hsMap;
            hsMap = new HashMap<>();
            hsMap.put("Name", "Emelianov Maxim Alexadrovich");
            arrayList.add(hsMap);
            hsMap = new HashMap<>();
            hsMap.put("Name", "Bacumenko Artem Sergeevich");
            arrayList.add(hsMap);
        }
        if(schoolName.equals("School1539") && classNum.equals("9")){
            HashMap<String, String> hsMap;
            hsMap = new HashMap<>();
            hsMap.put("Name", "Emelianov Maxim Alexadrovich");
            arrayList.add(hsMap);
            hsMap = new HashMap<>();
            hsMap.put("Name", "Marya Ivanovna");
            arrayList.add(hsMap);
        }

        return arrayList;
    }

    //TODO: rewrite
    public String getStudentInfo(Activity activity, String schoolName, String classNum, String studentName) {
        /*try {
            File file = new File(schoolName+"/"+classNum+"/"+studentName+".txt");
            Log.d(TAG, "File canonical path:" + file.getAbsolutePath());
            Log.d(TAG, schoolName+" exists: " + new File(schoolName).exists());
            Log.d(TAG, classNum+" exists: " + new File(schoolName+"/"+classNum).exists());
            Log.d(TAG, studentName+" exists: " + new File(schoolName+"/"+classNum+"/"+studentName+".txt").exists());

            if (!(file = new File(schoolName)).exists() || !file.isDirectory()) {
                Log.d(TAG, "schoolDir hasCreated: "+file.mkdirs());
            }
            if (!(file = new File(schoolName+"/"+classNum)).exists() || !file.isDirectory()){
                Log.d(TAG, "classDir hasCreated: "+file.mkdirs());
            }
            if (!(file = new File(schoolName+"/"+classNum+"/"+studentName+".txt")).exists()){
                Log.d(TAG, "studentFile hasCreated: "+file.createNewFile());
                return null;
            }

            StringBuilder stringBuilder = new StringBuilder();

            Scanner in = new Scanner(file);

            while (in.hasNextLine()){
                stringBuilder.append(in.nextLine());
            }
            in.close();

            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }*/
        /*
        try {
            Uri uri = new Uri.Builder().path(activity.getSharedPreferences("Students", Activity.MODE_PRIVATE).getString(schoolName+"$"+classNum+"$"+studentName, "")).build();
            Log.d(TAG, "URI: "+uri.toString());
            StringBuilder stringBuilder = new StringBuilder();

            ParcelFileDescriptor pfd = activity.getContentResolver().openFileDescriptor(uri, "r");
            FileInputStream fileInputStream = new FileInputStream(pfd.getFileDescriptor());

//            Scanner in = new Scanner(new File(uri.getPath()));

            *//*while (in.hasNextLine()){
                stringBuilder.append(in.nextLine());
            }
            in.close();*//*

            return stringBuilder.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
        */
        return null;
    }

    //TODO: rewrite
    public void setStudentInfo(Activity activity, String schoolName, String classNum, String studentName) {
//        File file = new File(schoolName+"/"+classNum+"/"+studentName+".txt");
        //            Log.d(TAG, "File canonical path:" + file.getCanonicalPath());
//            if (!file.exists()){
//                file.createNewFile();
//            }
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, schoolName+"$"+classNum+"$"+studentName+".txt");
        activity.startActivityForResult(intent, 47);

    }
}
