package com.arkadiy.enter.smp3.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.dataObjects.NewUser;

public class AddNewUserActivity extends AppCompatActivity {

    private NewUser newUser;
    private Button creatUserButton;





    private EditText addNewUserNameEditText;
    private EditText addNewUserPasswordEditText;
    private EditText addNewUserFirstNameEditText;
    private EditText addNewUserSecondNameEditText;
    private EditText addNewUserRoleEditText;
    private EditText addnewUserManagerEditText;
    private EditText addNewUserCityEditText;
    private EditText addNewUserStreetEditText;
    private EditText addNewUserHouseNumberEditText;
    private EditText addNewUserDoorNumberEditText;
    private EditText addNewUserTelephoneEditText;
    private EditText addNewUserEmailEditText;
    private RadioGroup sexRadioGroup;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);



        addNewUserNameEditText = (EditText)findViewById(R.id.addNewUserName_editText);
        addNewUserPasswordEditText = (EditText)findViewById(R.id.addNewUserPassword_editText);
        addNewUserFirstNameEditText = (EditText)findViewById(R.id.addNewUserFirstName_EditText);
        addNewUserSecondNameEditText = (EditText)findViewById(R.id.addNewUserSecondName_EditText);
        addNewUserRoleEditText = (EditText)findViewById(R.id.addNewUserRole_EditText);
        addnewUserManagerEditText = (EditText)findViewById(R.id.addnewUserManager_EditText);
        addNewUserCityEditText = (EditText)findViewById(R.id.addNewUserCity_EditText);
        addNewUserStreetEditText = (EditText)findViewById(R.id.addNewUSerStreet_EditText);
        addNewUserHouseNumberEditText = (EditText)findViewById(R.id.addNewUSerHouseNumber_EditText);
        addNewUserDoorNumberEditText = (EditText)findViewById(R.id.addNewUserDoorNumber_EditText);
        addNewUserTelephoneEditText = (EditText)findViewById(R.id.addNewUserTelephone_EditText);
        addNewUserEmailEditText = (EditText)findViewById(R.id.addNewUserEmail_EditText);
        sexRadioGroup = (RadioGroup)findViewById(R.id.sex_RadioGroup);
        maleRadioButton = (RadioButton)findViewById(R.id.male_RadioButton);
        femaleRadioButton = (RadioButton)findViewById(R.id.female_RadioButton);

        creatUserButton = (Button) findViewById(R.id.addNewUser_Button);

        context = this;

        creatUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(addNewUserNameEditText.getText().toString() != ""
                       && addNewUserPasswordEditText.getText().toString()   !=""
                       && addNewUserFirstNameEditText.getText().toString()  !=""
                       && addNewUserSecondNameEditText.getText().toString() !=""
                       && addNewUserRoleEditText.getText().toString()       !=""
                       && addnewUserManagerEditText.getText().toString()    !=""
                       && addNewUserCityEditText.getText().toString()       !=""
                       && addNewUserStreetEditText.getText().toString()     !=""
                       && addNewUserHouseNumberEditText.getText().toString()!=""
                       && addNewUserDoorNumberEditText.getText().toString() !=""
                       && addNewUserTelephoneEditText.getText().toString()  !=""
                       && addNewUserEmailEditText.getText().toString()      !="" ){

                   newUser = new NewUser(addNewUserNameEditText.getText().toString(), addNewUserFirstNameEditText.getText().toString(), addNewUserSecondNameEditText.getText().toString(), addNewUserPasswordEditText.getText().toString(),
                           addNewUserCityEditText.getText().toString(),addNewUserStreetEditText.getText().toString(),
                           Integer.parseInt(addNewUserHouseNumberEditText.getText().toString()), Integer.parseInt(addNewUserDoorNumberEditText.getText().toString()),
                           addNewUserTelephoneEditText.getText().toString(), addNewUserEmailEditText.getText().toString(),
                           Integer.parseInt(addNewUserRoleEditText.getText().toString()),context);

               }


            }

        });
    }


//    public void creatNewUser(){
//        String url =AppConfig.MAIN_SERVER_IP+AppConfig.MAIN_SERVER_PORT+"/manager/new_user";



//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
//                newUser.getJsonNewUser(), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    int respons = response.getInt("response_code");
//                    Toast.makeText(AddNewUserActivity.this,respons,Toast.LENGTH_LONG);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            /** Passing some request headers* */
//            @Override
//            public Map getHeaders() throws AuthFailureError {
//                HashMap headers = new HashMap();
//                headers.clear();
//                headers.put("Content-Type", "application/json");
//                headers.put("user_id", "2");
//                return headers;
//            }
//        };
//    }
}
//    "user_name":"as233e",
//            "first_name":"arkashaa",
//            "second_name":"nurmagamedov",
//            "password":"araara123",
//            "city":"yehud",
//            "street":"histadrut",
//            "house_number":2,
//            "door_number":1,
//            "telephone":"0546567712",
//            "email":"ahshjbsh@mail.com",
//            "role":2