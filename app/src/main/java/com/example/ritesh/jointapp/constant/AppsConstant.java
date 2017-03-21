package com.example.ritesh.jointapp.constant;

import android.content.SharedPreferences;


public class AppsConstant {
  //  public static SharedPreferences mPrefs = null;

    public static final String islogin = "islogin"; // for saving the login status
    public static final int MODE = 0;   //sharedpreference mode set to private
    public static SharedPreferences.Editor editorjoint;    // editor
    public static SharedPreferences jointpreference;       // sharedpreference variable
    public static String str_login_test;     // for checking the value of islogin in splash screen
    public static String answer;     // for checkint the internet type WIFI/MOBILE
    public static final String MyPREFERENCES = "mypref";

    public static final String coverimage   = "http:\\/\\/technorizen.com\\/WORKSPACE5\\/JOINT\\/uploads\\/users\\/COVER_";
    public static final String profileimage = "http:\\/\\/technorizen.com\\/WORKSPACE5\\/JOINT\\/uploads\\/users\\/USER_";
}