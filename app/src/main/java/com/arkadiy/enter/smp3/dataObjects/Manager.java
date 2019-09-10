package com.arkadiy.enter.smp3.dataObjects;

import org.json.JSONObject;

class Manager extends User {


    public Manager(JSONObject jsonObject) {
        super(jsonObject);
    }

    public void orderReport(int reportId, String fromDate, String toDate){
        //uses DataService to send json object to the server

    }
    public void editTask(Task task){
        // edit task and send to server

    }
    public void editShift(Shift shift){

    }

}
