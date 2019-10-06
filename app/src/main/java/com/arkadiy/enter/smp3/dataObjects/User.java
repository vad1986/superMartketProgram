package com.arkadiy.enter.smp3.dataObjects;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.activities.App;
import com.arkadiy.enter.smp3.activities.LogInActivity;
import com.arkadiy.enter.smp3.activities.MainActivity;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.config.ResponseCode;
import com.arkadiy.enter.smp3.services.DataServices;
import com.arkadiy.enter.smp3.services.SocketServices;
import com.arkadiy.enter.smp3.utils.Constants;
import com.arkadiy.enter.smp3.utils.ConstantsJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.arkadiy.enter.smp3.activities.App.CHANNEL_1_ID;
import static com.arkadiy.enter.smp3.activities.App.getContext;

public class User {

    private static IHandler taskActivityHandler;
    private static NotificationManagerCompat notificationManager;
    private int userId;
    private String userName;
    private String userFirstName;
    private int role;
    private String userLastName;
    private String privateKey;
    private String phone;
    private String city;
    private String email;
    private int status;
    private int groupId;
    private int managerId;
    public static ArrayList<Task>myTasks;
    private List<Alert>recivedAlerts;
    private List<Alert>sentAlerts;
    private List<Shift>lastShifts;
    private static RequestQueue requestQueue;
    private static Handler handler;
    private static int myUserId;
    private static String myPrivateKey;
    private static String myUserName;
    private static String myImail;
    private static int myRole;
    private static int departmentId;
    private static int numberNewTack; //TODO sum new task in this var

