package com.example.consumoapicontactos.models;

import java.io.Serializable;

public class Address implements Serializable {

    public Geo geo;

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }



}
