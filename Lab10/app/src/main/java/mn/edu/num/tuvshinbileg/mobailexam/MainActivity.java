package mn.edu.num.tuvshinbileg.mobailexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    public void goLogin(View view){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void goRegister(View view){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
