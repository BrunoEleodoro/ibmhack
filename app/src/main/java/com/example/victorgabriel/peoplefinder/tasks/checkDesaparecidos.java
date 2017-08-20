package com.example.victorgabriel.peoplefinder.tasks;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.victorgabriel.peoplefinder.BaseURL;
import com.example.victorgabriel.peoplefinder.Desaparecido;
import com.example.victorgabriel.peoplefinder.Internet;
import com.example.victorgabriel.peoplefinder.Message;
import com.example.victorgabriel.peoplefinder.R;
import com.example.victorgabriel.peoplefinder.activities.loading;
import com.example.victorgabriel.peoplefinder.activities.ver_des;
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

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by bruno on 19/08/17.
 */

public class checkDesaparecidos extends AsyncTask<String, String, String> {
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
    double minha_lat, minha_long;
    LocationManager locationManager;


    Location location;

    public checkDesaparecidos(Activity activity) {
        this.activity = activity;

        this.data_min = internet.encode(data_min);
        this.data_max = internet.encode(data_max);
        this.idade_min = internet.encode(idade_min);
        this.idade_max = internet.encode(idade_max);
        dialog = message.progress(activity, "Aguarde, buscando pessoas desaparecidas próximas...");
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        res = internet.get("listDesaparecidos.php?data_min=" + data_min + "&data_max=" + data_max + "&idade_min=" + idade_min + "&idade_max=" + idade_max, "");

        return res;
    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
        try {
            JSONArray array = new JSONArray(s);
            int i = 0;
            final List<String> lats = new ArrayList<>();
            final List<Desaparecido> desaparecidos = new ArrayList<>();
            List<LatLng> pontos = new ArrayList<>();
            while (i < array.length()) {
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
                lats.add(latitude);
                desaparecidos.add(desaparecido);

                float[] results = new float[1];
                Location.distanceBetween(minha_lat, minha_long, Double.parseDouble(latitude), Double.parseDouble(longitude), results);
                float distance = results[0];
                boolean perto = distance < 600;
                BitmapDescriptor icone = BitmapDescriptorFactory.fromResource(R.drawable.iconbonito_pequeno);
                if (perto) {

                }

                i++;
            }
        } catch (Exception e) {
            message.showMessage(activity, "Ocorreu um erro, tente novamente mais tarde!");
        }

    }

    public void getLocation() {

    }

    public void avisar (Context context, String titulo, String desc, int id)
    {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(titulo)
                        .setContentText(desc);

        Intent intent = new Intent(context, loading.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        builder.setAutoCancel(true);
        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());

    }
}
