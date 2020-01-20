package mn.edu.num.tuvshinbileg.lab04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


public class UserInfo extends AppCompatActivity {
    private String TAG="lab04.UserInfo";
    private EditText etName;
    private EditText etAge;
    private EditText etPhonenumber;
    private DatePicker dpBirthday;
    private int userId;
    private String passedPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        User getUser=(User) getIntent().getSerializableExtra("getUser");
        Log.i(TAG,getUser.toString());
        //editText
        etName = (EditText) findViewById(R.id.etuName);
        etAge = (EditText) findViewById(R.id.etAge);
        etPhonenumber = (EditText) findViewById(R.id.etPhone);
        //dp
        dpBirthday = (DatePicker) findViewById(R.id.mDatepicker);
        etName.setText(getUser.getName());
        etAge.setText(String.valueOf(getUser.getAge()));
        etPhonenumber.setText(String.valueOf(getUser.getPhonenumber()));
      //split to dp intial value
        String []dateParts=getUser.getDate().split("-");
        dpBirthday.init(Integer.parseInt(dateParts[0]),Integer.parseInt(dateParts[1]),
                Integer.parseInt(dateParts[2]),null);
        this.userId=getUser.getId();
        passedPass=getUser.getPassword();


    }


    public void changeInfoBtn(View view) {

        int day = dpBirthday.getDayOfMonth();
        int month = dpBirthday.getMonth() + 1;
        int year = dpBirthday.getYear();
        String date=year+"-"+month+"-"+day;
        DatabaseHelper db=new DatabaseHelper(this);
        boolean check= db.update(userId, etName.getText().toString(),Integer.valueOf(etAge.getText().toString()),
               Integer.parseInt( etPhonenumber.getText().toString()),date);
        if(check){
            Toast.makeText(getApplicationContext(),"Succesfuly updated",Toast.LENGTH_LONG).show();
        }
    }


    public void changePassBtn(View view) {
        Log.i(TAG,"onChangeBtn");
        Intent i=new Intent(this,ChangePassword.class);
        i.putExtra("oldPass",passedPass);
        i.putExtra("userId",String.valueOf(userId));

        startActivity(i);

    }

}
