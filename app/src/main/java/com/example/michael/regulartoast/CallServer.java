package com.example.michael.regulartoast;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by michael on 09.10.17.
 */

public class CallServer extends AsyncTask<String, Void, String>{

    private final CallServerFinishListener callback;

    public CallServer(CallServerFinishListener callback){
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... urlString) {

        String result = "";

        try {
            //Hier haben wir eine Connection
            URL url = new URL("http://space-labs.appspot.com/repo/465001/highscore.sjs");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            InputStream is = connection.getInputStream();

            result = convertStreamToString(is);

        }  catch(IOException e){
            System.err.println(e.toString());
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return result;
    }

    public static String convertStreamToString(InputStream is) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        is.close();

        return sb.toString();
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        callback.callFinished(result);
    }
}
