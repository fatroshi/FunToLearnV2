package se.atroshi.funtolearnv2.SiteConnection;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Farhad on 25/12/15.
 */
public class HttpManager {

    private final static String tag = "HttpManager";

    public static String getDate(String uri){
        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection  connection= (HttpURLConnection) url.openConnection();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }

            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if(reader !=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        Log.i(tag,"Could not get the data");
        return null;
    }

}
