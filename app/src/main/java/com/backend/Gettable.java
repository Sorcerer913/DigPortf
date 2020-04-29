package com.backend;

import java.util.ArrayList;
import java.util.HashMap;

public interface Gettable {
    public ArrayList<HashMap<String, String>> getSchools();
    public ArrayList<HashMap<String, String>> getStudents(String schoolName, String classNum);
    public ArrayList<HashMap<String, String>> getStudentInfo(String schoolName, String classNum, String studentName);
}
