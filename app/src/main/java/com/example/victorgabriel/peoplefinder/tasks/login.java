package com.example.victorgabriel.peoplefinder.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class login extends AsyncTask<String,String,String> {
    Message message = new Message();
    BaseURL baseURL = new BaseURL();
    Internet internet = new Internet();
    Activity activity;
    String nome;
    String email;
    String rg;
    String senha;
    ListView lv;
    ProgressDialog dialog;
    Database db;
    public login(Activity activity,String email, String senha)
    {
        this.activity = activity;
        this.lv = lv;
        this.email = internet.encode(email);
        this.senha = internet.encode(senha);
        db = new Database(activity);
        dialog =message.progress(activity,"Aguarde, buscando pessoas desaparecidas...");
    }
    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        res = internet.get("login.php?email="+email+"&senha="+senha,"");
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
                Toast.makeText(activity.getApplicationContext(), "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                db.sql("INSERT INTO login VALUES(null,\""+email+"\",\""+senha+"\");");
                //Intent intent = new Intent(activity,Maps);
                activity.finish();
            }
        }
    }
}
