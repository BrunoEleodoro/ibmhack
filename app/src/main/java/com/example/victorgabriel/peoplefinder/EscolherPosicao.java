package com.example.victorgabriel.peoplefinder;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.victorgabriel.peoplefinder.activities.cadastro_des;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EscolherPosicao extends AppCompatActivity implements OnMapReadyCallback{
    private LocationManager locationManager;
    ProgressDialog dialog;
    GoogleMap mMap;
    int selecionado = 0;
    Boolean me_achou = false;
    double lat,lng;
    float zoom = (float) 17.5;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_posicao);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        carregarMapa();

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton3);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findMe();
            }
        });
    }
    public void confirmar(View view)
    {
        Intent intent = new Intent(this,cadastro_des.class);
        intent.putExtra("latitude",""+lat);
        intent.putExtra("longitude",""+lng);
        startActivity(intent);
        finish();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                MarkerOptions marker = new MarkerOptions().position(latLng).title("Minha localização");
                mMap.addMarker(marker);
                lat = latLng.latitude;
                lng = latLng.longitude;
                mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom),2000,null);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
            }
        });
        findMe();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 999) {
            int length = permissions.length;
            int i = 0;
            while (i < length) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Permissao negada", Toast.LENGTH_SHORT).show();
                    finish();
                }
                i++;
            }

        }
    }
    public void carregarMapa()
    {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            dialog = new ProgressDialog(this);
            dialog.setTitle("Aviso");
            dialog.setIcon(R.mipmap.ic_launcher);
            dialog.setMessage("Aguarde...\nBuscando localizaçao do gps");
            dialog.setIndeterminate(true);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "SAIR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
            mapFragment.getMapAsync(EscolherPosicao.this);
            dialog.show();
        }else{
            showGPSDisabledAlertToUser();
        }
    }
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS esta desabilitado, gostaria de habilita-lo?")
                .setCancelable(false)
                .setPositiveButton("IR PARA PAGINA PARA HABILITAR",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("CANCELAR",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    public void findMe() {

        if (ActivityCompat.checkSelfPermission(EscolherPosicao.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location arg0) {
                // TODO Auto-generated method stub
                if(dialog.isShowing())
                {
                    dialog.dismiss();
                }
                lat = arg0.getLatitude();
                lng = arg0.getLongitude();
                LatLng minha_localizacao = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions().position(minha_localizacao).title("Você está aqui?"));
                me_achou = true;
                if (ActivityCompat.checkSelfPermission(EscolherPosicao.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EscolherPosicao.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(false);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(minha_localizacao));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(minha_localizacao).zoom(17).build();
                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
            }
        });
    }
}
