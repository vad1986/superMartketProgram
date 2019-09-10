package com.arkadiy.enter.smp3.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.config.ResponseCode;
import com.arkadiy.enter.smp3.dataObjects.IHandler;
import com.arkadiy.enter.smp3.dataObjects.User;
import com.arkadiy.enter.smp3.services.GpsChecker;
import com.arkadiy.enter.smp3.utils.Constants;
import com.arkadiy.enter.smp3.utils.ConstantsJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SigningAClockActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private TextView enterDataTextView;
    private TextView exitDataTextView;
    private TextView userName;
    private Button enterButton;
    private Button exitButton;
    private JSONObject jsonClock;
    private RequestQueue requestQueue;
    private Criteria criteria;
    private LocationManager locationManager;
    private GpsChecker checker;
    Handler handler;
    private int in_out;
    private IHandler iHandler;
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing_aclock);

//        file = new File(getFilesDir(),FileConfig.SING_IN_A_CLOCK);


        initGPS();// build GPS items

        enterDataTextView = (TextView) findViewById(R.id.enterData_TextView);
        exitDataTextView = (TextView) findViewById(R.id.exitData_TextView);
        enterButton = (Button) findViewById(R.id.enter_button);
        exitButton = (Button) findViewById(R.id.exit_Button);
        userName = (TextView) findViewById(R.id.UserName_TextView);
//        userName.setText("Hello "+User.getUserName()); //TODO: FIX THIS
        requestQueue = Volley.newRequestQueue(this);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in_out = Constants.SET_IN_OCLOCK;

                //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0.0f, this);
                checkLocation();
                sendToServer(getTime(), in_out);

                //sendToServer(getTime(), in_out);


            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in_out = Constants.SET_OUT_OCLOCK;
                checkLocation();
//                exitDataTextView.setText("");
//                exitDataTextView.setText(getTime());
            }
        });
    }


    public void sendToServer(String time, int in_out) {


        try {
            jsonClock = new JSONObject();
            jsonClock.put("Latitude", Double.parseDouble(checker.getLatitude()));
            jsonClock.put("Longtitude", Double.parseDouble(checker.getLongtitude()));
            jsonClock.put("in_out", in_out); // in_out String
            jsonClock.put("date_time", String.valueOf(time)); // time String
            User.punchClock(SigningAClockActivity.this, jsonClock, AppConfig.PUNCH_CLOCK, requestQueue, iHandler -> {
                if (iHandler.getData().getInt(ConstantsJson.RESPONSE_CODE) == ResponseCode.NOT_IN_RANGE) {

                } else if (iHandler.getData().getInt(ConstantsJson.RESPONSE_CODE) == ResponseCode.PUNCH_CLOCK) {
                    chengVisibility(time);
                }
                return true;
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String StringDate = df.format(Calendar.getInstance().getTime());
        return StringDate;
    }

    private void initGPS() {


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        checker = new GpsChecker(locationManager, this, afterGotLocation -> {
//            Bundle bundle = msg.getData();
            String date = getTime();
            if (afterGotLocation.getData() != null) {
//                   userName.setText(checker.getLatitude());
//                if (Constants.SET_IN_OCLOCK == in_out) {
//
//                    chengVisibility(date);
//
//                } else if (Constants.SET_OUT_OCLOCK == in_out) {
//
//                    chengVisibility(date);
//
//                }

               // sendToServer(date, in_out);
            }
            return true;
        });
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
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0.0f, checker);
//        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);



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

        locationManager.requestLocationUpdates(1000,1,criteria,checker,null);

//        locationManager.removeUpdates(checker);
//        locationManager=null;

    }
    private void checkLocation(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //
        // locationManager.requestLocationUpdates(1000,1,criteria,checker,null);
        locationManager.requestSingleUpdate("gps",checker,null);

    }

    public void chengVisibility (String date){

        if (enterButton.getVisibility()== View.VISIBLE){

            enterDataTextView.setText("");
            enterDataTextView.setText(date);
            enterButton.setVisibility(View.GONE);
            exitButton.setVisibility(View.VISIBLE);
            String txt = enterDataTextView.getText().toString();
//            ReadWriteFile.writeInToFile(FileConfig.SING_IN_A_CLOCK,txt,
//                    SigningAClockActivity.this);

        }else if(exitButton.getVisibility() == View.VISIBLE){

            exitDataTextView.setText("");
            exitDataTextView.setText(date);
            enterButton.setVisibility(View.VISIBLE);
            exitButton.setVisibility(View.GONE);
        }

    }


}
