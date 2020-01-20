package mn.edu.num.tuvshinbileg.lab07.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.concurrent.ExecutionException;

import mn.edu.num.tuvshinbileg.lab07.R;
import mn.edu.num.tuvshinbileg.lab07.ThreadTask.WeatherTask2;
import mn.edu.num.tuvshinbileg.lab07.model.Entry;
import mn.edu.num.tuvshinbileg.lab07.model.Weather;

public class TaskSecond  extends Fragment {
    ListView listView;
    String urlSeveralCities = "https://api.openweathermap.org/data/2.5/group?id=524901,703448,2643743&units=metric&APPID=ef6cf6ee239b68a30f6ea77134043d5a";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment2, container, false);
        listView =rootView.findViewById(R.id.listSecond);
        try {
            List<Weather> weathers=new WeatherTask2().execute(urlSeveralCities).get();
            ArrayAdapter<Weather> adapter =new ArrayAdapter<Weather>(getActivity(), android.R.layout.simple_list_item_1,weathers);
            listView.setAdapter(adapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rootView;
    }
}
