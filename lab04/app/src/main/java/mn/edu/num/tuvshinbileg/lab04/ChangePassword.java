package mn.edu.num.tuvshinbileg.lab04;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    private String oldPass;
    private EditText etOldpass;
    private EditText etNewPass;
    private EditText etRepeatPass;
    private String TAG="lab04.ChangePassword";
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        etOldpass=(EditText) findViewById(R.id.oldpass);
        etNewPass=(EditText) findViewById(R.id.newPass);
        etRepeatPass=(EditText) findViewById(R.id.newRepeatPass);
        oldPass=getIntent().getStringExtra("oldPass");
        userId= Integer.parseInt(getIntent().getStringExtra("userId"));
        Log.i(TAG,oldPass);

    }
    public void saveBtnPass(View view){
        Log.i(TAG,"onSavebtn");
        DatabaseHelper db=new DatabaseHelper(this);
        if(oldPass.matches(etOldpass.getText().toString())){
            if(etNewPass.getText().toString().matches(etRepeatPass.getText().toString())){
                if(db.updatePass(userId,etNewPass.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Password is successfuly update",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

}
