package mn.edu.num.tuvshinbileg.myexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import mn.edu.num.tuvshinbileg.myexam.model.User;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mRef;
    private String TAG="MAINACTIVITY";
    private ListView mList;
    private List<String> userList;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRef= FirebaseDatabase.getInstance().getReference();
        mList=(ListView) findViewById(R.id.myListview);
        userList=new ArrayList<>();
        final Intent intent=new Intent( this,ChatActivity.class);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectItem=(String) adapterView.getItemAtPosition(i);
                Log.i(TAG,"SELECTED:"+selectItem);
                intent.putExtra("info",selectItem);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        getContacts();
    }
    private void getContacts() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            StringBuilder queryResult = new StringBuilder("");
            while (cursor.moveToNext()){
                //  queryResult.append(cursor.getString(0)+" ,"  +cursor.getString(1)+"\n");
               User user=new User();
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                user.setUsername(name);
                Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                while (phoneCursor.moveToNext()) {
                    String phonenumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        user.setPhoneNumber(phonenumber);
                    Log.i(TAG, "id:" + id + ", name:" + name + ", phoneNumber:" + phonenumber);

                }
            String userInfo=user.getUsername()+ "," +user.getPhoneNumber();
                userList.add(userInfo);

            }
           adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,userList);
            mList.setAdapter(adapter);

        } else {
            Log.i(TAG, "No contacts in device");
        }

    }
/*

    public void goLogin(View view){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void goRegister(View view){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    public void addUser(View view){
        writeNewUser("Dorj","d1234");

    }
    public void writeNewUser(String username,String pass){
        User user=new User(username,pass);
        mRef.child("RegisterApp/Users/").child(username).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Sucesfullyy",Toast.LENGTH_SHORT);
            }
        });
    }
    public void deleteUser(String name){
        mRef.child("RegisterApp/Users/").child(name).removeValue();

    }
    public void update(String username,String pass){
        User user=new User(username,pass);
        mRef.child("RegisterApp/Users/").child(username).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Sucesfullyy",Toast.LENGTH_SHORT);
            }
        });
    }
    public void updateUser(View view){
        update("Dorj", "o1234");
    }
*/


}
