package com.example.ritesh.jointapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by ritesh on 31/8/16.
 */
public class ActivityWifiCheck extends AppCompatActivity {

    @BindView(R.id.btn_wifi_ok)
    Button wifiok;
    @BindView(R.id.pulsator)
    PulsatorLayout mPulsator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_check);
        ButterKnife.bind(this);
        mPulsator.start();

        wifiok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*you use this method to close the app (Start) */
//                moveTaskToBack(true);
//                ActivityWifiCheck.this.finish();
                /*you use this method to close the app (Start) */

                /*you use this method to close the app when app is supported api below 11 (Start) */
               /* if (Build.VERSION.SDK_INT >= 11) {
                    recreate();
                } else {
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    overridePendingTransition(0, 0);

                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }*/
                /*you use this method to close the app when app is supported api below 11 (Start) */

                /*you use this method to refresh the current activity with in the app (Start) */
                /*Intent refresh = new Intent(ActivityWifiCheck.this, SplashActivity.class);
                startActivity(refresh);
                finish();
                startActivity(getIntent());*/
                /*you use this method to refresh the current activity with in the app (Start) */

                /*you use this method to refresh the current activity with in the app (Start) */
                /*Intent refresh = new Intent(ActivityWifiCheck.this, SplashActivity.class);
                startActivity(refresh);
                finish();*/
                /*you use this method to refresh the current activity with in the app (End) */


                /*finish();
                startActivity(getIntent());*/

               /* Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);

                startActivity(intent);
                overridePendingTransition(0, 0);*/

            /*    if (Build.VERSION.SDK_INT >= 11) {
                    recreate();
                } else {
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    overridePendingTransition(0, 0);

                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }*/


                Intent intent = new Intent(ActivityWifiCheck.this, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);

                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
    }
}
