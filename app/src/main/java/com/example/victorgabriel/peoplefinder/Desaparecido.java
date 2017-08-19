package com.example.victorgabriel.peoplefinder;

import android.widget.BaseAdapter;

/**
 * Created by Victor Gabriel on 19/08/2017.
 */

public class Desaparecido {

    int cod;
    String nome_des;
    String idade_des;
    String latitude;
    String longitude;
    String descricao;
    String img;
    String data;
    String contato;
    String valido;
    String hora;

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getValido() {
        return valido;
    }

    public void setValido(String valido) {
        this.valido = valido;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome_des() {
        return nome_des;
    }

    public void setNome_des(String nome_des) {
        this.nome_des = nome_des;
    }

    public String getIdade_des() {
        return idade_des;
    }

    public void setIdade_des(String idade_des) {
        this.idade_des = idade_des;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }



}
