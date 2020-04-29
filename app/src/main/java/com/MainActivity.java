package com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.backend.GetInfoService;
import com.example.hackatone.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    ListView listView;
    Spinner schoolSpinner;
    Spinner classSpinner;

    String currentSchool;
    String currentClass;

    ArrayList<HashMap<String, String>> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialise Views
        listView = findViewById(R.id.main_activity_list_view);
        schoolSpinner = findViewById(R.id.school_chooser_spiner);
        classSpinner = findViewById(R.id.student_chooser_spiner);

        // do school's Spinner
        ArrayList<HashMap<String, String>> schoolList = new ArrayList<>();
        setTestArrayList(schoolList);
        SimpleAdapter schoolSpinnerAdapter = new SimpleAdapter(this, GetInfoService.getInstance().getSchools(), android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Name"},
                new int[]{android.R.id.text1});
        schoolSpinner.setAdapter(schoolSpinnerAdapter);

        // do class's Spinner
        ArrayList<HashMap<String, String>> classesArrayList = new ArrayList<>(11);
        HashMap<String, String> hashMap;
        for (int i = 1; i <= 11; i++) {
            hashMap = new HashMap<>();
            hashMap.put("Class", String.valueOf(i));
            classesArrayList.add(hashMap);
        }
        SimpleAdapter classSpinnerAdapter = new SimpleAdapter(this, classesArrayList, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Class"},
                new int[]{android.R.id.text1});
        classSpinner.setAdapter(classSpinnerAdapter);

        // do students List
        SimpleAdapter listAdapter = new SimpleAdapter(this, studentList, R.layout.list_item,
                new String[]{"Name"},
                new int[]{android.R.id.text1});
        listView.setAdapter(listAdapter);
    }

    private void setTestArrayList(ArrayList<HashMap<String, String>> list){
        HashMap hashMap;
        for (int i = 0; i < 30; i++) {
            // Досье на первого кота
            hashMap = new HashMap<>();
            hashMap.put("Name", "Мурзик");
            list.add(hashMap);

            // Досье на второго кота
            hashMap = new HashMap<>();
            hashMap.put("Name", "Барсик");
            list.add(hashMap);

            // Досье на третьего кота
            hashMap = new HashMap<>();
            hashMap.put("Name", "Васька");
            list.add(hashMap);
        }

    }

    public void updateUI(View view) {
//        setTestArrayList(studentList);

        // clearing for new data
        studentList.clear();

        // putting new data to list
        for (HashMap hashMap:
                GetInfoService.getInstance().getStudents(
                        (currentSchool = ((TextView)(schoolSpinner.getSelectedView())).getText().toString()),
                        (currentClass = ((TextView)(classSpinner.getSelectedView())).getText().toString())
                )
        ) {
            studentList.add(hashMap);
        }
        Log.d(TAG, "Current school: " + currentSchool);
        Log.d(TAG, "Current class: " + currentClass);

        // update
        SimpleAdapter listAdapter = new SimpleAdapter(this, studentList, R.layout.list_item,
                new String[]{"Name"},
                new int[]{android.R.id.text1});
        listView.setAdapter(listAdapter);

        // Log
        Log.d(TAG, "UI was updated!");
    }

    public void goProfile(View view) {
        Bundle instance = new Bundle();
        instance.putString("School", currentSchool);
        instance.putString("Class", currentClass);
        instance.putString("Student", "Student"/*((TextView) view).getText().toString()*/);

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent, instance);

    }
}