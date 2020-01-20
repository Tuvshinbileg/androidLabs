package mn.edu.num.tuvshinbileg.myexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mn.edu.num.tuvshinbileg.myexam.model.User;

public class LoginActivity extends AppCompatActivity {
    private DatabaseReference mRef;
    private String TAG="LOGIN";
    private List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userList=new ArrayList<>();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mRef= FirebaseDatabase.getInstance().getReference().child("RegisterApp/Users/");
        if(mRef==null){
            Toast.makeText(this,"NULL REF",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"NOT REF",Toast.LENGTH_SHORT).show();
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> iterable=dataSnapshot.getChildren();
                    for(DataSnapshot data:iterable){
                        User user=data.getValue(User.class);
                        Log.d(TAG, user.toString());
                     //   userList.add(user);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.i("LOGIN","FAILED");
                    Log.w(TAG, "Failed to read value.", databaseError.toException());

                }
            });

        }
    }

    public void onLogin(View view){

    }
}
