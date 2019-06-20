package com.vvitguntur.moviesapp.Models;

import java.io.Serializable;

public class Trailers implements Serializable{
    String trailerid;
    String key;
    String name;

    public Trailers(String trailerid, String key, String name) {
        this.trailerid = trailerid;
        this.key = key;
        this.name = name;
    }

    public String getTrailerid() {
        return trailerid;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