    private static Map<Integer,String>userOnline;
    public User(JSONObject jsonObject) {

        try {

//                    "city" : "yehud",
//                    "email" : "ahshjbsh@mail.com",
//                    "status" : 2,
//                    "userID" : 136,
//                    "password" : "araara123",
//                    "userName" : "Yonatan",
//                    "userRole" : 2,
//                    "managerId" : 12,
//                    "telephone" : "0546567712",
//                    "userLastName" : "Shvarmin",
//                    "userFirstName" : "Yonatan"

            this.city = jsonObject.getString("city");
            this.email = jsonObject.getString("email");
            this.status = jsonObject.getInt("status");
            this.userId = jsonObject.getInt("userID");
            this.userName = jsonObject.getString("userName");
            this.role = jsonObject.getInt("userRole");
            this.managerId = jsonObject.getInt("managerId");
            this.phone = jsonObject.getString("telephone");
            this.userFirstName = jsonObject.getString("userFirstName");
            this.userLastName = jsonObject.getString("userLastName");
            this.myImail = jsonObject.getString("mail");
            if(jsonObject.getInt(ConstantsJson.DEPARTMENT_ID) != -1){
                //TODO: check if simple user get value -1 if yes delete operator if
                this.departmentId = jsonObject.getInt(ConstantsJson.DEPARTMENT_ID);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }




    public static void addNewTask(JSONObject taskJS){
        Task newTask = new Task(taskJS);
        if (myTasks == null){
            myTasks = new ArrayList<>();
        }
        myTasks.add(newTask);
        Message msg = new Message();
        Bundle bundle=new Bundle();
        bundle.putString("task",taskJS.toString());
        msg.setData(bundle);
       taskActivityHandler.sendMessage(msg);

    }

    public static void setTaskHandler(IHandler handler) {
        User.taskActivityHandler=handler;
    }


    public void closeTask(int taskPosition){
        if(myTasks!=null){
            JSONObject json=myTasks.get(taskPosition).getTask();
            DataServices.sendData(AppConfig.CLOSE_TASK,json,requestQueue,App.getContext(), Constants.METHOD_POST, x->{

                return true;
            });

//            DataServices.sendData(AppConfig.CLOSE_TASK,json,requestQueue,User.context, Constants.METHOD_POST,
//                    handler->{
//
//            });
        }

    }


    public static void logIn(JSONObject json){
        requestQueue= Volley.newRequestQueue(App.getContext());
        DataServices.sendData(AppConfig.LOGIN,json,requestQueue,
                getContext(),Constants.METHOD_POST,handLogIn->{
            try {

                    Bundle bundle = handLogIn.getData();
                    JSONObject jsonRespons;
                    if(bundle.getString("json")!=null) {

                        jsonRespons =new JSONObject(bundle.getString("json"));
                        if (jsonRespons.getInt(ConstantsJson.RESPONSE_CODE) < ResponseCode.ERROR)
                        {
                            Intent intent = new Intent(App.getContext(), MainActivity.class);
                            App.getContext().startActivity(intent);


                        }else {
                            //TODO show message to user
                            return false;
                        }
                        SocketServices messageSender=new SocketServices();
                        messageSender.execute();
                        Store.fillStoreData(jsonRespons);
                        saveUser(jsonRespons);



                        }
                    }catch (JSONException e) {
                     e.printStackTrace();
            }

            return true;
                });

    }


    public static void sendAlert(Context context,JSONObject alert,RequestQueue requestQueue){
        // Sends  JSObject to data serveses and insert into sent alerts
        DataServices.sendData(AppConfig.SEND_ALERT,alert,requestQueue,context,Constants.METHOD_POST,null);

    }

    public static void saveUser (JSONObject json){
        try {

           User.setMyUserId(json.getInt("user_id"));
           User.setMyRole(json.getInt("role"));
            User.setMyPrivateKey(json.getString("private_key"));
            User.setMyUserName(json.getString("user_name"));

            //            myAcount.put("user_id",json.getInt("user_id"));
            //            myAcount.put("role",json.getInt("role"));
            //            myAcount.put("private_key",json.getInt("private_key"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //json.getString("user_id"),j.getString("user_name")," ",j.getString("private_key"),j.getString("role")

    }
    public static void sendNewTask (Context context, JSONObject jsonTask,RequestQueue requestQueue,IHandler sendTaskHandler){

        DataServices.sendData(AppConfig.ADD_NEW_TASK,jsonTask,requestQueue,context,Constants.METHOD_POST,sendTask->{
            JSONObject object;
            Message msg = new Message();
            Bundle bundle = sendTask.getData();
            if (bundle.getString("json")!=null){
                try {
                    object = new JSONObject(bundle.getString("json"));
                    if (object.getInt("response_code") < ResponseCode.ERROR){
                        msg.setData(bundle);
                        sendTaskHandler.sendMessage(msg);
                        return true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return false;
        });

    }
    public static void getTasksFromServer(Context context,IHandler i_handler){

        DataServices.sendData(AppConfig.GET_USER_TASKS,null,requestQueue,context,Constants.METHOD_GET,handTask->{
            try {

                JSONObject json;
                Bundle bundle = handTask.getData();
            if(bundle.getString("json")!=null) {
                json =new JSONObject(bundle.getString("json"));
                if(json.getJSONArray("data")!=null){
                    JSONArray jsonArray = json.getJSONArray("data");
                    setTasks(jsonArray);
                }


                i_handler.sendMessage(new Message());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        });
    }


    public static void punchClock(Context contextClock,JSONObject jsonClock,String url,RequestQueue requestQueue , IHandler i_handler){

        DataServices.sendData(AppConfig.PUNCH_CLOCK, jsonClock, requestQueue, contextClock,Constants.METHOD_POST, handClock->{
                    JSONObject json;
                     Message msg = new Message();
                    Bundle bundle = handClock.getData();
            try {
                json = new JSONObject(bundle.getString("json"));

                    if(json.getInt(Constants.RESPONSE_CODE) == ResponseCode.PUNCH_CLOCK){

                        bundle.putInt(ConstantsJson.RESPONSE_CODE,json.getInt(Constants.RESPONSE_CODE));
                        msg.setData(bundle);
                        i_handler.sendMessage(msg);
                        return true;
                    }else if(json.getInt(Constants.RESPONSE_CODE) == ResponseCode.NOT_IN_RANGE){
                        Toast.makeText(contextClock,json.getString(ConstantsJson.MESSAGE),Toast.LENGTH_LONG).show();
                        bundle.putInt(ConstantsJson.RESPONSE_CODE,json.getInt(Constants.RESPONSE_CODE));
                        msg.setData(bundle);
                        i_handler.sendMessage(msg);
                    }else return false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
                });


    }


    public static void setTasks(JSONArray tasksArray){
        myTasks = new ArrayList<Task>();
        for (int i = 0 ;i < tasksArray.length() ; i++){

            try {
                Task task = new Task((JSONObject) tasksArray.get(i));
                myTasks.add(task);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    public  JSONObject getJson() {
        try {
            return  new JSONObject()
                    .put("user_name",userName)
                    .put("private_key",privateKey)
                    .put("user_id", getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public static void getAlertFromSocket(Alert alert){
        String fromUser = null;
        for (int i = 0 ; i<Store.sizeUserOnline() ; i++){

            try {
                if(alert.getUserFrom() == Store.getUsersOnline().getJSONObject(i).getInt("user_id")){
                    fromUser = Store.getUsersOnline().getJSONObject(i).getString("user_name");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        notificationManager = NotificationManagerCompat.from(App.getContext());
        Notification notification = new NotificationCompat.Builder(App.getContext(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle("Got alert from user: " + fromUser +" Title: " +alert.getName())
                .setContentText(alert.getDescription())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManager.notify(1,notification);

    }
    public static int getDepartmentId() {
        return departmentId;
    }

    public static void setDepartmentId(int departmentId) {
        User.departmentId = departmentId;
    }

    public static int getMyUserId() {
        return myUserId;
    }

    public static void setMyUserId(int myUserId) {
        User.myUserId = myUserId;
    }

    public static String getMyPrivateKey() {
        return myPrivateKey;
    }

    public static void setMyPrivateKey(String myPrivateKey) {
        User.myPrivateKey = myPrivateKey;
    }

    public static String getMyUserName() {
        return myUserName;
    }

    public static void setMyUserName(String myUserName) {
        User.myUserName = myUserName;
    }

    public static int getMyRole() {
        return myRole;
    }

    public static void setMyRole(int myRole) {
        User.myRole = myRole;
    }

    public int getUserId() {
        return userId;
    }

    public int getRole() {
        return role;
    }

    public List<Alert> getSentAlerts() {
        return sentAlerts;
    }

    public void setSentAlerts(List<Alert> sentAlerts) {
        this.sentAlerts = sentAlerts;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public List<Task> getTasks() {
        return myTasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.myTasks = tasks;
    }

    public List<Alert> getRecivedAlerts() {
        return recivedAlerts;
    }

    public void setRecivedAlerts(List<Alert> recivedAlerts) {
        this.recivedAlerts = recivedAlerts;
    }

    public List<Alert> getSendAlerts() {
        return sentAlerts;
    }

    public void setSendAlerts(List<Alert> sendAlerts) {
        this.sentAlerts = sendAlerts;
    }

    public List<Shift> getLastShifts() {
        return lastShifts;
    }

    public void setLastShifts(List<Shift> lastShifts) {
        this.lastShifts = lastShifts;
    }


    public static int getNumberNewTack() {
        return numberNewTack;
    }

    public static void setNumberNewTack(int numberNewTack) {
        User.numberNewTack = numberNewTack;
    }


    public static void logOut(){

        DataServices.sendData(AppConfig.LOGOUT_SERVER,null,requestQueue, App.getContext(),Constants.METHOD_POST,handLogOut->{

            Intent intent = new Intent(App.getContext(), LogInActivity.class);
            App.getContext().startActivity(intent);
                    return true;
        });

    }

    public  void setUserName(String userName) {
        this.userName = userName;
    }

    public  String getUserName() {
        return userName;
    }

    public  String getPrivateKey() {
        return privateKey;
    }

    public  void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }



}
