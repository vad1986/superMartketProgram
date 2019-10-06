package com.arkadiy.enter.smp3.dataObjects;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import com.android.volley.RequestQueue;
import com.arkadiy.enter.smp3.activities.CommuicationActivity;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.services.DataServices;
import com.arkadiy.enter.smp3.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {

    public static  List<Department> departments;
    public static  Map<Integer,String> rollesDate;
    private static List<Alert>alerts;

    public static  Map<String,Integer> GLOBAL_DEPARTMANTS;
    private static Map<Integer,Report>reports;
    private static JSONArray usersOnline;


    public static void fillStoreData(JSONObject dataFromServer) {
        try {
            departments = new ArrayList<Department>();
            AppConfig.SOCKET_SERVER_URL ="ws://"+ dataFromServer.getString("socket");
            setRollDate(dataFromServer.getJSONArray("roles_data"));
            setGlobalDepartmants(dataFromServer.getJSONArray("departments_data"));
            fillDepartments(dataFromServer.getJSONArray("departments"));

            fillAlertList(dataFromServer.getJSONArray("sentences"));
        //{ "identifier" : 1, "description" : "cashier" }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setGlobalDepartmants(JSONArray globalDepartments){

        GLOBAL_DEPARTMANTS = new HashMap< String,Integer>();
        for(int i=0 ;i<globalDepartments.length();i++){
            try {
                GLOBAL_DEPARTMANTS.put(globalDepartments.getJSONObject(i).getString("description"),globalDepartments.getJSONObject(i).getInt("identifier"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    public static void setRollDate(JSONArray rollDate){

        rollesDate = new HashMap<Integer,String>();
        for (int i=0;i<rollDate.length();i++){

            try {
                rollesDate.put(rollDate.getJSONObject(i).getInt("identifier"),rollDate.getJSONObject(i).getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static void fillAlertList(JSONArray alertArray)throws  Exception{

        alerts = new ArrayList<>();

        for (int i = 0 ;i < alertArray.length(); i++){
            Alert alert = new Alert(alertArray.getJSONObject(i).getInt("id"),alertArray.getJSONObject(i).getString("name"),alertArray.getJSONObject(i).getString("description"));
            alerts.add(alert);

        }
        int x = 0;

    }
    public static void setNewAlert(JSONObject alertJson){

        Alert alert = new Alert(alertJson);
        //alerts.add(alert);
        CommuicationActivity.setNewAlertInAdapter(alert);
    }
    public static void checkUsersOnline(Context context, RequestQueue requestQueue, IHandler iHandler){

        DataServices.sendData(AppConfig.ONLINE_USERS,null,requestQueue,context, Constants.METHOD_GET,onlineUsers->{
            try {
                JSONObject json;
                Message msg = new Message();
                Bundle bundle = onlineUsers.getData();
                usersOnline = new JSONArray();
                    json = new JSONObject(bundle.getString("json"));
                    for (int i =0 ; i < json.getJSONArray("online").length(); i++){
                        usersOnline.put(json.getJSONArray("online").get(i));
                    }
                    //usersOnline = new JSONArray().put(json.getJSONArray("online"));
                msg.setData(bundle);
                iHandler.sendMessage(msg);
//                if(iHandler!=null){
//
//                    bundle.putString("json", String.valueOf(json));
//                    msg.setData(bundle);
//                    iHandler.sendMessage(msg);
//
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return true;
        });
    }


    private static void fillDepartments(JSONArray departmentsArray)throws Exception{
        for(int i = 0 ; i < departmentsArray.length(); i++){

            Department department=new Department((JSONObject) departmentsArray.get(i));
            //
            departments.add(department);
        }
    }
    /*

                //int id = ((JSONObject)dataFromServer.get(i)).getInt("identifier");
                //String description = ((JSONObject )dataFromServer.get(i)).getString("description");
               // department = new Department(id,description);
               // departments.add(department); //TODO: contenio to save params from global department
                //department.setId(id);
                //department.setDescription(((JSONObject )globalRoles.get(i)).getString("description"));

                //GLOBAL_DEPARTMANTS.put(department.getId(),department);

//                LogInActivity.dataBaseHelperGlobalRoles.addRoles(((JSONObject )globalRoles.get(i)).getInt("identifier"),//set parameters in to local database
//                        ((JSONObject )globalRoles.get(i)).getString("description"));

     */

    public static Department getDepartmentById(int id){
        int i;
        for ( i = 0 ; i<departments.size(); i++){
            if(id == departments.get(i).getId()){
                break;
            }
        }

        return departments.get(i);
    }

    public static JSONArray getUsersOnline() {
        return usersOnline;
    }
    public static int sizeUserOnline(){
        return usersOnline.length();
    }

    public static void setUsersOnline(JSONArray usersOnline) {
        Store.usersOnline = usersOnline;
    }

    public static List<Department> getDepartments() {
        return departments;
    }

    public static void setDepartments(List<Department> departments) {
        Store.departments = departments;
    }

    public static List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    public Map<Integer, Report> getReports() {
        return reports;
    }

    public void setReports(Map<Integer, Report> reports) {
        this.reports = reports;
    }

    public static void doSubscribe(JSONObject userFromSubscribe){
        if(getUsersOnline() == null){
            usersOnline = new JSONArray();
        }
        usersOnline.put(userFromSubscribe);

    }

    public static void doUnSubscribe(){


    }
}

