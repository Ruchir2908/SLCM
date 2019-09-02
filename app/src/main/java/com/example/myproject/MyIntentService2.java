package com.example.myproject;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

public class MyIntentService2 extends IntentService {

    public MyIntentService2() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        for(int i = 0;i<100;i++){
            Log.d("MyIntentService",i+"");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
