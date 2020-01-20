package mn.edu.num.tuvshinbileg.lab04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private EditText repeatPassword;
    private EditText age;
    private EditText sex;
    private EditText phonenumber;
    private DatePicker dp;
    private String TAG = "lab04.SignUp";
    private String oldPass;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //from main activitiy

        name = getIntent().getStringExtra("username");
       Log.i(TAG,name);
        oldPass = getIntent().getStringExtra("password");
     Log.i(TAG,"pass is"+oldPass);
      //

        repeatPassword = (EditText) findViewById(R.id.etRepeatPassword);
        age = (EditText) findViewById(R.id.etAgeSign);
        sex = (EditText) findViewById(R.id.etSexSign);
        phonenumber = (EditText) findViewById(R.id.etPhoneSign);
        dp=(DatePicker) findViewById(R.id.dpSign);

    }

    public void savebtn(View view) {
        DatabaseHelper db = new DatabaseHelper(this);
        User usr=db.checkUserExist(name, oldPass);
        int day = dp.getDayOfMonth();
        int month = dp.getMonth() + 1;
        int year = dp.getYear();
        String date=year+"-"+month+"-"+day;

        if (usr== null) {
            Log.i(TAG,"User doesnt exist");
            if (oldPass.matches(repeatPassword.getText().toString())) {
                Log.i(TAG,"password is equal");

            db.insertUsers(new User(0,name,repeatPassword.getText().toString(),
                        Integer.parseInt(phonenumber.getText().toString()),
                        Integer.parseInt(age.getText().toString()),
                        sex.getText().toString(),date
                        ));
            Toast.makeText(getApplicationContext(),"Succesfully" ,Toast.LENGTH_LONG).show();
            finish();
            }else {
                Toast.makeText(getApplicationContext(),"password's are'nt equal", Toast.LENGTH_LONG).show();
            }
        }else {
            Log.i(TAG,"User exist");
            Toast.makeText(getApplicationContext(),"User is exist",Toast.LENGTH_LONG ).show();
            finish();
         /*   boolean check=db.update(usr.getId(),name,Integer.parseInt(age.getText().toString()),
                    Integer.valueOf(phonenumber.getText().toString()),date);
            if(check){
                Toast.makeText(getApplicationContext(),"Succesfully updated",Toast.LENGTH_LONG).show();
            }else {
                Log.i(TAG,"Update is failed");
            }*/
        }
    }

    public void cancelbtn(View view) {
        finish();
    }


}
