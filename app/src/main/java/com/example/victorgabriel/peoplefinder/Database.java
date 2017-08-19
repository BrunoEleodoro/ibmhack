package com.example.victorgabriel.peoplefinder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by bruno on 06/07/17.
 */

public class Database {
    SQLiteDatabase db;
    public Database(Context context)
    {
        db = context.openOrCreateDatabase("banco_DB",context.MODE_PRIVATE,null);
        create_tables();
    }
    public void sql(String sql)
    {
        db.execSQL(sql);
    }
    public Cursor select(String sql)
    {
        return db.rawQuery(sql,null);
    }

    public void create_tables()
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS login(" +
                "cod INTEGER not null," +
                "email varchar(500) not null," +
                "senha varchar(500) not null," +
                "Primary key(cod));");

        db.execSQL("CREATE TABLE IF NOT EXISTS users(" +
                "cod INTEGER not null," +
                "nome varchar(500) not null," +
                "rg varchar(500) not null," +
                "email varchar(500) not null," +
                "senha varchar(500) not null," +
                "Primary key(cod));");

        db.execSQL("CREATE TABLE IF NOT EXISTS desaparecidos(" +
                "cod INTEGER not null," +
                "nome_des varchar(500) not null," +
                "idade_res int not null," +
                "latitude varchar(500) not null," +
                "longitude varchar(500) not null," +
                "descricao varchar(1000) not null," +
                "img varchar(500) not null," +
                "data varchar(500) not null," +
                "hora varchar(500) not null," +
                "contato varchar(500) not null," +
                "valido int not null," +
                "Primary key(cod));");

        /*
        db.execSQL("CREATE TABLE IF NOT EXISTS votacao(" +
                "cod INTEGER not null," +
                "cod_des int not null," +
                "cod_user int not null," +
                "qtd int not null," +
                "Primary key(cod));");
                */
    }
}


