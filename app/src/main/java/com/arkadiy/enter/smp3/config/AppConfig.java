package com.arkadiy.enter.smp3.config;

public class AppConfig {
    public static String MAIN_SERVER_IP="http://ec2-35-180-192-138.eu-west-3.compute.amazonaws.com";
    public static String MAIN_SERVER_PORT=":8080";
    public static String LOGIN="/login";
    public static String ADD_NEW_USER="/manager/new_user";
    public static String ADD_NEW_TASK="/manager/new_task";
    public static String PUNCH_CLOCK="http://ec2-35-180-192-138.eu-west-3.compute.amazonaws.com:8080/user/punchClock";
    public static String LOGOUT_SERVER="/user/logout";
}
