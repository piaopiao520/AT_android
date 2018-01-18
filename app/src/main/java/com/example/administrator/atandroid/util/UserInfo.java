package com.example.administrator.atandroid.util;

/**
 * Created by Administrator on 2017/5/17.
 */

public class UserInfo {
    private static String id;
    private static String password;
    private static Boolean IsLogin = false;

    private UserInfo() {

    }

    public static void setId(String id) {
        UserInfo.id = id;
    }

    public static String getId() {
        return id;
    }
    public static void setIsLogin(Boolean IsLogin) {
        UserInfo.IsLogin = IsLogin;
    }

    public static Boolean getIsLogin() {
        return IsLogin;
    }

    public static void setPassword(String password) {
        UserInfo.password = password;
    }

    public static String getPassword() {
        return password;
    }
}
