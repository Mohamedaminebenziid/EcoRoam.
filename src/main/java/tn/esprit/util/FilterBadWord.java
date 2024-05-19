package tn.esprit.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
public class FilterBadWord {
    public static String filter(String msg) {
        String result="";
        String apiURL= null;
        try {
            apiURL = "https://www.purgomalum.com/service/plain?text="+ URLEncoder.encode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        URL url= null;
        try {
            url = new URL(apiURL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection connection= null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader= null;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String line;
        while(true){
            try {
                if (!((line=reader.readLine())!=null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            result+=line;
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return result;
    }
}

