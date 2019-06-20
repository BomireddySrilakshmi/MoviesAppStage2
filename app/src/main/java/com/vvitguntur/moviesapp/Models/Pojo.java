package com.vvitguntur.moviesapp.Models;

import java.io.Serializable;

public class Pojo implements Serializable{
    String mtitle,poster,release,overview,rate;
    int id;
    public Pojo(String mtitle, String poster, String release, String overview, String rate,int id) {
        this.mtitle = mtitle;
        this.poster = poster;
        this.release = release;
        this.overview = overview;
        this.rate = rate;
        this.id=id;
    }

    public String getMtitle() {
        return mtitle;
    }

    public String getPoster() {
        return poster;
    }

    public String getRelease() {
        return release;
    }

    public String getOverview() {
        return overview;
    }

    public String getRate() {
        return rate;
    }

    public int getId() {
        return id;
    }
}
