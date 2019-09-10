package com.arkadiy.enter.smp3.config;

public class AppConfig {

    public static String MAIN_SERVER_IP="http://ec2-35-181-155-70.eu-west-3.compute.amazonaws.com";
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
}
