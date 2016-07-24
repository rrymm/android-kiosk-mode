package ru.spicedevelopers.igorpavlov.kioskmode;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class KioskActivity extends Activity {

    public static boolean running = false;

    protected void onStart() {
        super.onStart();
        running = true;
    }

    protected void onStop() {
        super.onStop();
        running = false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // https://github.com/apache/cordova-plugin-statusbar/blob/master/src/android/StatusBar.java
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new DBHelper(this.getApplicationContext());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true; // prevent event from being propagated
    }

    // http://www.andreas-schrade.de/2015/02/16/android-tutorial-how-to-create-a-kiosk-mode-in-android/
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus) {
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);

            ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
            am.moveTaskToFront(getTaskId(), ActivityManager.MOVE_TASK_WITH_HOME);

            // sametime required to close opened notification area
            Timer timer = new Timer();
            timer.schedule(new TimerTask(){
                public void run() {
                    Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                    sendBroadcast(closeDialog);
                }
            }, 500); // 0.5 second
        }
    }
}

