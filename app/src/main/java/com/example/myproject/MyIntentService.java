//package com.example.myproject;
//
//import android.app.IntentService;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.Intent;
//import android.content.Context;
//import android.media.MediaPlayer;
//import android.os.Build;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//import static android.support.v4.app.NotificationCompat.PRIORITY_MIN;
//
//public class MyIntentService extends Service {
//
//    MediaPlayer mediaPlayer;
//
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
////        mediaPlayer = MediaPlayer.create(this,R.raw.sound);
////        mediaPlayer.setLooping(true);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
//        Notification notification = notificationBuilder.setOngoing(true)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setPriority(PRIORITY_MIN)
//                .setCategory(NotificationCompat.CATEGORY_SERVICE)
//                .build();
//        startForeground(101, notification);
//
//        Log.i("SONG","onCreate");
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private String createNotificationChannel(NotificationManager notificationManager){
//        String channelId = "my_service_channelid";
//        String channelName = "My Foreground Service";
//        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
//        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
//        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//        notificationManager.createNotificationChannel(channel);
//        return channelId;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
////        mediaPlayer.start();
//        Log.i("SONG","onStart");
//        super.onStartCommand(intent, flags, startId);
//        return START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        Log.i("SONG","onDestroy");
////        Intent broadcastIntent = new Intent(MyIntentService.this, MyReceiver.class);
////        sendBroadcast(broadcastIntent);
////        mediaPlayer.stop();
////        Intent intent = new Intent(getApplication().getBaseContext(),MyIntentService.class);
////        getBaseContext().startService(intent);
//
//    }
//}
