package com.coeuz.pyscustomer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;


import org.json.JSONObject;

public class NotificationApplication extends Application {


    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public NotificationApplication() {

    }

    public NotificationApplication(Context context) {
        NotificationApplication.context = context;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).setNotificationOpenedHandler(new ExampleOpenHelper())
                .autoPromptLocation(true).init();


    }



    private class ExampleOpenHelper implements OneSignal.NotificationOpenedHandler {

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {

          //  OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject dat = result.notification.payload.additionalData;
            String customKey;

            /*Intent intent = new Intent(context2,User_Detail.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            context2.startActivity(intent);
*/

            if (dat != null) {
                customKey = dat.optString("data", "data");

            }


            JSONObject data = result.notification.payload.additionalData;


            String headings = data.optString("headings", "");
            String contents = data.optString("contents", "");

        }
    }}




