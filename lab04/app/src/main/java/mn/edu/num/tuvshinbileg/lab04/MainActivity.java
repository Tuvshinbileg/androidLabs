package mn.edu.num.tuvshinbileg.lab04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button btnLogin;
    private Button btnSign;
    private String TAG = "lab04";
    private String mname;
    private String mpass;
    private DatabaseHelper myDb;
    Stack<String> usernames;
    private String lastLoginUsername;
    private static final String pref_login="username";
    private  final String DefaultUnameValue="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        display(myDb.getAllUsers());
        username = (EditText) findViewById(R.id.etName);
        password = (EditText) findViewById(R.id.etPass);
        btnLogin = (Button) findViewById(R.id.mbtnLogin);
        btnSign = (Button) findViewById(R.id.mbtnSign);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLastlogin();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveLastlogin();
    }
    public void loadLastlogin(){
        SharedPreferences settings=getSharedPreferences(pref_login,Context.MODE_PRIVATE);
        String uname=settings.getString(pref_login,DefaultUnameValue);
        username.setText(uname);
    }

    public void saveLastlogin(){
        SharedPreferences settings=getSharedPreferences(pref_login, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();
        lastLoginUsername=username.getText().toString();
        editor.putString(pref_login,lastLoginUsername);
        editor.commit();

    }

    public void loginEvent(View view) {
        mname = username.getText().toString();
        mpass = password.getText().toString();
        Log.i(TAG, "Log button");
        if (mname.matches("") || mpass.matches("")) {
            Toast.makeText(getApplicationContext(), "username or password is null", Toast.LENGTH_LONG).show();
        } else {
            User usr = myDb.checkUserExist(mname, mpass);
            if (usr != null) {
                Log.i(TAG, "move to User info");
//sending user
                Intent i = new Intent(this, UserInfo.class);
                i.putExtra("getUser", usr);
                startActivity(i);

            } else {
                Toast.makeText(getApplicationContext(), "User doesnt exist", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void signEvent(View view) {
        Log.i(TAG, "Sign button");
        mname = username.getText().toString();
        mpass = password.getText().toString();
        if (mname.matches("") || mpass.matches("")) {

        } else {
            Intent i = new Intent(this, SignUp.class);
            i.putExtra("username", mname);
            i.putExtra("password",mpass);
            startActivity(i);

        }
    }

    public void display(ArrayList<User> a) {
        for (int i = 0; i < a.size(); i++) {
            Log.i("TAG", String.valueOf(a.get(i)));
        }
    }


}
