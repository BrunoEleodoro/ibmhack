package com.example.victorgabriel.peoplefinder.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.victorgabriel.peoplefinder.BaseURL;
import com.example.victorgabriel.peoplefinder.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class ver_des extends AppCompatActivity implements OnMapReadyCallback{
    GoogleMap mMap;
    double latitude;
    double longitude;
    EditText txt_nome;
    EditText txt_idade;
    EditText txt_descricao;
    EditText txt_contato;
    ImageView txt_img;
    TextView txt_data;
    TextView txt_hora;
    ImageView call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_des);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this);

        String nome_des = getIntent().getStringExtra("nome_des");
        String idade_des = getIntent().getStringExtra("idade_des");
        latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
        String descricao = getIntent().getStringExtra("descricao");
        String img = getIntent().getStringExtra("img");
        String data = getIntent().getStringExtra("data");
        String hora = getIntent().getStringExtra("hora");
        final String contato = getIntent().getStringExtra("contato");

        txt_nome = (EditText) findViewById(R.id.txt_nome);
        txt_idade = (EditText) findViewById(R.id.txt_idade);
        txt_descricao = (EditText) findViewById(R.id.txt_descricao);
        txt_img = (ImageView) findViewById(R.id.txt_img);
        txt_data = (TextView) findViewById(R.id.txt_data);
        txt_hora = (TextView) findViewById(R.id.txt_hora);
        txt_contato = (EditText) findViewById(R.id.txt_contato);

        call = (ImageView) findViewById(R.id.imageView4);

        txt_nome.setText(nome_des);
        txt_idade.setText(idade_des);
        txt_descricao.setText(descricao);
        txt_data.setText(data);
        txt_hora.setText(hora);
        txt_contato.setText(contato);

        BaseURL baseURL = new BaseURL();
        Picasso.with(this).load(baseURL.getUrl()+""+img).into(txt_img);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:"+contato);
               Intent intent = new Intent(Intent.ACTION_DIAL,uri);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Pessoa desaparecida"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13),2000,null);
    }
}
