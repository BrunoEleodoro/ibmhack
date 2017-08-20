package com.example.victorgabriel.peoplefinder.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.victorgabriel.peoplefinder.R;

import java.util.Calendar;

public class loading extends AppCompatActivity {

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        pb = (ProgressBar) findViewById(R.id.progressBar2);

        boolean alarmeAtivo = (PendingIntent.getBroadcast(this, 0, new Intent("VERIFICAR_PESSOAS"), PendingIntent.FLAG_NO_CREATE) == null);

        if(alarmeAtivo){

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.add(Calendar.SECOND, 0);
            Intent i = new Intent("VERIFICAR_PESSOAS");
            PendingIntent p = PendingIntent.getBroadcast(this, 0, i, 0);
            AlarmManager alarme = (AlarmManager) this.getSystemService(ALARM_SERVICE);
            alarme.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1800000, p);
            //alarme.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 60000, p);
            Log.i("Script", "Novo alarme pessoas");
        }
        else{
            Log.i("Script", "Alarme pessoas já ativo");
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Intent i = new Intent(loading.this,login.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
