package com.arkadiy.enter.smp3.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.dataObjects.Users;
import com.arkadiy.enter.smp3.services.UserServices;
import com.arkadiy.enter.smp3.utils.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity  implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Button exitButton;
    private Button addNewUserButton;
    private Button signingAClockButton;
    private Button tasksButton;
    private Button addNewTaskButton;
    private ListView promoListView;
    private ListAdapter listAdapter;
    private JSONObject jsonObject;
    private RequestQueue requestQueue;
    public static HashMap<Integer,ArrayList<Users>> USERS;
    static final Integer LOCATION = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        USERS =  new HashMap<>();
        setContentView(R.layout.activity_main);
        String[] promoListString = {"Arkadiy","Vadim","Haim","Gil"};
        exitButton = (Button)findViewById(R.id.exit_Button);
        addNewUserButton = (Button)findViewById(R.id.addNewUser_Button);
        signingAClockButton = (Button)findViewById(R.id.signingAClock_Button);
        tasksButton = (Button)findViewById(R.id.tasks_Button);
        addNewTaskButton = (Button)findViewById(R.id.addNewTask_Button);
//        Toolbar toolbar = findViewById(R.id.toolbar_MainActivity);
//        toolbar.setTitle("NAME");
//        toolbar.setLogo(R.drawable.profil);
//        toolbar.getContentInsetStartWithNavigation();
//        setSupportActionBar(toolbar);
        requestQueue = Volley.newRequestQueue(this);
        UserServices.sendData(AppConfig.GET_MY_USERS,null,requestQueue,MainActivity.this,Constants.METHOD_GET,null);


        //here start all gps content

        //here ends gps



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
                UserServices.sendData(AppConfig.LOGOUT_SERVER,jsonObject,requestQueue,MainActivity.this,Constants.METHOD_POST,null);

            }
        });




        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }



    }

    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                //Location
                case 1:
                    askForGPS();
                    break;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void askForGPS() {
//        locationManager.requestLocationUpdates(1000, 1, criteria, checker,null);

    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }


}
