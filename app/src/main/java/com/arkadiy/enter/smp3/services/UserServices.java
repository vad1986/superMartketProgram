package com.arkadiy.enter.smp3.services;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.arkadiy.enter.smp3.activities.LogInActivity;
import com.arkadiy.enter.smp3.config.FileConfig;
import com.arkadiy.enter.smp3.dataObjects.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserServices {

    public static void sendData(final String url, final JSONObject jsonObject, final RequestQueue requestQueue, final Context context){


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(String.valueOf(response));
                            if(jsonResponse.getInt("response_code") >=500 ){
                                LogOut (context);
//                                Intent intent = new Intent(context,LogInActivity.class);
//


                            }else if(jsonResponse.getInt("response_code")==8){
                                UserServices.LogOut (context);
//                                Intent intent = new Intent(context,LogInActivity.class);
//                                context.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        response.hashCode();
                        response.toString();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                //           headers.put("Content-Type", "application/json");



                headers.put("user_name",User.getUserName());
                headers.put("user_id", User.getUserId());
                headers.put("private_key",User.getPrivateKey());




                return headers;
            }
        };

        requestQueue.add(jsonObjReq);


    }

    public static void insertIntoUser(String str){
        try {
            JSONObject j = new JSONObject(str);
//            private static String userName;
//            private static String privateKey;
//            private static int userId;
//            private static int role;
            User.setUserName(j.getString("user_name"));
            User.setPrivateKey(j.getString("private_key"));
            User.setUserId(j.getInt("user_id"));
            User.setRole(j.getInt("role"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void saveFile(String file,String text,Context context){

        try{
            FileOutputStream fos = context.openFileOutput(file,Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(context,"Saved!",Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {
            Toast.makeText(context,"Error saving file!",Toast.LENGTH_LONG).show();
        }

    }

    public static String readFile(String file,Context context){
        String text="";

        try{
            FileInputStream fis = context.openFileInput(file);
            int size = fis.available();
            byte [] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(context,"Error reading file!",Toast.LENGTH_LONG).show();
        }
        return text;
    }
    public static void LogOut (final Context context){

        saveFile(FileConfig.USER_FILE,"",context);
        insertIntoUser("");
        Intent intent = new Intent(context,LogInActivity.class);
        context.startActivity(intent);



    }
}
