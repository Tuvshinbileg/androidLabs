package mn.edu.num.tuvshinbileg.lab05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.app.TaskInfo;
import android.app.VoiceInteractor;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.sql.Date;

public class MainActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etAge;
    private EditText etPhonenumber;
    private DatePicker dpBirthday;
    private EditText etSex;
    private EditText etPass;
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "PHONENUMBER";
    public static final String COL_5 = "AGE";
    public static final String COL_6 = "SEX";
    public static final String COL_7 = "BIRTHDAY";


    private static final String TAG = MainActivity.class.getSimpleName();
    private Uri CONTENT_URI_1 = Uri.parse
            ("content://mn.edu.num.tuvshinbileg.lab04/TODO_LIST");
    private Uri CONTENT_URI_2 = Uri.parse
            ("content://mn.edu.num.tuvshinbileg.lab04/TODO_LIST_FROM_PLACE");
    private Uri CONTENT_URI_3 = Uri.parse
            ("content://mn.edu.num.tuvshinbileg.lab04/TODOS_COUNT");

    private ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        etName=(EditText)findViewById(R.id.etLoginName);
        etPass=(EditText)findViewById(R.id.etPass);
        etPhonenumber=(EditText)findViewById(R.id.etPnumber);
        etSex=(EditText) findViewById(R.id.etSex);
        etAge=(EditText) findViewById(R.id.etAge);
        dpBirthday=(DatePicker) findViewById(R.id.dpBday);
        Log.i(TAG,"Main");
        getAllToDo();

    }

    private void getAllToDo(){

        resolver = getContentResolver();

        Cursor cursor=resolver.query(CONTENT_URI_1,null,null,null,null);

        StringBuilder stringBuilder=new StringBuilder("");
        Log.i(TAG,"cursor count: "+cursor.getCount());
        if(cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){
                stringBuilder.append(cursor.getString(0)+", "+cursor.getString(1)+", "+cursor.getString(2)
                    +", "+cursor.getString(3)+","+cursor.getString(4)+","+cursor.getString(5)+","+cursor.getString(6)    +"\n");
                Log.i(TAG, "getAllToDo: "+stringBuilder);
            }
        }
        cursor.close();

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

            while (cursor.moveToNext()) {
                //  queryResult.append(cursor.getString(0)+" ,"  +cursor.getString(1)+"\n");
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                while (phoneCursor.moveToNext()) {
                    String phonenumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.i(TAG, "id:" + id + ", name:" + name + ", phoneNumber:" + phonenumber);
                }
            }
            //  Log.i(TAG,queryResult.toString());
        } else {
            Log.i(TAG, "No contacts in device");
        }

    }

    private void getCalendarEvent() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, 1);
        }
        String[] mProjection =
                {
                        "_id",
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.EVENT_LOCATION,
                        CalendarContract.Events.DTSTART,
                        CalendarContract.Events.DTEND,
                };

        ContentResolver resolver = getContentResolver();
        Uri uri = CalendarContract.Events.CONTENT_URI;
        Cursor cursor = resolver.query(uri, null, null, null, null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
            String startDate = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
            Log.i(TAG, "Title:" + title);
        }


    }

    public void btnContactEvent(View view) {
        getContacts();
    }

    public void btnCalendarEvent(View view) {
        getCalendarEvent();
    }
    public void btnNext(View view){
        int day = dpBirthday.getDayOfMonth();
        int month = dpBirthday.getMonth() + 1;
        int year = dpBirthday.getYear();
        String date=year+"-"+month+"-"+day;

        User usr=new User(1,
                etName.getText().toString(),etPass.getText().toString(),
                Integer.parseInt(etPhonenumber.getText().toString()),
                Integer.parseInt(etAge.getText().toString()),etSex.getText().toString(),date
        );
        ContentValues values=new ContentValues();
        values.put(COL_2, usr.getName());
        values.put(COL_3, usr.getPassword());
        values.put(COL_4, usr.getPhonenumber());
        values.put(COL_5, usr.getAge());
        values.put(COL_6, usr.getSex().toLowerCase());
        values.put(COL_7, usr.getDate());
        ContentResolver resolver=getContentResolver();
        Uri myUri=null;
        myUri=resolver.insert(CONTENT_URI_1,values);
        Log.i(TAG,"returned uri:"+myUri);
    };
    public void btnDelete(View view){
        ContentResolver resolver=getContentResolver();
        String whereClause = COL_1+"=?";
        String[] whereArgs=new String[]{"11"};
        int i=  resolver.delete(CONTENT_URI_1,whereClause,whereArgs);
              Log.i(TAG,"Return num:"+i);
    }

}
