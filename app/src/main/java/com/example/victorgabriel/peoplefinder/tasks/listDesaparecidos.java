package com.example.victorgabriel.peoplefinder.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.victorgabriel.peoplefinder.BaseURL;
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

public class listDesaparecidos extends AsyncTask<String,String,String> {
    Message message = new Message();
    BaseURL baseURL = new BaseURL();
    Internet internet = new Internet();
    Activity activity;
    String data_min;
    String data_max;
    String hora_min;
    String hora_max;
    ListView lv;
    ProgressDialog dialog;
    public listDesaparecidos(Activity activity,ListView lv,String data_min, String data_max, String hora_min, String hora_max)
    {
        this.activity = activity;
        this.lv = lv;
        this.data_min = internet.encode(data_min);
        this.data_max = internet.encode(data_max);
        this.hora_min = internet.encode(hora_min);
        this.hora_max = internet.encode(hora_max);
        dialog = message.progress(activity,"Aguarde, buscando pessoas desaparecidas...");
    }
    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        res = internet.get("listDesaparecidos.php?data_min="+data_min+"&data_max="+data_max+"&hora_min="+hora_min+"&hora_max="+hora_max,"");
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
            try
            {
                JSONArray array = new JSONArray(s);
                int i = 0;
                List<Desaparecido> desaparecidos = new ArrayList<>();
                while(i<array.length())
                {
                    JSONObject object = array.getJSONObject(i);
                    String cod = object.optString("cod");
                    String nome_des = object.optString("nome_des");
                    String idade_des = object.optString("idade_des");
                    String latitude = object.optString("latitude");
                    String longitude = object.optString("longitude");
                    String descricao = object.optString("descricao");
                    String img = object.optString("img");
                    String data = object.optString("data");
                    String hora = object.optString("hora");
                    String contato = object.optString("contato");
                    String valido = object.optString("valido");

                    Desaparecido desaparecido = new Desaparecido();
                    desaparecido.setCod(Integer.parseInt(cod));
                    desaparecido.setNome_des(nome_des);
                    desaparecido.setIdade_des(idade_des);
                    desaparecido.setLatitude(latitude);
                    desaparecido.setLongitude(longitude);
                    desaparecido.setDescricao(descricao);
                    desaparecido.setImg(img);
                    desaparecido.setData(data);
                    desaparecido.setHora(hora);
                    desaparecido.setContato(contato);
                    desaparecido.setValido(valido);

                    desaparecidos.add(desaparecido);

                    i++;
                }
                lv.setAdapter(new DesaparecidoAdapter(activity,desaparecidos));
            }
            catch (Exception e)
            {
                Log.i("Script","erro json listDesaparecidos="+e);
            }
        }
    }
}
