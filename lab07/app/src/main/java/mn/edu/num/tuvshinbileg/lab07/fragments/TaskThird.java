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
import mn.edu.num.tuvshinbileg.lab07.ThreadTask.WeatherTask3;
import mn.edu.num.tuvshinbileg.lab07.model.CurrentDay;

public class TaskThird extends Fragment {

    ListView listView;
    String urlInterval = "https://api.openweathermap.org/data/2.5/forecast?q=Ulaanbaatar,mn&mode=json&APPID=ef6cf6ee239b68a30f6ea77134043d5a";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment3, container, false);
        listView =rootView.findViewById(R.id.listThird);
        try {
            List<CurrentDay> weathers=new WeatherTask3().execute(urlInterval).get();
            ArrayAdapter<CurrentDay> adapter=new ArrayAdapter<CurrentDay>(getActivity(),android.R.layout.simple_list_item_1,weathers);
           listView.setAdapter(adapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rootView;
    }
}
