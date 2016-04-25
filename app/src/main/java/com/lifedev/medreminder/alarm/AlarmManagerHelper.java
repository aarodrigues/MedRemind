package com.lifedev.medreminder.alarm;

import android.content.BroadcastReceiver;

import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.view.WindowManager;
import android.widget.Toast;


/**
 * Created by alano on 4/21/16.
 */
public class AlarmManagerHelper extends BroadcastReceiver{

    public static final String ID = "id";
    public static final String TIME_HOUR = "timeHour";
    public static final String TIME_MINUTE = "timeMinute";
    public static final String INTERVAL = "interval";
    public static final String TONE = "alarmTone";

    private static Ringtone ringtoneAlarm;

    @Override
    public void onReceive(Context context, Intent intent) {

        Uri alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtoneAlarm = RingtoneManager.getRingtone(context.getApplicationContext(), alarmTone);
        ringtoneAlarm.play();

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//		long [] pattern = {0,200,500};
//		vibrator.vibrate(pattern,0);
        vibrator.vibrate(2000);

//	    sendSMSAlert();

        long idDrug = intent.getLongExtra("id", -1);
        String nameDrug = intent.getStringExtra("name");

        Intent i = new Intent();
        i.putExtra("idAlarm", idDrug);
        i.putExtra("name", nameDrug);
        i.setClassName("com.app.carehealth", "com.app.carehealth.AlertActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        context.startActivity(i);

    }


    public void sendSMSAlert(){
        try {
            SmsManager smsMensager = SmsManager.getDefault();
            smsMensager.sendTextMessage("7193236617", null, "Mensagem de teste", null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setAlarm(Context context,long idDrug, Calendar beginHour, Calendar interval,Intent intent){

//		cancelAlarms(context);

        long beginHourCurrentTime = System.currentTimeMillis();
        beginHour.set(Calendar.SECOND, 00);

        long millisecondsInterval = timeToMilliseconds(interval.get(Calendar.HOUR_OF_DAY),interval.get(Calendar.MINUTE));

        long difference = beginHourCurrentTime - beginHour.getTimeInMillis();

        if(difference > 0){
            beginHourCurrentTime += difference;
        }

        PendingIntent pIntent = createPendingIntent(context, idDrug, millisecondsInterval,intent);

        setAlarm(context, beginHourCurrentTime,millisecondsInterval, pIntent);

    }

    public static long timeToMilliseconds(int hour,int minute){
        return (hour * 3600000) + (minute * 60000);
    }



    private static void setAlarm(Context context,long beginHour, long interval, PendingIntent pIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//			alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
//		} else {

//		alarmManager.set(AlarmManager.RTC_WAKEUP,interval,pIntent);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, beginHour,interval, pIntent);
//		}

    }

    public static void stopAlarm(){
        // finish the ringtone sound
        ringtoneAlarm.stop();

        // finish the vibration


    }

    public static void cancelAlarm(Context context,long id,Intent intent){

        PendingIntent pIntent = createPendingIntent(context,id,10L,intent);

        // finish the ringtone sound
        ringtoneAlarm.stop();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pIntent);
    }

    public static void cancelAlarms(Context context){

        /*

        MedicineDAO MedicineDAO = new MedicineDAO(context);
        List<Drug> drugList = MedicineDAO.getAllDrugs();

        if (drugList != null) {
            for (Drug drug : drugList) {

                Calendar hourCalendar = Calendar.getInstance();
                hourCalendar.setTime(drug.getBeginHour());
                Calendar intervalCalendar = Calendar.getInstance();
                intervalCalendar.setTime(drug.getInterval());

                long millisecondsInterval = timeToMilliseconds(hourCalendar.get(Calendar.HOUR_OF_DAY),
                        intervalCalendar.get(Calendar.MINUTE));

                Intent intent = new Intent(context, AlarmManagerHelper.class);

                PendingIntent pIntent = createPendingIntent(context,drug.getId(),millisecondsInterval,intent);

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pIntent);
            }
        }

        */
    }

    private static PendingIntent createPendingIntent(Context context, long idDrug,long interval, Intent intent) {

        intent.putExtra(ID, idDrug);
        return PendingIntent.getBroadcast(context, (int) idDrug, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
