package com.example.ritesh.jointapp;


import android.util.Log;

import com.applozic.mobicomkit.api.notification.MobiComPushReceiver;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class FcmListenerService extends FirebaseMessagingService {

    private static final String TAG = "ApplozicGcmListener";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.i(TAG,"Message data:"+remoteMessage.getData());
        Log.e("Message data:", "" + remoteMessage.getData());
        Log.e("Message data:", "" + remoteMessage.getData());
        Log.e("Message data:", "" + remoteMessage.getData());

        if(remoteMessage.getData().size()>0){
            if (MobiComPushReceiver.isMobiComPushNotification(remoteMessage.getData())) {
                Log.i(TAG, "Applozic notification processing...");
                Log.e("Applozic notification processing :", "ok");
                Log.e("Applozic notification processing :", "ok");
                Log.e("Applozic notification processing :", "ok");
                MobiComPushReceiver.processMessageAsync(this, remoteMessage.getData());
                return;
            }
        }

    }

}