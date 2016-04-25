package com.lifedev.medreminder.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by alano on 4/21/16.
 */
public class AlarmService extends Service{

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        AlarmManagerHelper.setAlarm(this,1L);
        Toast.makeText(getApplicationContext(), "Service Running ",Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

}
