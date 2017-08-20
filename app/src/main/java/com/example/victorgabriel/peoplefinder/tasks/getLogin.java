package com.example.victorgabriel.peoplefinder.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.victorgabriel.peoplefinder.BaseURL;
import com.example.victorgabriel.peoplefinder.Database;
import com.example.victorgabriel.peoplefinder.Internet;
import com.example.victorgabriel.peoplefinder.Message;
import com.example.victorgabriel.peoplefinder.activities.Maps_des;

/**
 * Created by bruno on 19/08/17.
 */

public class getLogin extends AsyncTask<String,String,String> {
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
    public getLogin(Activity activity, String email, String senha)
    {
        this.activity = activity;
        this.lv = lv;
        this.email = email;
        this.senha = internet.encode(senha);
        db = new Database(activity);
        dialog =message.progress(activity,"Aguarde, buscando pessoas desaparecidas...");
    }
    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        res = internet.get("getLogin.php?email="+email+"&senha="+senha,"");
        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
        Log.i("Script","login retorno="+s);
        if(s.contains("[erro]"))
        {
          message.showMessage(activity,"Ocorreu um erro, tente novamente mais tarde!");
        }
        else
        {
            if(s.contains("[cod]"))
            {
                String codigo = s.replace("[cod]","");
                Toast.makeText(activity.getApplicationContext(), "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                db.sql("DELETE FROM login WHERE 1");
                db.sql("INSERT INTO login VALUES("+codigo+",\""+email+"\",\""+senha+"\");");
                Intent i = new Intent(activity,Maps_des.class);
                activity.startActivity(i);
                activity.finish();
            }
            else
            {
                Toast.makeText(activity.getApplicationContext(), "Email ou senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
