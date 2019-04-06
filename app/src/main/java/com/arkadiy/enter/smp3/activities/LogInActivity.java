package com.arkadiy.enter.smp3.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.services.UserServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LogInActivity extends AppCompatActivity {
    private Button logIn;
    private EditText password;
    private EditText userName;
    private RequestQueue requestQueue;
    private String strFile;
    public  static HashMap<Integer,String> DEPARTMANTS;
    public  static HashMap<String,Integer>MYDEPARTMENTS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        DEPARTMANTS = new HashMap<>();
        MYDEPARTMENTS=new HashMap<>();
        userName = (EditText)findViewById(R.id.userName_editText);
        password = (EditText)findViewById(R.id.password_editText);
        logIn = (Button)findViewById(R.id.logIn_button);
        requestQueue = Volley.newRequestQueue(this);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  jsonParse();



            }
        });
    }
    private void jsonParse(){

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("user_name",userName.getText());
            jsonObject.put("password",password.getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        UserServices.sendData(AppConfig.LOGIN,jsonObject,requestQueue,LogInActivity.this,1, null);



    }

}
