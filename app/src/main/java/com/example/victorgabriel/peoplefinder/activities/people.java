package com.example.victorgabriel.peoplefinder.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.victorgabriel.peoplefinder.Database;
import com.example.victorgabriel.peoplefinder.Desaparecido;
import com.example.victorgabriel.peoplefinder.Internet;
import com.example.victorgabriel.peoplefinder.R;
import com.example.victorgabriel.peoplefinder.tasks.listData;
import com.example.victorgabriel.peoplefinder.tasks.listDesaparecidos;
import com.example.victorgabriel.peoplefinder.tasks.votarSpam;

public class people extends AppCompatActivity {

    ListView lv;
    Database db;
    Spinner sp;
    Spinner filtro1;
    Spinner filtro2;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.list);

        db = new Database(this);

        sp = (Spinner) findViewById(R.id.spinner);

        filtro1 = (Spinner) findViewById(R.id.spinner2);

        filtro2 = (Spinner) findViewById(R.id.spinner3);

        btn = (Button) findViewById(R.id.button5);

        if(sp.getSelectedItem().toString().equals("Idade"))
        {
            new listData(this,filtro1,filtro2).execute("");
        }
        else
        {
            new listData(this,filtro1,filtro2).execute("");
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp.getSelectedItem().toString().equals("Idade"))
                {
                    Internet net = new Internet();
                    String idade1 = filtro1.getSelectedItem().toString();
                    String idade2 = filtro2.getSelectedItem().toString();
                    new listDesaparecidos(people.this,lv,"","",idade1,idade2).execute("");

                }
                else
                {
                    Internet net = new Internet();
                    String data1 = net.data_certa(filtro1.getSelectedItem().toString());
                    String data2 = net.data_certa(filtro2.getSelectedItem().toString());
                    new listDesaparecidos(people.this,lv,data1,data2,"","").execute("");
                }
            }
        });

        new listDesaparecidos(this,lv,"","","","").execute("");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final Desaparecido des = new Desaparecido();

                AlertDialog.Builder detalhes = new AlertDialog.Builder(people.this);
                detalhes.setTitle("Spam");
                detalhes.setMessage("Deseja denunciar esse desaparecimento como spam?");
                detalhes.setIcon(R.drawable.icon_dois);
                detalhes.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String cod = null;

                        Cursor c = db.select("SELECT * FROM login");
                        while(c.moveToNext())
                        {
                            cod = c.getString(c.getColumnIndex("cod"));
                        }

                        new votarSpam(people.this,""+des.getCod(),cod).execute("");

                    }
                });
                detalhes.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                detalhes.show();

                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(people.this,cadastro_des.class);
                startActivity(i);
            }
        });
    }

}
