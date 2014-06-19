package a.intentservicetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;



public class Main extends Activity {

    private int taskCount;
    private TextView tv, tvResult;
    private Handler mainHandler;
    Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv = (TextView) findViewById(R.id.textView);
        tvResult = (TextView) findViewById(R.id.textView2);
        mainHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                tvResult.setText("Got message");
            }
        };

        messenger = new Messenger(mainHandler);
    }


    public void clickForStartService(View view) {
        Intent i = new Intent(this, CoolService.class);

        i.putExtra("messageToService", "Hello bro! #" + taskCount++);
        i.putExtra("task", taskCount);
        i.putExtra("messenger", messenger);

        startService(i);

        tv.setText("Task #" + taskCount + " has gone to CoolService");
    }


    public static void log(String s) {
        Log.d("ruanLog", s);
    }

    public void clickForBindService(View view) {
    }

    public void clickForIntent(View view) {

        Intent i = new Intent(Intent.ACTION_VIEW);

        startActivity(i);
    }
}
