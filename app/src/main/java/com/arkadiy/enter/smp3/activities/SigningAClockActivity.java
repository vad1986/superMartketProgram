package com.arkadiy.enter.smp3.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.services.UserServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SigningAClockActivity extends AppCompatActivity {
    private TextView enterDataTextView;
    private TextView exitDataTextView;
    private Button enterButton;
    private Button exitButton;
    private JSONObject jsonClock;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing_aclock);
        enterDataTextView = (TextView)findViewById(R.id.enterData_TextView);
        exitDataTextView = (TextView)findViewById(R.id.exitData_TextView);
        enterButton = (Button)findViewById(R.id.enter_button);
        exitButton = (Button)findViewById(R.id.exit_Button);

        requestQueue = Volley.newRequestQueue(this);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterDataTextView.setText("");
                String date = getTime();
                enterDataTextView.setText(date);
                sendToServer(date,1);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDataTextView.setText("");
                exitDataTextView.setText(getTime());
                sendToServer(exitDataTextView.getText().toString(),2);
            }
        });
    }
    public void sendToServer(String time,int in_out){

        try {
            jsonClock = new JSONObject();
            jsonClock.put("place" ,"123456789123");
            jsonClock.put("in_out" ,in_out); // in_out String
            jsonClock.put("date_time" ,String.valueOf(time)); // time String
            UserServices.sendData(AppConfig.PUNCH_CLOCK,jsonClock,requestQueue,SigningAClockActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getTime(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }
}
