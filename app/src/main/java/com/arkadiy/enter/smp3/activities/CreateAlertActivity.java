package com.arkadiy.enter.smp3.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.config.ResponseCode;
import com.arkadiy.enter.smp3.dataObjects.Admin;
import com.arkadiy.enter.smp3.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAlertActivity extends AppCompatActivity {

    private TextView nameForNewAlert_textView;
    private TextView descriptionForNewAlert_textView;
    private Button createNewAlet_button;
    private int CREATE_NEW_ALERT_ID = 0;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alert);
        requestQueue = Volley.newRequestQueue(this);
        nameForNewAlert_textView = (TextView)findViewById(R.id.name_new_alertEditText);
        descriptionForNewAlert_textView = (TextView)findViewById(R.id.description_alertEditText);
        createNewAlet_button = (Button)findViewById(R.id.send_new_alertButton);
        App.setContext(this);
        createNewAlet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameForNewAlert_textView.getText() !="" && descriptionForNewAlert_textView.getText()!=""){
                    createAlert();
                }
            }
        });



    }
    private void createAlert(){

        try {
            JSONObject newAlert = new JSONObject().put("name",nameForNewAlert_textView.getText().toString()
            ).put("description",descriptionForNewAlert_textView.getText().toString()).put("id",CREATE_NEW_ALERT_ID);
            Admin.createNewAlert(CreateAlertActivity.this,newAlert,requestQueue,i_handler->{
                if (i_handler.getData().getInt(Constants.RESPONSE_CODE) < ResponseCode.ERROR){

                }

                return true;
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
