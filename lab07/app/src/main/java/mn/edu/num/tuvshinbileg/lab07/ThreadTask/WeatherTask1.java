package mn.edu.num.tuvshinbileg.lab07.ThreadTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
import java.util.List;

import mn.edu.num.tuvshinbileg.lab07.MainActivity;
import mn.edu.num.tuvshinbileg.lab07.R;
import mn.edu.num.tuvshinbileg.lab07.model.Entry;
//Must be Xml Parsing

public class WeatherTask1 extends AsyncTask<String, Void, List<Entry>> {

    private BufferedReader reader;
    private static final String ns = null;
    private List<Entry> entries;
    private ListView listView;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(List<Entry> entries) {
        super.onPostExecute(entries);

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
