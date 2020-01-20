package mn.edu.num.tuvshinbileg.lab03;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    private TextView txtv1;
    private  TextView txtv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }


    public void btnOne(View view){
        Intent intent=new Intent();
        txtv1=(TextView) findViewById(R.id.secName) ;
        txtv2=(TextView) findViewById(R.id.secAge) ;
        String name=txtv1.getText().toString();
        String age=txtv2.getText().toString();
        intent.putExtra("active2","name: "+name +" age:"+age );
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void btnSecond(View view){
            finish();
    }


}
