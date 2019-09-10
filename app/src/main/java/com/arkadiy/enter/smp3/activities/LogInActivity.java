package com.arkadiy.enter.smp3.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.arkadiy.enter.smp3.DataBaseHelperGlobalRoles;
import com.arkadiy.enter.smp3.DataBaseHelperUserConnected;
import com.arkadiy.enter.smp3.dialogs.DialogforgotPassword;
import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.dataObjects.User;
import com.arkadiy.enter.smp3.utils.ConstantsJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LogInActivity extends AppCompatActivity implements DialogforgotPassword.DialogforgotPasswordListener {
    private Button logIn;
    private static EditText password;
    private static EditText userName;
    private RequestQueue requestQueue;
    public  static HashMap<Integer,String> DEPARTMANTS;
    public  static HashMap<String,Integer>MYDEPARTMENTS;
    public static DataBaseHelperUserConnected dataBaseHelper;
    public static DataBaseHelperGlobalRoles dataBaseHelperGlobalRoles;
    private TextView forgotPasswordTextVView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        dataBaseHelper = new DataBaseHelperUserConnected(LogInActivity.this);
        dataBaseHelperGlobalRoles = new DataBaseHelperGlobalRoles(LogInActivity.this);



        DEPARTMANTS = new HashMap<>();
        MYDEPARTMENTS=new HashMap<>();
        userName = (EditText)findViewById(R.id.userName_editText);
        password = (EditText)findViewById(R.id.password_editText);
        logIn = (Button)findViewById(R.id.logIn_button);
        forgotPasswordTextVView = (TextView)findViewById(R.id.forgotPasswordTextView);
        //requestQueue = Volley.newRequestQueue(this);
        Context context = LogInActivity.this;
        User.init(context);
//        String privateKey=dataBaseHelper.ifExist();
//        if (privateKey!=null){
//            DataServices.getUserIntoDataBase(user);
//            DataServices.sendData(AppConfig.CHECK_PRIVATE_KEY+ privateKey,
//                    null,requestQueue,LogInActivity.this,
//                    Constants.METHOD_GET,null,user);
//
//            Intent intent = new Intent( LogInActivity.this,MainActivity.class);
//            startActivity(intent);
//        }
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              User.logIn(jsonParse());
//                        DataServices.sendData(AppConfig.LOGIN,jsonParse(),requestQueue,
//                        LogInActivity.this,1, null,user);

            }
        });

        forgotPasswordTextVView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogforgotPassword dialogforgotPassword = new DialogforgotPassword();
                dialogforgotPassword.show(getSupportFragmentManager(),userName.getText().toString());
//                Intent intent = new Intent(LogInActivity.this,ForgotPassword.class);
//                startActivity(intent);
            }
        });

    }
    private JSONObject jsonParse(){

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put(ConstantsJson.USERNAME,userName.getText());
            jsonObject.put(ConstantsJson.PASSWORD,password.getText());
            return  jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static void resetValueUserInput (){
        userName.setText("");
        password.setText("");
    }


    @Override
    public void applyText(String email) {

        JSONObject emailJson = new JSONObject();
        String url = AppConfig.CLOSE_TASK;
        try {
            emailJson.put("email",email);

            //DataServices.sendData(url,emailJson,requestQueue,LogInActivity.this,Constants.METHOD_POST,null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(LogInActivity.this,email,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {


    }
}
