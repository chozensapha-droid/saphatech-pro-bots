package com.saphatech.probot;

import android.app.*;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class TradingEngineService extends Service {
    public static final String START="START_ENGINE";
    private volatile boolean running;
    private Thread worker;

    @Override public void onCreate() {
        super.onCreate();
        String channel="saphatech_engine";
        if (android.os.Build.VERSION.SDK_INT>=26) {
            NotificationChannel nc=new NotificationChannel(channel,"SAPHATECH Trading Engine",NotificationManager.IMPORTANCE_LOW);
            getSystemService(NotificationManager.class).createNotificationChannel(nc);
        }
        Notification.Builder b=android.os.Build.VERSION.SDK_INT>=26?new Notification.Builder(this,channel):new Notification.Builder(this);
        b.setContentTitle("SAPHATECH PRO BOT").setContentText("Phone-only trading engine running").setSmallIcon(android.R.drawable.stat_notify_sync).setOngoing(true);
        startForeground(7001,b.build());
    }
    @Override public int onStartCommand(Intent intent,int flags,int id) {
        if(!running){ running=true; worker=new Thread(()->{ while(running){ try { Thread.sleep(1000); } catch(InterruptedException ignored){} } }); worker.start(); }
        return START_STICKY;
    }
    @Override public void onDestroy(){ running=false; if(worker!=null) worker.interrupt(); super.onDestroy(); }
    @Nullable @Override public IBinder onBind(Intent intent){ return null; }
}
