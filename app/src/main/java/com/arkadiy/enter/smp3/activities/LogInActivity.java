package com.arkadiy.enter.smp3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.config.FileConfig;
import com.arkadiy.enter.smp3.dataObjects.User;
import com.arkadiy.enter.smp3.services.UserServices;

import org.json.JSONException;
import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity {
    private Button logIn;
    private EditText password;
    private EditText userName;
    private RequestQueue requestQueue;
    private String strFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        userName = (EditText)findViewById(R.id.userName_editText);
        password = (EditText)findViewById(R.id.password_editText);
        logIn = (Button)findViewById(R.id.logIn_button);

        requestQueue = Volley.newRequestQueue(this);
        strFile = UserServices.readFile(FileConfig.USER_FILE,LogInActivity.this);
        if( strFile!= null){
            UserServices.insertIntoUser(strFile);
//            if(User.getPrivateKey()!= null) {
//
////                Intent intent;
////                intent = new Intent(LogInActivity.this, MainActivity.class);
////                startActivity(intent);
//            }
        }

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                jsonParse();

            }
        });
    }
    private void jsonParse(){


//        String url = "http://35.180.192.138:8080/login";
          String url = AppConfig.MAIN_SERVER_IP + AppConfig.MAIN_SERVER_PORT + AppConfig.LOGIN;
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("user_name",userName.getText());
            jsonObject.put("password",password.getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            int responseCode=response.getInt("response_code");
                            if ( responseCode < 500) {
                                String privateKey=response.getString("private_key");
                                String userName=response.getString("user_name");
                                int userId=response.getInt("user_id");
                                int role=response.getInt("role");

                                if(role!=0 && userId!=0 && privateKey!=null && userName!=null){
                                    User.setPrivateKey(privateKey);
                                    User.setRole(role);
                                    User.setUserName(userName);
                                    User.setUserId(userId);
                                    Intent intent;
                                    UserServices.saveFile(FileConfig.USER_FILE,response.toString(),LogInActivity.this);
                                    intent = new Intent(LogInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }


                            }
                            else Toast.makeText(LogInActivity.this,"One of the ditels is WRONG",Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(jsonObjectRequest);


    }

}
