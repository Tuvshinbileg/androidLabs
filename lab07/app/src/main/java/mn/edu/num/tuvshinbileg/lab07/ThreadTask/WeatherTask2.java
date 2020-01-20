package mn.edu.num.tuvshinbileg.lab07.ThreadTask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mn.edu.num.tuvshinbileg.lab07.model.Weather;

/**
 * Current day for different country
 */
public class WeatherTask2 extends AsyncTask<String, Void, List<Weather>> {
    private BufferedReader reader = null;

    @Override
    protected List<Weather> doInBackground(String... address) {
        StringBuffer buffer = null;
        try {
            URL url = new URL(address[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));
             buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
                Log.i("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  parsingJSON(buffer.toString());
    }

    public List<Weather> parsingJSON(String data) {
        List<Weather> weathers=new ArrayList<Weather>();
        String cityName="";
        String coordLon="";
        String coordLat="";
        String temp="";
        String description="";
        String date="";
        try {
            JSONObject jsonObject = new JSONObject(data);
            String weatherData = jsonObject.getString("list");
            JSONArray array = new JSONArray(weatherData);
            Log.i("JSONArray","Length"+array.length());
            for (int i = 0; i < array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                JSONObject coord=weatherPart.getJSONObject("coord");
                JSONObject weather= (JSONObject) weatherPart.getJSONArray("weather").get(0);
                JSONObject main=weatherPart.getJSONObject("main");
                coordLon=coord.getString("lon");
                coordLat=coord.getString("lat");
                Log.i("parsingJSON",coordLon +" ," +coordLat);
                description= String.valueOf(weather.getString("description"));
                Log.i("parsingJSON",description);
                temp=main.getString("temp");
                Log.i("parsingJSON",temp);
                cityName=weatherPart.getString("name");
                Log.i("parsingJSON",cityName);
                date=weatherPart.getString("dt");
                weathers.add(new Weather(coord.getString("lon"),coord.getString("lat"),cityName, weather.getString("description"),
                        main.getString("temp"),date));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return weathers;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}

