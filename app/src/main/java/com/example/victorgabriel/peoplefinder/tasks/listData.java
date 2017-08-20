package com.example.victorgabriel.peoplefinder.tasks;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.victorgabriel.peoplefinder.Internet;
import com.example.victorgabriel.peoplefinder.Message;
import com.example.victorgabriel.peoplefinder.R;
import com.example.victorgabriel.peoplefinder.activities.people;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor Gabriel on 19/08/2017.
 */

public class listData extends AsyncTask<String,String,String> {

    Internet internet = new Internet();

    Message message = new Message();

    Activity ac;

    Spinner sp1;

    Spinner sp2;


    public listData(Activity ac, Spinner sp1, Spinner sp2)
    {
        this.ac = ac;
        this.sp2 = sp2;
        this.sp1 = sp1;

    }

    @Override
    protected String doInBackground(String... params) {
        String res = "";
        res = internet.get("listDatas.php","");
        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String datas = null;

        if(s.contains("[erro]"))
        {
            message.showMessage(ac,"Ocorreu um erro, tente novamente mais tarde!");
        }
        else
        {
            try
            {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ac,android.R.layout.simple_spinner_dropdown_item);
                JSONArray array = new JSONArray(s);
                int i = 0;
                while(i<array.length())
                {
                    JSONObject object = array.getJSONObject(i);
                    datas = object.optString("data");
                    String datas2[] = datas.split("-");
                    String datas3 = datas2[2] + "/" +datas2[1] + "/" + datas2[0];
                    adapter.add(datas3);
                    i++;
                }


                sp1.setAdapter(adapter);
                sp2.setAdapter(adapter);

            }
            catch (Exception e)
            {
                Log.i("Script","erro json listDatas="+e);
            }
        }
    }
}
