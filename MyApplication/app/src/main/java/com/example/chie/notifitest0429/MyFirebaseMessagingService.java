package com.example.chie.notifitest0429;

import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.example.chie.notifitest0429.MainActivity.flag;

/*
 * Created by chie on 2017/04/29.
 */

//Firebaseからの通知の受け取りを担う部分

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMessService";
    private MainActivity main;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //フォアグラウンド時に通知が送信された場合flagを１にする
        Log.d(TAG, "onMessageReceived");
        flag = 1;

        // TODO
        // onMessageReceived()によりflagが１になった時、
        // activity_mainのflag_viewの表示を変化させる

        // TODO
        // バックグラウンド時に通知を受け取っても、flagを１にする


    }


}
