package com.arkadiy.enter.smp3.config;

public class AppConfig {

    public static String MAIN_SERVER_IP="http://15.188.69.193";
    //public static String MAIN_SERVER_IP="http://192.168.43.177";
    public static String SOCKET_SERVER_URL="";
    public static String MAIN_SERVER_PORT=":8080";
    public static String LOGIN=MAIN_SERVER_IP+MAIN_SERVER_PORT+"/login";
    public static String ADD_NEW_USER=MAIN_SERVER_IP+MAIN_SERVER_PORT+"/manager/new_user";
    public static String ADD_NEW_TASK=MAIN_SERVER_IP+MAIN_SERVER_PORT+"/user/new_task";
    public static String PUNCH_CLOCK=MAIN_SERVER_IP+MAIN_SERVER_PORT+"/user/punchClock";
    public static String LOGOUT_SERVER=MAIN_SERVER_IP+MAIN_SERVER_PORT+"/user/logout";
    public static String GET_USER_TASKS=MAIN_SERVER_IP+MAIN_SERVER_PORT+"/user/get_user_tasks/0";
    public static String CLOSE_TASK=MAIN_SERVER_IP+MAIN_SERVER_PORT+"/user/close-task";
    public static String GET_MY_USERS=MAIN_SERVER_IP+MAIN_SERVER_PORT+"/user/get_my_users";
    public static String CHECK_PRIVATE_KEY = MAIN_SERVER_IP+MAIN_SERVER_PORT+"/checkPrivate/";
    public static String CREATE_SENTENCE = MAIN_SERVER_IP+MAIN_SERVER_PORT+"/createSentence";
    public static String ONLINE_USERS = MAIN_SERVER_IP+MAIN_SERVER_PORT+"/onlineUsers";
    public static String SEND_ALERT = MAIN_SERVER_IP+MAIN_SERVER_PORT+"/message";
    public static String ADD_SHOP_SETTING_LOCATION = MAIN_SERVER_IP+MAIN_SERVER_PORT+"/manager/addShopLocation";

}
