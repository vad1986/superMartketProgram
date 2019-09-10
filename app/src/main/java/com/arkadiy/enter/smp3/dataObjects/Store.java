package com.arkadiy.enter.smp3.dataObjects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Store {

    public static  List<Department> departments;
    static private Map<Integer,Alert>alerts;
    //static public   HashMap<Integer,Department> GLOBAL_DEPARTMANTS;
    static private Map<Integer,Report>reports;


    static public  void fillStoreData(JSONObject dataFromServer) {
        try {
            departments = new ArrayList<Department>();
            fillDepartments(dataFromServer.getJSONArray("departments"));
        //{ "identifier" : 1, "description" : "cashier" }
        } catch (Exception e) {
            e.printStackTrace();
        }
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




    public static List<Department> getDepartments() {
        return departments;
    }

    public static void setDepartments(List<Department> departments) {
        Store.departments = departments;
    }

    public Map<Integer, Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(Map<Integer, Alert> alerts) {
        this.alerts = alerts;
    }

    public Map<Integer, Report> getReports() {
        return reports;
    }

    public void setReports(Map<Integer, Report> reports) {
        this.reports = reports;
    }
}

