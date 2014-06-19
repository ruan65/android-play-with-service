package a.intentservicetest;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;


public class CoolService extends IntentService {

    private static volatile int serviceCounter;
    private final int indx = ++serviceCounter;

    public CoolService() {
        super("CoolService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Main.log("inside CoolService onCreate() thread: "
                + Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Main.log("inside CoolService onStartCommand() messageToService: "
                + intent.getStringExtra("messageToService"));
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Main.log("inside CoolService #" + indx + " onHandleIntent() start, thread: "
                + Thread.currentThread().getName());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String finMessage = "Task number " + intent.getIntExtra("task", 666) + " has finished.";

        Main.log(finMessage);  // 666 - default value (((

//        Message m = Main.mainHandler.obtainMessage();
//        m.sendToTarget();

        Message msg = Message.obtain();
        Messenger m = intent.getParcelableExtra("messenger");
        try {
            m.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        Main.log("inside CoolService OnBind()");
        return super.onBind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Main.log("CoolService #" + indx + " was destroyed (((");
    }
}
