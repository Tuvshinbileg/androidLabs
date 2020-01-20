package mn.edu.num.tuvshinbileg.mobailexam;

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

public class LoginActivity extends AppCompatActivity {
    private DatabaseReference mRef;
    private String TAG="LOGIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mRef= FirebaseDatabase.getInstance().getReference().child("Register/Users");
        if(mRef==null){
            Toast.makeText(this,"NULL REF",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"NOT REF",Toast.LENGTH_SHORT).show();

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Value is: " + value);
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
