package com.arkadiy.enter.smp3.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.services.GpsChecker;
import com.arkadiy.enter.smp3.services.UserServices;
import com.arkadiy.enter.smp3.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SigningAClockActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    private TextView enterDataTextView;
    private TextView exitDataTextView;
    private Button enterButton;
    private Button exitButton;
    private JSONObject jsonClock;
    private RequestQueue requestQueue;
    private Criteria criteria;
    private LocationManager locationManager;
    private GpsChecker checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing_aclock);
        enterDataTextView = (TextView) findViewById(R.id.enterData_TextView);
        exitDataTextView = (TextView) findViewById(R.id.exitData_TextView);
        enterButton = (Button) findViewById(R.id.enter_button);
        exitButton = (Button) findViewById(R.id.exit_Button);
        initGPS(); // build GPS items
        requestQueue = Volley.newRequestQueue(this);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterDataTextView.setText("");
                String date = getTime();
                enterDataTextView.setText(date);
                sendToServer(date, 1);
                checkLocation();
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDataTextView.setText("");
                exitDataTextView.setText(getTime());
                sendToServer(exitDataTextView.getText().toString(), 2);
                checkLocation();
            }
        });
    }

    public void sendToServer(String time, int in_out) {

        try {
            jsonClock = new JSONObject();
            jsonClock.put("place", "123456789123");
            jsonClock.put("in_out", in_out); // in_out String
            jsonClock.put("date_time", String.valueOf(time)); // time String
            UserServices.sendData(AppConfig.PUNCH_CLOCK, jsonClock, requestQueue, SigningAClockActivity.this, Constants.METHOD_POST, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    private void initGPS() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        checker = new GpsChecker(locationManager, this);
        checker.run();
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setBearingRequired(false);

        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
    }
    private void checkLocation(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        locationManager.requestLocationUpdates(1000, 1, criteria, checker, null);
//        locationManager.requestLocationUpdates("gps", 5000, 0, checker);
        locationManager.requestLocationUpdates(1000, 1, criteria, checker,null);


    }
}
