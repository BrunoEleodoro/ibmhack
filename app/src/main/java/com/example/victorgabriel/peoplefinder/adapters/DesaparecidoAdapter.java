package com.example.victorgabriel.peoplefinder.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.victorgabriel.peoplefinder.BaseURL;
import com.example.victorgabriel.peoplefinder.Database;
import com.example.victorgabriel.peoplefinder.Desaparecido;
import com.example.victorgabriel.peoplefinder.Internet;
import com.example.victorgabriel.peoplefinder.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Victor Gabriel on 19/08/2017.
 */

public class DesaparecidoAdapter extends BaseAdapter{
    List<Desaparecido> des;
    Activity activity;
    Database db;
    Picasso picasso;
    Internet net = new Internet();
    BaseURL url;

    public DesaparecidoAdapter(Activity activity, List<Desaparecido> des)
    {
        this.activity = activity;
        this.des = des;
        db = new Database(activity);
        Picasso.Builder builder = new Picasso.Builder(activity);
        picasso = builder.build();
        url = new BaseURL();
    }
    @Override
    public int getCount() {
        return des.size();
    }

    @Override
    public Object getItem(int position) {
        return des.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    class ViewHolder{

        ImageView img;
        TextView nome;
        TextView data;
        TextView hora;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Desaparecido desaparecido = des.get(position);
        ViewHolder holder = null;
        if(view == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(activity);
            view = inflater.inflate(R.layout.linha_desaparecido,null);
            holder.img = (ImageView) view.findViewById(R.id.img_des);
            holder.nome = (TextView) view.findViewById(R.id.txt_nome);
            holder.data = (TextView) view.findViewById(R.id.txt_data);
            holder.hora = (TextView) view.findViewById(R.id.txt_hora);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        picasso.load(url.getUrl()+""+desaparecido.getImg()).resize(600,600).onlyScaleDown().placeholder(R.drawable.user).into(holder.img);
        holder.nome.setText(desaparecido.getNome_des());
        String data = net.data_certa2(desaparecido.getData());
        holder.data.setText(data);
        holder.hora.setText(desaparecido.getHora());

        return view;
    }
}
