package com.example.victorgabriel.peoplefinder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.victorgabriel.peoplefinder.R;
import com.example.victorgabriel.peoplefinder.tasks.getLogin;

public class login extends AppCompatActivity {

    Button btn_cad;
    Button btn_entrar;

    EditText login;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.editText);
        senha = (EditText) findViewById(R.id.editText2);

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

                new getLogin(login.this,login.getText().toString().trim(),senha.getText().toString()).execute("");

                Intent i = new Intent(login.this,Maps_des.class);
                startActivity(i);
            }
        });

    }
}
