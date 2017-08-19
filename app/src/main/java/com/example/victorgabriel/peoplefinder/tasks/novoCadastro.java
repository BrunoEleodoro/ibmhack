package com.example.victorgabriel.peoplefinder.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.victorgabriel.peoplefinder.BaseURL;
import com.example.victorgabriel.peoplefinder.Database;
import com.example.victorgabriel.peoplefinder.Desaparecido;
import com.example.victorgabriel.peoplefinder.Internet;
import com.example.victorgabriel.peoplefinder.Message;
import com.example.victorgabriel.peoplefinder.adapters.DesaparecidoAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno on 19/08/17.
 */

public class novoCadastro extends AsyncTask<String,String,String> {
    Message message = new Message();
    BaseURL baseURL = new BaseURL();
    Internet internet = new Internet();
    Activity activity;
    String nome;
    String email;
    String rg;
    String senha;
    ProgressDialog dialog;
    Database db;
    public novoCadastro(Activity activity,String nome,String rg,String email,String senha)
    {
        this.activity = activity;
        this.nome = internet.encode(nome);
        this.rg = internet.encode(rg);
        this.email = internet.encode(email);
        this.senha = internet.encode(senha);
        db = new Database(activity);
        dialog =message.progress(activity,"Aguarde, buscando pessoas desaparecidas...");
    }
    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        res = internet.get("novoCadastro.php?nome="+nome+"&rg="+rg+"&email="+email+"&senha="+senha,"");
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
            if(s.contains("[cod]"))
            {
                String codigo = s.replace("[cod]","");
                Toast.makeText(activity.getApplicationContext(), "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                db.sql("DELETE FROM login WHERE 1");
                db.sql("DELETE FROM users WHERE 1");
                db.sql("INSERT INTO users VALUES("+codigo+",\""+nome+"\",\""+rg+"\",\""+email+"\",\""+senha+"\");");
                db.sql("INSERT INTO login VALUES("+codigo+",\""+email+"\",\""+senha+"\");");
                activity.finish();
            }
        }
    }
}
