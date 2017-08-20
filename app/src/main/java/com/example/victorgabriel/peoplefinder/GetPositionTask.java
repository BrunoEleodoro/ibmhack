package com.example.victorgabriel.peoplefinder;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

import com.example.victorgabriel.peoplefinder.activities.loading;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor Gabriel on 20/08/2017.
 */
public class GetPositionTask extends AsyncTask<Void, Void, Location> implements LocationListener {
    final long TWO_MINUTES = 2 * 60 * 1000;
    private Location location;
    private LocationManager lm;
    Context context;
    Internet internet = new Internet();
    String res = "";
    public GetPositionTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        // Configure location manager - I'm using just the network provider in this example
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        //nearProgress.setVisibility(View.VISIBLE);
    }

    protected Location doInBackground(Void... params) {
        // Try to use the last known position
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location lastLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        // If it's too old, get a new one by location manager
        if (System.currentTimeMillis() - lastLocation.getTime() > TWO_MINUTES) {
            while (location == null)
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }

            return location;
        }
        res = internet.get("listDesaparecidos.php", "");
        return lastLocation;
    }

    protected void onPostExecute(Location location) {

        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.removeUpdates(this);
        try {
            JSONArray array = new JSONArray(res);
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
                Location.distanceBetween(location.getLatitude(),location.getLongitude(), Double.parseDouble(latitude), Double.parseDouble(longitude), results);
                float distance = results[0];
                boolean perto = distance < 600;
                if (perto) {
                    avisar(context,"Atenção","Existe um desaparecimento proximo de você!",0);
                }

                i++;
            }
        } catch (Exception e) {

        }
        //Toast.makeText(context, "latitude=" + location.getLatitude(), Toast.LENGTH_SHORT).show();
        // HERE USE THE LOCATION
    }
    public void avisar (Context context, String titulo, String desc,int id)
    {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.iconbonito_pequeno)
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

    @Override
    public void onLocationChanged(Location newLocation) {
        location = newLocation;
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}