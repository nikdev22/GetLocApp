package com.example.nik.getlocapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Function {
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null;
        return isConnected;
    }

    public static String executeGet(String targetURL, String urlParameters){
        URL url;
        HttpURLConnection connection = null;
        try{
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("content-type","application/json; charset = utf-8");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(false);


            InputStream is;
            int status = connection.getResponseCode();
            if(status != HttpURLConnection.HTTP_OK)
                is = connection.getErrorStream();
            else
                is = connection.getInputStream();

            BufferedReader br= new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer stringBuffer = new StringBuffer();
            while((line = br.readLine()) != null){
                stringBuffer.append(line);
                stringBuffer.append('\r');

            }
            br.close();
            return stringBuffer.toString();

        }
        catch (Exception e){
            return null;
        }finally {
            if(connection != null)
                connection.disconnect();
        }
    }
}
