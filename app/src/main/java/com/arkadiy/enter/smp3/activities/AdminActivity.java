package com.arkadiy.enter.smp3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.arkadiy.enter.smp3.R;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private Button addNewUser_Btn;
    private Button editUser_Btn;
    private Button editDepartment_Btn;
    private Button createDepartment_Btn;
    private Button createAlert_Btn;
    private Button editAlert_Btn;
    private Button GPS_conf_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addNewUser_Btn = (Button)findViewById(R.id.addNewUser_Btn);
        editUser_Btn = (Button)findViewById(R.id.editUser_Btn);
        editDepartment_Btn = (Button)findViewById(R.id.editDepartment_Btn);
        createDepartment_Btn = (Button)findViewById(R.id.createDepartments_Btn);
        createAlert_Btn = (Button)findViewById(R.id.createAlert_Btn);
        editAlert_Btn = (Button)findViewById(R.id.editAlert_Btn);
        GPS_conf_Btn = (Button)findViewById(R.id.GPS_conf_Btn);
        App.setContext(this);

        addNewUser_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( AdminActivity.this,AddNewUserActivity.class);
                startActivity(intent);
            }
        });

        editUser_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editDepartment_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        createDepartment_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        createAlert_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, CreateAlertActivity.class);
                startActivity(intent);
            }
        });

        GPS_conf_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminActivity.this, GPSActivity.class);
                startActivity(intent);

            }
        });


    }
}
