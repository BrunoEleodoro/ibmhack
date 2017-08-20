package com.example.victorgabriel.peoplefinder.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by bruno on 03/01/17.
 */

public class ChecarSeLigou extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {

            Intent i_msg = new Intent("VERIFICAR_PESSOAS");
            PendingIntent p_msg = PendingIntent.getBroadcast(context, 0, i_msg, 0);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.add(Calendar.SECOND, 0);
            AlarmManager alarme_msg = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarme_msg.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1800000, p_msg);
            //alarme_msg.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 60000, p_msg);


        }
    }

}
//https://www.googleapis.com/youtube/v3/channels?part=contentDetails&forUsername=StickGamerBrasil&key=AIzaSyBnh7wTdf37FKRzS4c1OVEXpf8wUcEPSu4
//https://www.googleapis.com/youtube/v3/channels?id=StickGamerBrasilkey=AIzaSyBnh7wTdf37FKRzS4c1OVEXpf8wUcEPSu4&part=contentDetails