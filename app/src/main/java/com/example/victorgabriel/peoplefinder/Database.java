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
        db.execSQL("CREATE TABLE IF NOT EXISTS notas(" +
                "cod INTEGER not null," +
                "titulo varchar(500) not null," +
                "texto varchar(500) not null," +
                "data date not null," +
                "selecao varchar(5)," +
                "Primary key(cod));");

    }
}


