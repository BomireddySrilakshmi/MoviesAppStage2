package com.vvitguntur.moviesapp.NetUtils;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Networkutils {
    public static final String BASE_URL="https://api.themoviedb.org/3/movie/";
    private static final String KEY="api_key";
    public static String getQuery;
    public static void setGetQuery(String value){getQuery=value;}
    public static Uri buildUri()
    {
        Uri finalUri=Uri.parse(BASE_URL).buildUpon().appendPath(getQuery).appendQueryParameter(KEY,"c2b6226475df2f71cc1497d17ade7431")
                .build();
        return finalUri;
    }
    public static String getMovieInfo(){
        try {
            URL requestURL = new URL(buildUri().toString());
            HttpsURLConnection httpsURLConnection= (HttpsURLConnection) requestURL.openConnection();
            httpsURLConnection.connect();
            InputStream inputStream=httpsURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder=new StringBuilder();
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line+"\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
