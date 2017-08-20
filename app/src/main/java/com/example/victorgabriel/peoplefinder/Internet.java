package com.example.victorgabriel.peoplefinder;

import android.util.Log;

import com.example.victorgabriel.peoplefinder.BaseURL;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by bruno on 19/08/17.
 */

public class Internet {
    BaseURL baseURL = new BaseURL();
    public String get(String file_in_server,String data)
    {
        String res = "";
        if(data.equals(""))
        {
            try
            {
                URL url = new URL(baseURL.getUrl()+""+file_in_server);
                URLConnection con = url.openConnection();
                con.setDoOutput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    res += line;
                }

            }
            catch (Exception e)
            {
                Log.i("Script","erro internet= "+e);
                res += "[erro]";
            }
        }
        else
        {
            try
            {
                URL url = new URL(baseURL.getUrl()+""+file_in_server);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setUseCaches(false);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-type","application/x-www-form-urlencoded");

                DataOutputStream printer = new DataOutputStream(con.getOutputStream());
                printer.writeBytes(data);
                printer.close();
                printer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    res += line;
                }
            }
            catch (Exception e)
            {
                res += "[erro]";
            }
        }

        return res;
    }
    public String data_certa(String text)
    {
        String partes[] = text.split("/");
        return partes[2] + "-" +partes[1] + "-" + partes[0];
    }
    public String data_certa2(String text)
    {
        String partes[] = text.split("-");
        return partes[2] + "/" +partes[1] + "/" + partes[0];
    }
    public String encode(String text)
    {
        String res = "";
        try
        {
            res = URLEncoder.encode(text,"UTF-8");
        }
        catch (Exception e)
        {

        }
        return res;
    }
}