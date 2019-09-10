package com.arkadiy.enter.smp3.dataObjects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Department {

    private int id;
    private String name;
    private ArrayList users;
    private String description;
    private Manager manager;
    private ArrayList listTasks;

    public Department(int id,String description ){
        this.id = id;
        this.description = description;
    }

    public Department(JSONObject jsonObject){
    //fill users and tasks here by using innner methods
        try {
            setName(jsonObject.getJSONObject("data").getString("name"));
            setId(jsonObject.getJSONObject("data").getInt("id"));
            if (jsonObject.get("users").getClass().equals(JSONArray.class)) {
                fillUsers(jsonObject.getJSONArray("users"));
            }
            if (jsonObject.get("tasks").getClass().equals(JSONArray.class) ) {
                fillTasks(jsonObject.getJSONArray("tasks"));
            }
            if (jsonObject.get("manager").getClass().equals(JSONObject.class)){
                Manager man = new Manager(jsonObject.getJSONObject("manager"));
                setManager(man);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void fillTasks(JSONArray jsonTasks)throws Exception {
        this.listTasks=new ArrayList<Task>();
        if(jsonTasks != null){
            for(int i = 0 ; i < jsonTasks.length(); i++){

                Task task=new Task((JSONObject) jsonTasks.get(i));
                //
                this.listTasks.add(task);
            }
        }

    }



    public void fillUsers(JSONArray usersArray)throws Exception{
        this.users=new ArrayList<User>();
        if(usersArray != null){
            for(int i = 0 ; i < usersArray.length(); i++){

                User user=new User((JSONObject) usersArray.get(i));
                //
                this.users.add(user);
            }
        }

        //run through array and add to users
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public ArrayList<User> getUsers() {
       return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Manager getManager() {
//        return manager;
//    }
//
    public void setManager(Manager manager) {
        this.manager = manager;
    }
//
//    public List<Task> getTasks() {
//        return tasks;
//    }
//
   public void setTasks(ArrayList<Task> tasks) {
        this.listTasks = tasks;
   }
}
