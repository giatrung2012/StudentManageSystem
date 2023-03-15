package com.example.studentmanagesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnOpenClass, btnOpenStudent, btnExitApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenClass = findViewById(R.id.btnOpenClass);
        btnOpenStudent = findViewById(R.id.btnOpenStudent);
        btnExitApp = findViewById(R.id.btnExitApp);

        btnOpenClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        ClassListActivity.class);
                startActivity(intent);
            }
        });

        btnOpenStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        StudentListActivity.class);
                startActivity(intent);
            }
        });

        btnExitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notify.exit(MainActivity.this);
            }
        });
    }
}