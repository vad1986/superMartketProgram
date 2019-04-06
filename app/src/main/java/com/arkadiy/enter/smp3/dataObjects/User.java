package com.arkadiy.enter.smp3.dataObjects;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private static String userName;
    private static String privateKey;
    private static int userId;
    private static int role;

    public User(String userName,String privateKey,int userId) {
        userId=userId;
        privateKey=privateKey;
        userId=userId;
    }

    public User(JSONObject jsonObject) {
       userId=jsonObject.optInt("userID");
        userName=jsonObject.optString("userFirstName")+" "+jsonObject.optString("userLastName");
    }

    public static int getRole() {
        return role;
    }

    public static void setRole(int role) {
        User.role = role;
    }

    public static String getUserName() {
        return userName;
    }

    public String getUserNameNonStatic() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public static void setPrivateKey(String privateKey) {
        User.privateKey = privateKey;
    }

    public static String getUserId() {
        return String.valueOf(userId);
    }

    public static void setUserId(int userId) {
        User.userId = userId;
    }

    public static JSONObject getJson() {
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
}
