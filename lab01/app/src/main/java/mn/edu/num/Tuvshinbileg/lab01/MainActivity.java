package mn.edu.num.Tuvshinbileg.lab01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView  mTextView;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView=(TextView) findViewById(R.id.myTextView);
        mButton=(Button) findViewById(R.id.myButton);
        mTextView.setText("Programming in Android");
        System.out.println(mTextView.getText());

         mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("wowowow");

                Toast.makeText(getApplicationContext(), "Hello Toast", Toast.LENGTH_LONG).show();
            }
        });

    }
}
