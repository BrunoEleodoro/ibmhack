package com.example.victorgabriel.peoplefinder.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.example.victorgabriel.peoplefinder.GetPositionTask;
import com.example.victorgabriel.peoplefinder.R;
import com.example.victorgabriel.peoplefinder.activities.loading;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by bruno on 05/01/17.
 */

public class ChecarSeDeu_o_Tempo extends BroadcastReceiver {
    //Database database;
    @Override
    public void onReceive(Context context, Intent intent) {
        new GetPositionTask(context).execute();
    }

}
        /*
        database = new Database(context);
        try
        {
            String novo = intent.getStringExtra("novo");
            if(novo.equals(""))
            {

            }
            else
            {
               database.sql("DELETE FROM avaliacoes_trabalhos WHERE 1");
            }
        }
        catch (Exception e)
        {

        }
        try
        {
            String action = intent.getStringExtra("action");
            if(action.equals(""))
            {

            }
            else
            {
                String avaliacoes = intent.getStringExtra("avaliacoes");
                if(avaliacoes.equals(""))
                {

                }
                else
                {
                    String partes[] = avaliacoes.split("&");
                    int i = 0;
                    while (i < partes.length)
                    {
                        String partes_partes[] = partes[i].split("---");
                        String sentence = "INSERT INTO avaliacoes_trabalhos VALUES(null," +
                                "'"+partes_partes[0]+"'," +
                                "'"+partes_partes[1]+"'," +
                                "'"+partes_partes[2]+"'," +
                                "'"+partes_partes[3]+"'," +
                                "'nao');";
                        if(exists(partes_partes[0]))
                        {

                        }
                        else
                        {
                            database.sql(sentence);
                        }
                        i++;
                    }
                }
            }
        }
        catch(Exception e)
        {
            Log.i("Script","erro receiver ="+e);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+3);
        Date myDate = cal.getTime();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        Log.i("Script","data daqui a 3 dias="+formater.format(myDate));
        Cursor c = database.select("SELECT * FROM avaliacoes_trabalhos WHERE data='"+formater.format(myDate)+"' AND avisei='nao'");
        int i = 0;
        if(c.getCount() > 0)
        {
            while(c.moveToNext())
            {
                String cod = c.getString(c.getColumnIndex("cod"));
                String titulo = c.getString(c.getColumnIndex("nome"));
                String desc = c.getString(c.getColumnIndex("descricao"));
                //avisar(context,titulo,minimal_desc(desc),Integer.parseInt(cod));
                //database.sql("UPDATE avaliacoes_trabalhos SET avisei='sim' WHERE nome='"+titulo+"'");
            }
        }
        else
        {

        }
        show();
        checkPast();

    }
    public void checkPast()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-1);
        Date myDate = cal.getTime();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        String data = formater.format(myDate);
        database.sql("DELETE FROM avaliacoes_trabalhos WHERE data=\""+data+"\"");
    }
    public void show()
    {
        Cursor c = database.select("SELECT * FROM avaliacoes_trabalhos");
        while(c.moveToNext())
        {
            Log.i("Script","item="+c.getString(c.getColumnIndex("nome")));
        }
    }
    public Boolean exists(String nome)
    {
        Boolean res = false;
        Cursor c = database.select("SELECT * FROM avaliacoes_trabalhos WHERE nome='"+nome+"'");
        if(c.getCount() > 0)
        {
            res = true;
        }
        return res;
    }
    public String minimal_desc(String text)
    {
        String res = "";
        try
        {
            int i = 0;
            while(i < 50)
            {
                res += text.charAt(i);
                i++;
            }
        }
        catch (Exception e)
        {

        }
        return res;
    }
    */