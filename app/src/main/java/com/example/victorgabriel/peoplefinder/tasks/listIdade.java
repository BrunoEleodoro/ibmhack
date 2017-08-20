package com.example.victorgabriel.peoplefinder.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.victorgabriel.peoplefinder.Internet;
import com.example.victorgabriel.peoplefinder.Message;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Victor Gabriel on 19/08/2017.
 */

public class listIdade extends AsyncTask<String,String,String> {

    Internet internet = new Internet();

    Message message = new Message();

    Activity activity;

    Spinner sp1;

    Spinner sp2;

    String datas = null;

    public listIdade(Activity activity,Spinner sp1, Spinner sp2)
    {
        this.activity = activity;
        this.sp2 = sp2;
        this.sp1 = sp1;

    }

    @Override
    protected String doInBackground(String... params) {
        String res = "";
        res = internet.get("listIdade.php","");
        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);



        if(s.contains("[erro]"))
        {
            message.showMessage(activity,"Ocorreu um erro, tente novamente mais tarde!");
        }
        else
        {
            try
            {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_dropdown_item);
                JSONArray array = new JSONArray(s);
                int i = 0;
                while(i<array.length())
                {
                    JSONObject object = array.getJSONObject(i);
                    datas = object.optString("idade_des");
                    adapter.add(datas);
                    i++;
                }


                sp1.setAdapter(adapter);
                sp2.setAdapter(adapter);

            }
            catch (Exception e)
            {
                Log.i("Script","erro json listIdade="+e);
            }
        }
    }
}
