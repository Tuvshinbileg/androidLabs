package mn.edu.num.tuvshinbileg.lab05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = SecondActivity.class.getSimpleName();


    private Uri CONTENT_URI_1 = Uri.parse
            ("content://mn.edu.num.tuvshinbileg.lab04/TODO_LIST");
    private EditText etName;
    private EditText etAge;
    private EditText etPhonenumber;
    private DatePicker dpBirthday;
    private EditText etSex;
    private EditText etPass;

    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "PHONENUMBER";
    public static final String COL_5 = "AGE";
    public static final String COL_6 = "SEX";
    public static final String COL_7 = "BIRTHDAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setContentView(R.layout.activity_main);
        etName=(EditText)findViewById(R.id.etLoginName);
        etPass=(EditText)findViewById(R.id.etPass);
        etPhonenumber=(EditText)findViewById(R.id.etPnumber);
        etSex=(EditText) findViewById(R.id.etSex);
        etAge=(EditText) findViewById(R.id.etAge);
        dpBirthday=(DatePicker) findViewById(R.id.dpBday);
    }
    public void insertEvent(View view){

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
    }

}
