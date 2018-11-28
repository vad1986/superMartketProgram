package com.arkadiy.enter.smp3.dataObjects;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.services.UserServices;

import org.json.JSONException;
import org.json.JSONObject;

public class Task {

private int forUserId;
private String dateStart;
private String dateEnd;
private String nameTask;
private String description;
private Context context;
private JSONObject taskJson;
    private RequestQueue requestQueue;

    public Task(int forUserId, String dateStart, String dateEnd, String nameTask, String description, Context context) {
        this.forUserId = forUserId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.nameTask = nameTask;
        this.description = description;
        this.context = context;
        requestQueue = Volley.newRequestQueue(this.context);
        try {
            this.taskJson.put("user_id",this.forUserId);
            this.taskJson.put("date_start",this.dateStart);
            this.taskJson.put("date_end",this.dateEnd);
            this.taskJson.put("name",this.nameTask);
            this.taskJson.put("description",this.description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = AppConfig.MAIN_SERVER_IP+AppConfig.MAIN_SERVER_PORT+AppConfig.ADD_NEW_TASK;
        UserServices.sendData(url,taskJson,requestQueue,context);
//        creatNewTask();
    }
    //    user_id | date_start (string) | date_end (String) | name (String) | description (String)

    public void creatNewTask(){

    }
    public int getForUserId() {
        return forUserId;
    }

    public void setForUserId(int forUserId) {
        this.forUserId = forUserId;
    }

    public JSONObject getTaskJson() {
        return taskJson;
    }

    public void setTaskJson(JSONObject taskJson) {
        this.taskJson = taskJson;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;

    }



}
