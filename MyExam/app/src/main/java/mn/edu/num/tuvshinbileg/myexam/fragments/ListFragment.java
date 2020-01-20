package mn.edu.num.tuvshinbileg.myexam.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mn.edu.num.tuvshinbileg.myexam.R;
import mn.edu.num.tuvshinbileg.myexam.model.User;

public class ListFragment extends Fragment {
    private DatabaseReference mRef;
    private ListView mList;
    private ArrayAdapter adapter;
    private List<User> userList;
    private static String TAG="ListFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_list, container, false);
        mList=(ListView) rootView.findViewById(R.id.myListview);
        userList= new ArrayList<User>();
        //fragment bundle
        // Bundle bundle=getArguments();
        return rootView;
    }

   @Override
    public void onStart() {
        super.onStart();
        mRef= FirebaseDatabase.getInstance().getReference().child("RegisterApp/Users/");
        if(mRef==null){
            Toast.makeText(getActivity(),"NULL REF",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),"NOT REF",Toast.LENGTH_SHORT).show();
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> iterable=dataSnapshot.getChildren();
                    for(DataSnapshot data:iterable){
                        String key=dataSnapshot.getKey();
                        Log.i(TAG, ""+key);
                        User user=data.getValue(User.class);
                        Log.d(TAG, user.toString());
                        userList.add(user);
                    }
                    adapter=new ArrayAdapter<User>(getActivity(),android.R.layout.simple_list_item_1,userList);
                    mList.setAdapter(adapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Log.i("LOGIN","FAILED");
                    Log.w(TAG, "Failed to read value.", databaseError.toException());

                }
            });

        }
    }
}
