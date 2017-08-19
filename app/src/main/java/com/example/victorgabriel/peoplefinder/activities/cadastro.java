package com.example.victorgabriel.peoplefinder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.victorgabriel.peoplefinder.Message;
import com.example.victorgabriel.peoplefinder.R;
import com.example.victorgabriel.peoplefinder.tasks.novoCadastro;

public class cadastro extends AppCompatActivity {

    EditText nome;
    EditText rg;
    EditText email;
    EditText senha;
    EditText senha2;

    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = (EditText) findViewById(R.id.editText3);

        rg = (EditText) findViewById(R.id.editText6);

        email = (EditText) findViewById(R.id.editText4);

        senha = (EditText) findViewById(R.id.editText5);

        senha2 = (EditText) findViewById(R.id.editText7);

        btn = (Button) findViewById(R.id.button4);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(senha.getText().toString().equals(senha2.getText().toString())) {

                    new novoCadastro(cadastro.this, nome.getText().toString().trim(), rg.getText().toString().trim(), email.getText().toString().trim(), senha.getText().toString()).execute("");

                }
                else
                {
                    Message msg = new Message();
                    msg.showMessage(cadastro.this,"Senhas diferentes!");
                    senha.setText("");
                    senha2.setText("");
                }
            }
        });
    }
}
