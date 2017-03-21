package com.example.ritesh.jointapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.ritesh.jointapp.constant.AppsConstant;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by ritesh on 18/6/16.
 */
public class SplashActivity extends Activity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //SharedPreferences shh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Utils.isConnected(getApplicationContext())) {

             /*internet connectivity check (Start)*/
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
               /* if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)*/
                   /* AppsConstant.answer = "You are connected to a WiFi Network";*/
            /*        TastyToast.makeText(getApplicationContext(), "You are connected to a WiFi Network", TastyToast.LENGTH_LONG,
                            TastyToast.SUCCESS);*/
               /* if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)*/
                   /* AppsConstant.answer = "You are connected to a Mobile Network";*/
               /* TastyToast.makeText(getApplicationContext(), "You are connected to a Mobile Network", TastyToast.LENGTH_LONG,
                        TastyToast.SUCCESS);*/
            } else
                /*AppsConstant.answer = "No internet Connectivity!!!";*/
          /*  Toast.makeText(getApplicationContext(), AppsConstant.answer, Toast.LENGTH_SHORT).show();*/

            // here initializing the shared preference
            AppsConstant.jointpreference = getSharedPreferences(AppsConstant.MyPREFERENCES, Context.MODE_PRIVATE);
            AppsConstant.jointpreference = getSharedPreferences("myprefe", 0);
            AppsConstant.editorjoint = AppsConstant.jointpreference.edit();
            AppsConstant.editorjoint.commit();

            // check here if user is login or not
            AppsConstant.jointpreference = getSharedPreferences(AppsConstant.MyPREFERENCES, Context.MODE_PRIVATE);
            AppsConstant.str_login_test = AppsConstant.jointpreference.getString("loginTest", null);

            if (getIntent().getBooleanExtra("EXIT", false)) {
                finish();
                return;
            }
        /*splash activity (Start)*/
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                /*
                 * if user login test is true on oncreate then redirect the user
				 * to DashboardActivity page
				 */

                    if (AppsConstant.str_login_test != null && !AppsConstant.str_login_test.toString().trim().equals("")) {
                        Intent send = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(send);
                        Log.e("********* GET LOGIN DETAIL *********", "Yes Success GOTO MAINPAGE");
                        Log.e("********* GET LOGIN DETAIL *********", "Yes Success GOTO MAINPAGE");
                        Log.e("********* GET LOGIN DETAIL *********", "Yes Success GOTO MAINPAGE");

                        finish();
                    }
                /*
                 * if user login test is false on oncreate then redirect the
				 * user to Signin Activity page
				 */
                    else {

                        Intent send = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(send);
                        Log.e("********* Get Login Detail *********", "NOT Success GOTO LOGINPAGE");
                        Log.e("********* Get Login Detail *********", "NOT Success GOTO LOGINPAGE");
                        Log.e("********* Get Login Detail *********", "NOT Success GOTO LOGINPAGE");

                    }
                }

            }, 3000);

        } else {
            TastyToast.makeText(getApplicationContext(), "No internet Connectivity!!! ", TastyToast.LENGTH_LONG,
                    TastyToast.ERROR);
            Intent wifi = new Intent(SplashActivity.this, ActivityWifiCheck.class);
            startActivity(wifi);
        }
    }
}