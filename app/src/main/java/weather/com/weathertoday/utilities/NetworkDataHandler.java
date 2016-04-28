package weather.com.weathertoday.utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * Created by MMT3760 on 3/12/2016.
 */
public class NetworkDataHandler extends AsyncTask<String, WeatherCallback, String> {

    WeatherCallback callback;

    public NetworkDataHandler(WeatherCallback callback){
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString=params[0]; // URL to call

        String resultToDisplay = "";

        InputStream is = null;

        try {

            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);

            conn.connect();
            int response = conn.getResponseCode();
            if(response==200) {
                is = conn.getInputStream();

                // Convert the InputStream into a string
                resultToDisplay = readIt(is, 50000);
            }

        } catch (Exception e ) {

            Log.e("NetworkDataHandler",e.toString());
            return e.getMessage();

        }
        return resultToDisplay.trim();
    }


    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.e("NetworkDataHandler",s);
        if(null!=callback){
            callback.onResponse(s);
        }

    }
}
