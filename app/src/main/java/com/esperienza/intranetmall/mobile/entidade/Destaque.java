package com.esperienza.intranetmall.mobile.entidade;

import org.parceler.Parcel;

/**
 * Created by ThinkPad on 15/04/2016.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Destaque {

    private int id;
    private String url;
    private String link;
    private int ordem;
    private int tipo;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
