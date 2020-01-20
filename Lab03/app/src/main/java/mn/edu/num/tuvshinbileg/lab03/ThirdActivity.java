package mn.edu.num.tuvshinbileg.lab03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    Boolean val1=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        checkBox1=(CheckBox)findViewById(R.id.checkbox1);
        checkBox2=(CheckBox)findViewById(R.id.checkbox2);
        checkBox3=(CheckBox)findViewById(R.id.checkbox3);
      String msg=getIntent().getStringExtra("back2");
      if(checkBox1.getText().toString().equals(msg)){
          checkBox1.setChecked(true);
      }else if(checkBox2.getText().toString().equals(msg)){
          checkBox2.setChecked(true);
      }else if(checkBox3.getText().toString().equals(msg)){
          checkBox3.setChecked(true);
      }
    }


    public void btnEvent(View view){

        if(checkBox1.isChecked()){
            Intent intent=new Intent();
            intent.putExtra("checkbox1",checkBox1.getText().toString() );
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else if(checkBox2.isChecked()){
            Intent intent=new Intent();
            intent.putExtra("checkbox1",checkBox2.getText().toString() );
            setResult(Activity.RESULT_OK,intent);
            finish();

        }else if(checkBox3.isChecked()){
            Intent intent=new Intent();
            intent.putExtra("checkbox1",checkBox3.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else {
            finish();
        }

    }
    public void cancelThirdEvent(View view){
        finish();
    }

}
