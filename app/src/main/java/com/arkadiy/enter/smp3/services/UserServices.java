package com.arkadiy.enter.smp3.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.arkadiy.enter.smp3.activities.LogInActivity;
import com.arkadiy.enter.smp3.activities.MainActivity;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.config.FileConfig;
import com.arkadiy.enter.smp3.dataObjects.JsonAssembler;
import com.arkadiy.enter.smp3.dataObjects.Task;
import com.arkadiy.enter.smp3.dataObjects.User;
import com.arkadiy.enter.smp3.dataObjects.Users;
import com.arkadiy.enter.smp3.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.arkadiy.enter.smp3.utils.Constants.TASKS;

public class UserServices {
    private static ArrayList<Task> tasks = null;
    public static void sendData(final String url, final JSONObject jsonObject, final RequestQueue requestQueue, final Context context, int method, final Handler handler) {
        JsonObjectRequest jsonObjReq = null;

//        method == Request.Method.POST
                jsonObjReq = new JsonObjectRequest(method , url, jsonObject,new Response.Listener() {

                            @Override
                            public void onResponse(Object response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(String.valueOf(response));
                                    if(jsonResponse.getInt("response_code") >=504 ){
                                        LogOut (context);
//                                        Intent intent = new Intent(context,LogInActivity.class);

                                    }else if(jsonResponse.getInt("response_code")==8){
                                          UserServices.LogOut (context);
//                                          Intent intent = new Intent(context,LogInActivity.class);
//                                          context.startActivity(intent);
                                    }
                                    else if (jsonResponse.getInt("response_code")==6 ||jsonResponse.getInt("response_code")==5){

                                        if(jsonResponse.has("data")){
                                            try {
                                                tasks = JsonAssembler.getTasksArray( jsonResponse.getJSONArray("data"));
                                               UserServices.sendTasksBack(handler,tasks);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                       }

                                    }else if (jsonResponse.getInt("response_code")==3){
                                        setDepatments(jsonResponse.getJSONArray("global_roles"));
                                        Intent intent = new Intent(context,MainActivity.class);
                                        context.startActivity(intent);
                                        insertIntoUser(String.valueOf(jsonResponse));
                                    }else if (jsonResponse.getInt("response_code")==11){
                                        setUsers(jsonResponse.getJSONArray("users"));
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
                        headers.put("private_key",User.getPrivateKey());
                        headers.put("user_id", User.getUserId());




                        return headers;
                    }
                };


        requestQueue.add(jsonObjReq);


    }

    private static void setUsers(JSONArray departments) {

        for (int i = 0 ; i < departments.length(); i++){
            try {
                String name=((JSONObject)departments.get(i)).keys().next();
                JSONArray arrayOfUsers=((JSONObject)departments.get(i)).getJSONArray(name);
                ArrayList<Users>users=new ArrayList<>();
                LogInActivity.MYDEPARTMENTS.put(LogInActivity.DEPARTMANTS.get(Integer.parseInt(name)),Integer.parseInt(name));
                for (int j = 0 ; j < arrayOfUsers.length(); j++){
                    users.add(new Users(((JSONObject)arrayOfUsers.get(j))));
                }
                MainActivity.USERS.put(Integer.parseInt(name),users);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private static void setDepatments(JSONArray globalRoles) {

        //{ "identifier" : 1, "description" : "cashier" }
        for(int i = 0 ; i < globalRoles.length(); i++){
            try {
                LogInActivity.DEPARTMANTS.put(((JSONObject )globalRoles.get(i)).getInt("identifier"),
                                                ((JSONObject )globalRoles.get(i)).getString("description"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        
    }

    private static void sendTasksBack(Handler mHandler,ArrayList<Task> tasks){
        Message msg = mHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TASKS,tasks);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    public static void sendTask(JSONObject task, RequestQueue requestQueue, Context context){
        sendData(AppConfig.ADD_NEW_TASK,task,requestQueue,context,Constants.METHOD_POST, null);
    }

    public  static ArrayList getTasksArray(final String url, final RequestQueue requestQueue, final Context context, int method,
                                           Handler handler){
        JSONObject object = new JSONObject();
        sendData(url,object,requestQueue,context,0,handler);
        return tasks;

    }
    public static void insertIntoUser(String str){

        if (str != null){
            try {
                JSONObject j = new JSONObject(str);

                User.setUserName(j.getString("user_name"));
                User.setPrivateKey(j.getString("private_key"));
                User.setUserId(j.getInt("user_id"));
                User.setRole(j.getInt("role"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            User.setUserName(null);
            User.setPrivateKey(null);
            User.setUserId(0);
            User.setRole(0);
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

//        saveFile(FileConfig.USER_FILE,"",context);
        File file = new File(context.getFilesDir(),FileConfig.USER_FILE);
        if(file.exists()){
            context.deleteFile(FileConfig.USER_FILE);
            Toast.makeText(context,"File Delete Success",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,"File not found",Toast.LENGTH_LONG).show();
        }
        insertIntoUser(null);
        Intent intent = new Intent(context.getApplicationContext(),LogInActivity.class);
        context.startActivity(intent);



    }
}
