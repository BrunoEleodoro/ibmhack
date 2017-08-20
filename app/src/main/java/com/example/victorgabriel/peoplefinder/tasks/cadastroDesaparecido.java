package com.example.victorgabriel.peoplefinder.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.example.victorgabriel.peoplefinder.BaseURL;
import com.example.victorgabriel.peoplefinder.Database;
import com.example.victorgabriel.peoplefinder.Internet;
import com.example.victorgabriel.peoplefinder.Message;
import com.example.victorgabriel.peoplefinder.activities.people;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by bruno on 19/08/17.
 */

public class cadastroDesaparecido extends AsyncTask<String,String,String> {
    /*
    $nome_des = @$_GET["nome_des"];
    $idade_des = @$_GET["idade_des"];
    $latitude = @$_GET["latitude"];
    $longitude = @$_GET["longitude"];
    $descricao = @$_GET["descricao"];
    $data = @$_GET["data"];
    $hora = @$_GET["hora"];
    $contato = @$_GET["contato"];
     */
    Message message = new Message();
    BaseURL baseURL = new BaseURL();
    Internet internet = new Internet();
    Activity activity;
    String nome_des;
    String idade_des;
    String descricao;
    String latitude;
    String longitude;
    String data;
    String hora;
    String contato;
    ProgressDialog dialog;
    Database db;
    File file;
    public cadastroDesaparecido(Activity activity,String nome_des,String idade_des,String descricao,String latitude,String longitude,String data,String hora,String contato,File file)
    {
        this.activity = activity;
        this.nome_des = internet.encode(nome_des);
        this.idade_des = internet.encode(idade_des);
        this.descricao = internet.encode(descricao);
        this.latitude = internet.encode(latitude);
        this.longitude = internet.encode(longitude);
        this.data = internet.encode(data);
        this.hora = internet.encode(hora);
        this.contato = internet.encode(contato);
        this.file = file;
        db = new Database(activity);
        dialog =message.progress(activity,"Aguarde, salvando no banco de dados...");
    }
    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        /*
        $nome_des = @$_GET["nome_des"];
    $idade_des = @$_GET["idade_des"];
    $latitude = @$_GET["latitude"];
    $longitude = @$_GET["longitude"];
    $descricao = @$_GET["descricao"];
    $data = @$_GET["data"];
    $hora = @$_GET["hora"];
    $contato = @$_GET["contato"];
         */
        res = internet.get("cadastroDesaparecido.php?nome_des="+nome_des+"&idade_des="+idade_des+"&latitude="+latitude+"&longitude="+longitude+"&descricao="+descricao+"&data="+data+"&hora="+hora+":00&contato="+contato,"base64="+base64(file));
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
                //String codigo = s.replace("[cod]","");
                Toast.makeText(activity.getApplicationContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(activity,people.class);
                activity.startActivity(i);
                activity.finish();
            }
        }
    }
    public String base64(File file)
    {
        String res = "";
        try
        {
            FileInputStream leitor = new FileInputStream(file);
            byte cod_fonte[] = new byte[(int) file.length()];
            leitor.read(cod_fonte);
            res = Base64.encodeToString(cod_fonte, Base64.DEFAULT);
        }
        catch(Exception e)
        {

        }
        return res;
    }
}
