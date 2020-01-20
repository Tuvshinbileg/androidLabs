package mn.edu.num.tuvshinbileg.lab03;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etValue;
    private Boolean returned=false;
    private Boolean returned1=false;
    private Boolean returned2=false;
    private Boolean returned3=false;
    private String TAG="lab03";
    private static final int CODE_GETMESSAGE=0001;
    String val1;
    String val2;
    String val3;
    String val4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etValue=(EditText) findViewById(R.id.myEditText);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.secondAct:
                secondActivity();
                return true;
            case R.id.thirdAct:
                thirdActivity();
                return true;
            case R.id.fourthAct:
                fourthActivity();
                return true;
            case R.id.fifthAct:
                fifthActivity();
                return true;
            default:
                return super.onContextItemSelected(item);

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case CODE_GETMESSAGE:
                if(resultCode== Activity.RESULT_OK){
                        String message = data.getStringExtra("active2");
                        if(message==null) {
                        Log.i(TAG,"Result is null");
                        }else {
                            returned = true;
                            val1 = message;
                            etValue.setText(val1);
                            Log.i(TAG, "Result message:" + message);
                        }
                }
            case 0002:
                if(resultCode==Activity.RESULT_OK){
                        String message1 = data.getStringExtra("checkbox1");
                        if(message1==null){
                        Log.i(TAG,"Result is empty");
                        }else {
                            returned1=true;
                            val2 = message1;
                            etValue.setText(message1);
                            Log.i(TAG,"Result message:"+message1);
                        }

                }

            case 0003:
                if(resultCode==Activity.RESULT_OK){
                    String message3=data.getStringExtra("rdb");
                    if(message3==null){
                        Log.i(TAG,"Result is empty");
                    }else {
                        returned2=true;
                        val3=message3;
                        etValue.setText(message3);
                        Log.i(TAG,"Result message:"+message3);
                    }
                    Log.i(TAG,"Result message:"+message3);

                }

            case 0004:
                if(resultCode==Activity.RESULT_OK){
                    String message=data.getStringExtra("active5");
                    Log.i(TAG,"Result message:"+message);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void btnOneEvent(View view) {
        if(returned) {
            Intent i=new Intent(this,SecondActivity.class);
            i.putExtra("back",val1);
           startActivityForResult(i,CODE_GETMESSAGE);
        }else {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivityForResult(intent,CODE_GETMESSAGE);

        }
    }

    public void btnSecondEvent(View view) {
        if(returned1){
            Intent intent = new Intent(this, ThirdActivity.class);
            intent.putExtra("back2",val2);
            startActivityForResult(intent,0002);

        }else{
            Intent intent = new Intent(this, ThirdActivity.class);
            startActivityForResult(intent,0002);

        }

    }

    public void btnThirdEvent(View view) {
      if(returned2){
          Intent intent = new Intent(this, FourthActivity.class);
          intent.putExtra("back3",val3);
          startActivityForResult(intent,0003);
          finish();
      }else {
          Intent intent = new Intent(this, FourthActivity.class);
          startActivityForResult(intent,0003);
      }
    }


    public void btnFourthEvent(View view) {
        if(returned3) {

            Intent intent = new Intent(this, FifthActivity.class);
            intent.putExtra("back2",val3);
           finish();
        }else {
            Intent intent = new Intent(this, FifthActivity.class);
            startActivityForResult(intent,0004);
        }
    }

    public void secondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void thirdActivity() {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

    public void fourthActivity() {
        Intent intent = new Intent(this, FourthActivity.class);
        startActivity(intent);
    }

    public void fifthActivity() {
        Intent intent = new Intent(this, FifthActivity.class);
        startActivity(intent);
    }
}
