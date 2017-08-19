package com.example.victorgabriel.peoplefinder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.victorgabriel.peoplefinder.R;

public class login extends AppCompatActivity {

    Button btn_cad;
    Button btn_entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_cad = (Button) findViewById(R.id.button3);
        btn_entrar = (Button) findViewById(R.id.button2);

        btn_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this,cadastro.class);
                startActivity(i);
            }
        });

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this,Maps_des.class);
                startActivity(i);
            }
        });

    }
}
