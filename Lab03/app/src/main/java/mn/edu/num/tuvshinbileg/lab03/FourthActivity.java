package mn.edu.num.tuvshinbileg.lab03;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FourthActivity extends AppCompatActivity {
    private RadioButton myrdb1;
    private RadioButton myrdb2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        myrdb1=(RadioButton) findViewById(R.id.firstRdb);
        myrdb2=(RadioButton) findViewById(R.id.secondRdb);

        String msg=getIntent().getStringExtra("back3");
        if(myrdb1.getText().toString().equals(msg)){
            myrdb1.setChecked(true);
        }
        if(myrdb2.getText().toString().equals(msg)){
            myrdb2.setChecked(true);
        }

    }
    public void fourthOK(View view){
            if(myrdb1.isChecked()){
                Intent intent=new Intent();
                intent.putExtra("rdb",myrdb1.getText().toString() );
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
            if(myrdb2.isChecked()){
                Intent intent=new Intent();
                intent.putExtra("rdb",myrdb2.getText().toString() );
                setResult(Activity.RESULT_OK,intent);
                finish();
            }


    }
    public  void fourthCancel(View view){
        finish();

    }


}
