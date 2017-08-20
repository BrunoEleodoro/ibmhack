package com.example.victorgabriel.peoplefinder.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.victorgabriel.peoplefinder.BaseURL;
import com.example.victorgabriel.peoplefinder.Desaparecido;
import com.example.victorgabriel.peoplefinder.Internet;
import com.example.victorgabriel.peoplefinder.Message;
import com.example.victorgabriel.peoplefinder.R;
import com.example.victorgabriel.peoplefinder.adapters.DesaparecidoAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
    String idade_min;
    String idade_max;
    GoogleMap mMap;
    ProgressDialog dialog;
    double minha_lat,minha_long;
    public listDesaparecidosMapa(Activity activity, GoogleMap mMap,double minha_lat,double minha_long, String data_min, String data_max, String idade_min, String idade_max)
    {
        this.activity = activity;
        this.mMap = mMap;
        this.minha_lat = minha_lat;
        this.minha_long = minha_long;
        this.data_min = internet.encode(data_min);
        this.data_max = internet.encode(data_max);
        this.idade_min = internet.encode(idade_min);
        this.idade_max = internet.encode(idade_max);
        dialog = message.progress(activity,"Aguarde, buscando pessoas desaparecidas próximas...");
    }
    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        res = internet.get("listDesaparecidos.php?data_min="+data_min+"&data_max="+data_max+"&idade_min="+idade_min+"&idade_max="+idade_max,"");

        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
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
                    boolean perto = distance < 600;
                    BitmapDescriptor icone = BitmapDescriptorFactory.fromResource(R.drawable.iconbonito_pequeno);
                    if(perto)
                    {
                        LatLng ponto = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                        MarkerOptions marker = new MarkerOptions().position(ponto).title(nome_des).icon(icone);
                        mMap.addMarker(marker);
                    }

                    i++;
                }
                mMap.addCircle(new CircleOptions()
                .center(new LatLng(minha_lat,minha_long))
                .radius(600)
                .strokeColor(Color.parseColor("#87cefa"))
                .fillColor(0x5587cefa)
                .strokeWidth(2));

            }
            catch (Exception e)
            {
                message.showMessage(activity,"Ocorreu um erro, tente novamente mais tarde!");
            }

    }
}
