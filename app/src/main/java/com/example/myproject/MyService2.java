package com.example.myproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class MyService2 extends Service {
    MediaPlayer player;

    public MyService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();

//        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        NotificationChannel channel = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            channel = new NotificationChannel("abc","ABC", NotificationManager.IMPORTANCE_HIGH);
//            manager.createNotificationChannel(channel);
//        }
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"abc");
//        builder.setContentText("Service in foreground");
//        builder.setContentTitle("My Service");
//        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
//        Notification notification = builder.build();
//        startForeground(1,notification);

//        return START_REDELIVER_INTENT;

        Notification notification = intent.getExtras().getParcelable("notification_icon");
        startForeground(1, notification);
        return START_STICKY;

    }

    public void startPlayer(){
        player.start();
    }

    public void stopPlayer(){
        player.pause();
    }

    @Override
    public void onCreate() {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
//        player.stop();
//        player.release();
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        public MyService2 getService(){
            return MyService2.this;
        }
    }



//    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";
//
//    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
//
//    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";
//
//    public static final String ACTION_PAUSE = "ACTION_PAUSE";
//
//    public static final String ACTION_PLAY = "ACTION_PLAY";
//
//    public MyService2() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.d(TAG_FOREGROUND_SERVICE, "My foreground service onCreate().");
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if(intent != null)
//        {
//            String action = intent.getAction();
//
//            switch (action)
//            {
//                case ACTION_START_FOREGROUND_SERVICE:
//                    startForegroundService();
//                    Toast.makeText(getApplicationContext(), "Foreground service is started.", Toast.LENGTH_LONG).show();
//                    break;
//                case ACTION_STOP_FOREGROUND_SERVICE:
//                    stopForegroundService();
//                    Toast.makeText(getApplicationContext(), "Foreground service is stopped.", Toast.LENGTH_LONG).show();
//                    break;
//                case ACTION_PLAY:
//                    Toast.makeText(getApplicationContext(), "You click Play button.", Toast.LENGTH_LONG).show();
//                    break;
//                case ACTION_PAUSE:
//                    Toast.makeText(getApplicationContext(), "You click Pause button.", Toast.LENGTH_LONG).show();
//                    break;
//            }
//        }
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    /* Used to build and start foreground service. */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void startForegroundService()
//    {
//        Log.d(TAG_FOREGROUND_SERVICE, "Start foreground service.");
//
////        // Create notification default intent.
////        Intent intent = new Intent();
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
////
////        // Create notification builder.
////        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
////
////        // Make notification show big text.
////        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
////        bigTextStyle.setBigContentTitle("Music player implemented by foreground service.");
////        bigTextStyle.bigText("Android foreground service is a android service which can run in foreground always, it can be controlled by user via notification.");
////        // Set big text style.
////        builder.setStyle(bigTextStyle);
////
////        builder.setWhen(System.currentTimeMillis());
////        builder.setSmallIcon(R.mipmap.ic_launcher);
////        Bitmap largeIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.camera);
////        builder.setLargeIcon(largeIconBitmap);
////        // Make the notification max priority.
////        builder.setPriority(Notification.PRIORITY_MAX);
////        // Make head-up notification.
////        builder.setFullScreenIntent(pendingIntent, true);
////
////        // Add Play button intent in notification.
////        Intent playIntent = new Intent(this, MyService2.class);
////        playIntent.setAction(ACTION_PLAY);
////        PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0);
////        NotificationCompat.Action playAction = new NotificationCompat.Action(android.R.drawable.ic_media_play, "Play", pendingPlayIntent);
////        builder.addAction(playAction);
////
////        // Add Pause button intent in notification.
////        Intent pauseIntent = new Intent(this, MyService2.class);
////        pauseIntent.setAction(ACTION_PAUSE);
////        PendingIntent pendingPrevIntent = PendingIntent.getService(this, 0, pauseIntent, 0);
////        NotificationCompat.Action prevAction = new NotificationCompat.Action(android.R.drawable.ic_media_pause, "Pause", pendingPrevIntent);
////        builder.addAction(prevAction);
////
////        // Build the notification.
////        Notification notification = builder.build();
////
////        // Start foreground service.
////        startForeground(1, notification);
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//
//        Notification notification =
//                new Notification.Builder(this, "TEST")
//                        .setContentTitle("HELLO")
//                        .setContentText("HELLO")
//                        .setSmallIcon(R.drawable.camera)
//                        .setContentIntent(pendingIntent)
//                        .build();
//
//        startForeground(1, notification);
//
//
//
//
//    }
//
//    private void stopForegroundService()
//    {
//        Log.d(TAG_FOREGROUND_SERVICE, "Stop foreground service.");
//
//        // Stop foreground service and remove the notification.
//        stopForeground(true);
//
//        // Stop the foreground service.
//        stopSelf();
//    }



}



















