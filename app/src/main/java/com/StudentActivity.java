package com;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.backend.GetInfoService;
import com.example.hackatone.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.ParcelFileDescriptor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StudentActivity extends AppCompatActivity {

    TextView profileTextView;
    private String schoolName, classNum, studentName;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle data = getIntent().getExtras();
        if(data != null){
            setTitle(data.getString("StudentName"));
        }else{
            setTitle("");
        }
        schoolName = data.getString("SchoolName");
        classNum = data.getString("ClassNum");
        studentName = data.getString("StudentName");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        profileTextView = findViewById(R.id.profile_content_text_view);

        profileTextView.setText(getStudentTextContent(schoolName, classNum, studentName));

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getStudentTextContent(String schoolName, String classNum, String studentName){
        String str = GetInfoService.getInstance().getStudentInfo(this, schoolName, classNum, studentName);
        if(str == null){
            Toast.makeText(this, R.string.noFileMessage, Toast.LENGTH_LONG).show();
            return "";
        }
        return str;
    }

    public void editText(View view) {
        //Получаем вид с файла input_edit_text.xml, который применим для диалогового окна:
        LayoutInflater li = LayoutInflater.from(StudentActivity.this);
        final View promptsView = li.inflate(R.layout.popup_profile_dialog_alert, null);

        //Создаем AlertDialog
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(StudentActivity.this);

        //Настраиваем input_edit_text.xml для нашего AlertDialog:
        mDialogBuilder.setView(promptsView);

        //Настраиваем отображение поля для ввода текста в открытом диалоге:
        final EditText userInput = (EditText) promptsView.findViewById(R.id.input_edit_text);

        //Настраиваем сообщение в диалоговом окне:
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                profileTextView.setText(userInput.getText().toString());
                                GetInfoService.getInstance().setStudentInfo(StudentActivity.this, schoolName, classNum, studentName);
                            }
                        })
                .setNegativeButton( R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        //Создаем AlertDialog:
        AlertDialog alertDialog = mDialogBuilder.create();

        //и отображаем его:
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(getApplicationContext(), "47 "+requestCode+" "+resultCode, Toast.LENGTH_LONG).show();
        if(requestCode==47 && resultCode==RESULT_OK)
        {
            Uri uri = null;
            if (data!=null){
                uri = data.getData();

                //Toast.makeText(getApplicationContext(), "47 "+uri.toString(), Toast.LENGTH_LONG).show();
                Log.d("Document saved",
                        getSharedPreferences("Students", Activity.MODE_PRIVATE).edit().putString(schoolName+"$"+classNum+"$"+studentName, uri.toString()).commit() +""
                        );

                final Uri uri1 = uri;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        /*try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                        editDocument(uri1, profileTextView.getText().toString());
                    }
                }).start();
            }


        }
    }
    private void editDocument(Uri uri, String text){
        try {
            // default encoding utf-8
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(text.getBytes(StandardCharsets.UTF_16BE));
            /*PrintWriter writer = new PrintWriter(fileOutputStream);
            writer.println(resultFileString);
            writer.close();*/

            fileOutputStream.close();
            pfd.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}