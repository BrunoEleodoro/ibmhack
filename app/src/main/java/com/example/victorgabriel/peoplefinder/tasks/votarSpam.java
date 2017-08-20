package com.example.victorgabriel.peoplefinder.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.example.victorgabriel.peoplefinder.BaseURL;
import com.example.victorgabriel.peoplefinder.Database;
import com.example.victorgabriel.peoplefinder.Internet;
import com.example.victorgabriel.peoplefinder.Message;

/**
 * Created by bruno on 19/08/17.
 */

public class votarSpam extends AsyncTask<String,String,String> {
    Message message = new Message();
    BaseURL baseURL = new BaseURL();
    Internet internet = new Internet();
    Activity activity;
    String cod_des;
    String cod_user;
    ListView lv;
    ProgressDialog dialog;
    Database db;
    public votarSpam(Activity activity, String cod_des, String cod_user)
    {
        this.activity = activity;
        this.lv = lv;
        this.cod_des = internet.encode(cod_des);
        this.cod_user = internet.encode(cod_user);
        db = new Database(activity);
        dialog =message.progress(activity,"Aguarde, marcando como spam...");
    }
    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        res = internet.get("votarSpam.php?cod_des="+cod_des+"&cod_user="+cod_user,"");

        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
        
        if(s.contains("[erro]"))
        {
            message.showMessage(activity,"Ocorreu um erro, tente novamente mais tarde!");
        }
        else
        {
            if(s.contains("[sucesso]"))
            {
                Toast.makeText(activity.getApplicationContext(), "Marcado com sucesso!", Toast.LENGTH_SHORT).show();

            }
            else if(s.contains("[cadastrado]"))
            {
                Toast.makeText(activity.getApplicationContext(), "Você já marcou isto como spam!", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
