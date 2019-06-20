package com.vvitguntur.moviesapp.Models;

import java.io.Serializable;

public class Reviews implements Serializable {
    String authorname;
    String reviewcontent;

    public Reviews(String authorname, String reviewcontent) {
        this.authorname = authorname;
        this.reviewcontent = reviewcontent;
    }

    public String getAuthorname() {
        return authorname;
    }

    public String getReviewcontent() {
        return reviewcontent;
    }
}
