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
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mn.edu.num.tuvshinbileg.lab07.model.CurrentDay;
import mn.edu.num.tuvshinbileg.lab07.model.Entry;

public class WeatherTask3 extends AsyncTask<String, Void, List<CurrentDay>> {
    BufferedReader reader = null;

    @Override
    protected List<CurrentDay> doInBackground(String... address) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(address[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
                Log.i("ResponseTask3: ", "> " + line);   //here u ll get whole response...... :-)

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
    public List<CurrentDay> parsingJSON(String data){
        String date="";
        String temp="";
        String windSpeed="";
        String description="";
        List<CurrentDay> list=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(data);
            String weatherData = jsonObject.getString("list");
            JSONArray array = new JSONArray(weatherData);
            Log.i("JSON LENGTH:",""+array.length());
            for(int i=0; i<5; i++){
                JSONObject weatherPart=array.getJSONObject(i);
                JSONObject main=weatherPart.getJSONObject("main");
                temp=main.getString("temp");
                JSONObject weather= (JSONObject) weatherPart.getJSONArray("weather").get(0);
                description= weather.getString("description");
                JSONObject wind=weatherPart.getJSONObject("wind");
                windSpeed=wind.getString("speed");
                date=weatherPart.getString("dt_txt");
                list.add(new CurrentDay(date,temp,description,windSpeed));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    return  list;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
