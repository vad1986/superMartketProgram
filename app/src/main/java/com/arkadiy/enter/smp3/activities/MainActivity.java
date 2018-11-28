package com.arkadiy.enter.smp3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.services.UserServices;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  {

    private Button exitButton;
    private Button addNewUserButton;
    private Button signingAClockButton;
    private Button tasksButton;
    private Button addNewTaskButton;
    private ListView promoListView;
    private ListAdapter listAdapter;
    private JSONObject jsonObject;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] promoListString = {"Arkadiy","Vadim","Haim","Gil"};
        exitButton = (Button)findViewById(R.id.exit_Button);
        addNewUserButton = (Button)findViewById(R.id.addNewUser_Button);
        signingAClockButton = (Button)findViewById(R.id.signingAClock_Button);
        tasksButton = (Button)findViewById(R.id.tasks_Button);
        addNewTaskButton = (Button)findViewById(R.id.addNewTask_Button);
        Toolbar toolbar = findViewById(R.id.toolbar_MainActivity);
        toolbar.setTitle("NAME");
        toolbar.setLogo(R.drawable.profil);
        toolbar.getContentInsetStartWithNavigation();
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(this);
        promoListView = (ListView)findViewById(R.id.promo_ListView);
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,promoListString);
        promoListView.setAdapter(listAdapter);

        tasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TasksActivity.class);
                startActivity(intent);
            }
        });

        signingAClockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SigningAClockActivity.class);
                startActivity(intent);
            }
        });

        addNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this,AddNewUserActivity.class);
                startActivity(intent);
            }
        });

        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddNewTaskActivity.class);
                startActivity(intent);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserServices.sendData(AppConfig.MAIN_SERVER_IP+AppConfig.MAIN_SERVER_PORT+AppConfig.LOGOUT_SERVER,jsonObject,requestQueue,MainActivity.this);

            }
        });

    }


}
