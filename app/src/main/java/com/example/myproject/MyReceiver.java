//package com.example.myproject;
//
//import android.annotation.TargetApi;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//public class MyReceiver extends BroadcastReceiver {
//
//    @TargetApi(Build.VERSION_CODES.O)
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        Log.i("SONG","Broadcast");
//        Intent intent1 = new Intent(context,MyIntentService.class);
//            context.startService(intent1);
//    }
//}
