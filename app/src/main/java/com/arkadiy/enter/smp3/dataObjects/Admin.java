package com.arkadiy.enter.smp3.dataObjects;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.services.DataServices;
import com.arkadiy.enter.smp3.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class Admin extends Manager {

    public Admin(JSONObject jsonObject) {
        super(jsonObject);
    }

    public static void createNewAlert(Context context, JSONObject newAlert, RequestQueue requestQueue, IHandler i_handlerNewAlert ){

        DataServices.sendData(AppConfig.CREATE_SENTENCE,newAlert,requestQueue, context, Constants.METHOD_POST,i_handler->{

            JSONObject json;
            Message msg = new Message();
            Bundle  bundle = i_handler.getData();

            try {
                json = new JSONObject(bundle.getString("json"));
                Toast.makeText(context,json.getString("message"),Toast.LENGTH_LONG).show();
                //TODO create some response and send this response in to activity


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        });

    }
    public static void addShopSettingLocation(Context context, JSONObject newJsonSettingsLocation, RequestQueue requestQueue, IHandler iHandlerLcation){

        DataServices.sendData(AppConfig.ADD_SHOP_SETTING_LOCATION,newJsonSettingsLocation,requestQueue, context, Constants.METHOD_POST,iHendlerLoc->{

            JSONObject json;
            Message msg = new Message();
            Bundle  bundle = iHendlerLoc.getData();




            return true;
        });


    }
}
