package mn.edu.num.tuvshinbileg.myexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mn.edu.num.tuvshinbileg.myexam.model.User;

public class ChatActivity extends AppCompatActivity {
    String userInfo="";
    EditText etContact;
    private DatabaseReference mRef;
    private String TAG="MessageActivity";
    private String ownNumber="99481499";
    String [] arr;
    EditText etMessage;
    EditText etInput;
    String ownOldValue="";
    String [] msgData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        etContact=(EditText) findViewById(R.id.etContact);
        etMessage=(EditText) findViewById(R.id.etMessage);
        etInput=(EditText) findViewById(R.id.etInput);
        Bundle p = getIntent().getExtras();
        userInfo =p.getString("info");
      arr =userInfo.split(",");
        etContact.setText(arr[1]);

        workingChat();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    public void workingChat(){
        mRef= FirebaseDatabase.getInstance().getReference().child("RegisterApp/Messages/");
        if(mRef==null){
            Toast.makeText(this,"NULL REF",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"NOT REF",Toast.LENGTH_SHORT).show();
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> iterable=dataSnapshot.getChildren();
                    for(DataSnapshot data:iterable){
                        String key=data.getKey();
                        Log.d(TAG, "key:"+key);
                        String msg=data.getValue(String.class);
                        if(key.equals(ownNumber)){

                            msgData=msg.split(",");
                           for(int i=0; i<msgData.length; i++) {
                               etMessage.append("\n");
                               etMessage.append(ownNumber+":"+msgData[i]);
                           }

                         //   ownOldValue=msg;
                        }
                        if(key.equals(arr[1])){
                            etMessage.append("\n");
                            etMessage.append(arr[1]+":"+msg);

                        }
                        Log.d(TAG, "Message:"+msg);
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
    public void onSend(View view){
    String inputMsg=etInput.getText().toString();
    etMessage.append("\n");
    etMessage.append(ownNumber+":"+inputMsg);
    insertDb(inputMsg);
    }
    public void insertDb(final String msg){
        StringBuilder sb1=new StringBuilder();

     for(int i=0; i<msgData.length; i++){
            sb1.append(msgData[i]);
            sb1.append(",");
     }
     sb1.append(msg);
        mRef.child(ownNumber).setValue(sb1.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Sucesfullyy",Toast.LENGTH_SHORT);
                Log.d(TAG,"Succesfully inserted");
                etMessage.setText("");
                workingChat();
            }

        });
    }


}
