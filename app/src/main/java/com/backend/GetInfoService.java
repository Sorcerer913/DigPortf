package com.backend;

import java.util.ArrayList;
import java.util.HashMap;

public class GetInfoService implements Gettable{

    // SingleTone pattern
    private static GetInfoService mInstance;
    public static GetInfoService getInstance() {
        if (mInstance == null) {
            mInstance = new GetInfoService();
        }
        return mInstance;
    }

    //TODO: rewrite
    @Override
    public ArrayList<HashMap<String, String>> getSchools() {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap hashMap;
        for (int i = 1; i < 100; i++) {
            // Досье на первого кота
            hashMap = new HashMap<>();
            hashMap.put("Name", "School"+i);
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    //TODO: rewrite
    @Override
    public ArrayList<HashMap<String, String>> getStudents(String schoolName, String classNum) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            HashMap<String, String> hsMap = new HashMap<>();
            hsMap.put("Name", "Student");
            arrayList.add(hsMap);
        }
        return arrayList;
    }

    //TODO: rewrite
    @Override
    public ArrayList<HashMap<String, String>> getStudentInfo(String schoolName, String classNum, String studentName) {
        return null;
    }
}
