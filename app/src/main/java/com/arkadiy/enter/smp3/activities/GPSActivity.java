package com.arkadiy.enter.smp3.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.dataObjects.Admin;
import com.arkadiy.enter.smp3.services.GpsChecker;
import com.google.android.gms.maps.MapView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class GPSActivity extends AppCompatActivity {
    private MapView mMap;
    private TextView newLocationTextView;
    private Button getNewLocationBtn;
    private Button sendNewLocationBtn;
    private GpsChecker checker;
    private Criteria criteria;
    private LocationManager locationManager;
    private Switch sw;
    private EditText radiusSizeEditText;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        App.setContext(this);
        sw = (Switch)findViewById(R.id.switchGPS) ;
        requestQueue = Volley.newRequestQueue(this);
        getNewLocationBtn = (Button) findViewById(R.id.get_New_Location_Btn);
        sendNewLocationBtn = (Button) findViewById(R.id.send_new_location_Btn);
        newLocationTextView = (TextView) findViewById(R.id.new_Location_TextView);
        radiusSizeEditText = (EditText) findViewById(R.id.radiusSize_EditText);
//        mMap = (MapView) findViewById(R.id.mapView);
//        mMap.onStart();

        initGPS();

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



            }
        });
        getNewLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkLocation();



            }
        });

        sendNewLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: send string for new location to server
                if(radiusSizeEditText.getText().toString() != ""){

                    sendToServer();
                }
            }
        });

    }
    private void sendToServer(){
        try {

            JSONObject newJsonSettingsLocation = new JSONObject()
                    .put("Latitude",Double.parseDouble(checker.getLatitude()))
                    .put("Longtitude",Double.parseDouble(checker.getLongtitude()))
                    .put("Size",Double.parseDouble(radiusSizeEditText.getText().toString()));
            Admin.addShopSettingLocation(GPSActivity.this,newJsonSettingsLocation,requestQueue,createNewLocSetting->{


                return true;
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void initGPS() {



        locationManager = (LocationManager) GPSActivity.this.getSystemService(Context.LOCATION_SERVICE);

        checker = new GpsChecker(locationManager, GPSActivity.this, handConfGps->{

            if (handConfGps.getData()!=null) {

            }
            return true;
        });
//
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
        checker.onLocationChanged(locationManager.getLastKnownLocation(checker.getLatitude()+checker.getLongtitude()));
        newLocationTextView.setText("Latitude: "+checker.getLatitude() + " Longtitude: "+checker.getLongtitude());



    }
}
