package com.example.victorgabriel.peoplefinder.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.victorgabriel.peoplefinder.BaseURL;
import com.example.victorgabriel.peoplefinder.Desaparecido;
import com.example.victorgabriel.peoplefinder.Internet;
import com.example.victorgabriel.peoplefinder.Message;
import com.example.victorgabriel.peoplefinder.adapters.DesaparecidoAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno on 19/08/17.
 */

public class listDesaparecidosMapa extends AsyncTask<String,String,String> {
    Message message = new Message();
    BaseURL baseURL = new BaseURL();
    Internet internet = new Internet();
    Activity activity;
    String data_min;
    String data_max;
    String hora_min;
    String hora_max;
    GoogleMap mMap;
    ProgressDialog dialog;
    double minha_lat,minha_long;
    public listDesaparecidosMapa(Activity activity, GoogleMap mMap,double minha_lat,double minha_long, String data_min, String data_max, String hora_min, String hora_max)
    {
        this.activity = activity;
        this.mMap = mMap;
        this.data_min = internet.encode(data_min);
        this.data_max = internet.encode(data_max);
        this.hora_min = internet.encode(hora_min);
        this.hora_max = internet.encode(hora_max);
        dialog = message.progress(activity,"Aguarde, buscando pessoas desaparecidas próximas...");
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
                List<LatLng> pontos = new ArrayList<>();
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

                    float[] results = new float[1];
                    Location.distanceBetween(minha_lat,minha_long,Double.parseDouble(latitude),Double.parseDouble(longitude),results);
                    float distance = results[0];
                    boolean perto = distance < 300;
                    Toast.makeText(activity, nome_des + " esta perto", Toast.LENGTH_SHORT).show();
                    /*
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
                    */
                    i++;
                }

            }
            catch (Exception e)
            {
                Log.i("Script","erro json listDesaparecidos="+e);
            }
        }
    }
}
