package mn.edu.num.tuvshinbileg.lab07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import mn.edu.num.tuvshinbileg.lab07.ThreadTask.WeatherTask1;
import mn.edu.num.tuvshinbileg.lab07.ThreadTask.WeatherTask2;
import mn.edu.num.tuvshinbileg.lab07.ThreadTask.WeatherTask3;
import mn.edu.num.tuvshinbileg.lab07.model.CurrentDay;
import mn.edu.num.tuvshinbileg.lab07.model.Entry;
import mn.edu.num.tuvshinbileg.lab07.model.Weather;

public class MainActivity extends AppCompatActivity {

    String urlXml = "https://api.openweathermap.org/data/2.5/forecast?q=Ulaanbaatar,mn&mode=xml&APPID=ef6cf6ee239b68a30f6ea77134043d5a";
    String urlInterval = "https://api.openweathermap.org/data/2.5/forecast?q=Ulaanbaatar,mn&mode=json&APPID=ef6cf6ee239b68a30f6ea77134043d5a";
    String urlSeveralCities = "https://api.openweathermap.org/data/2.5/group?id=524901,703448,2643743&units=metric&APPID=ef6cf6ee239b68a30f6ea77134043d5a";
    ListView list1;
    ListView list2;
    ListView list3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        try {
            //Task1
            List<Entry> weathers = new Task1().execute(urlXml).get();
            //Task2
            List<Weather> readFromJSON = new Task2().execute(urlSeveralCities).get();
            //Task3
            List<CurrentDay> currentDays= new Task3().execute(urlInterval).get();


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void init(){
        list1=(ListView) findViewById(R.id.list1);
        list2=(ListView) findViewById(R.id.list2);
        list3=(ListView) findViewById(R.id.list3);


    }


    public class Task1 extends AsyncTask<String, Void, List<Entry>> {

        private BufferedReader reader;
        private List<Entry> entries;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected void onPostExecute(List<Entry> entries) {
            super.onPostExecute(entries);
            list1.setAdapter(new ArrayAdapter<Entry>(MainActivity.this,android.R.layout.simple_list_item_1,entries));
        }

        @Override
        protected List<Entry> doInBackground(String... address) {
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream istream = connection.getInputStream();
                //ready to be read
                xmlParsing(istream);
                reader = new BufferedReader(new InputStreamReader(istream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.i("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
           /* if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            }

            return this.entries;
        }


        public void xmlParsing(InputStream istream) throws Exception {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            try {
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(istream, null);
                parser.nextTag();
                //ParsingJSon
                readXMlData(parser);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }

        public void readXMlData(XmlPullParser parser) throws XmlPullParserException, IOException {
            entries = new ArrayList<Entry>();
            int eventType = parser.getEventType();
            String tag = "";
            Entry entry = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                tag = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        switch (tag) {
                            case "time":
                                entry = new Entry();
                                entry.setTimeTo(parser.getAttributeValue(null, "to"));
                                entry.setTimeFrom(parser.getAttributeValue(null, "from"));
                                entries.add(entry);
                                break;
                            case "temperature":
                                entry.setTemperature(parser.getAttributeValue(null, "value"));
                                break;
                            case "windDirection":
                                String wind = parser.getAttributeValue(null, "deg");
                                entry.setWindDirection(wind);
                                break;
                            case "clouds":
                                String clouds = parser.getAttributeValue(null, "value");
                                entry.setClouds(clouds);
                                break;
                        }

                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }


        }

    }

    public class Task2 extends AsyncTask<String, Void, List<Weather>> {
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

        @Override
        protected void onPostExecute(List<Weather> weathers) {
            super.onPostExecute(weathers);
            list2.setAdapter(new ArrayAdapter<Weather>(MainActivity.this,android.R.layout.simple_list_item_1,weathers));
        }
    }

    public class Task3 extends AsyncTask<String, Void, List<CurrentDay>> {
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

        @Override
        protected void onPostExecute(List<CurrentDay> currentDays) {
            super.onPostExecute(currentDays);
            list3.setAdapter(new ArrayAdapter<CurrentDay>(MainActivity.this,android.R.layout.simple_list_item_1,currentDays));
        }
    }


}
