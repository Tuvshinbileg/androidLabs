package mn.edu.num.tuvshinbileg.lab02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button addBtn;
    private Button substractBtn;
    private Button multiplyBtn;
    private Button divideBtn;
    private EditText etValA;
    private EditText etValB;
    private TextView etResult;
    private TextView mMenu;
    private String regex = "^[0-9]*$";
    private static final String TAG = "tuvshinbileg.lab02";

    /*
     * dp=Utasni delgetsnees hamaaralgui hemjees*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab2layout);
        Log.i(TAG, "on Create running");
        mMenu = (TextView) findViewById(R.id.menu);
        addBtn = (Button) findViewById(R.id.btAdd);
        substractBtn = (Button) findViewById(R.id.btSub);
        multiplyBtn = (Button) findViewById(R.id.btMulti);
        divideBtn = (Button) findViewById(R.id.btDivide);
        etValA = (EditText) findViewById(R.id.etValueA);
        etValB = (EditText) findViewById(R.id.etValueB);
        etResult = (EditText) findViewById(R.id.etResult);
        //registerForContextMenu(mMenu);

    }
    /*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
       Log.i(TAG,"OnCreateContextMenu");

        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0,1,0,"Add");
        menu.add(0,2,0,"Substraction");
        menu.add(0,3,0,"Multiply");
        menu.add(0,4,0,"Divide");

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdd:
                Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                String add = "result:" + " Valua A:" + etValA.getText().toString() + "+" + "Valua B:" + etValB.getText().toString();
                Log.i(TAG, add);
                add();
                return true;
            case R.id.menuSub:
                Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                String sub = "result:" + " Valua A:" + etValA.getText().toString() + "-" + "Valua B:" + etValB.getText().toString();
                Log.i(TAG, sub);
                substract();
                return true;
            case R.id.menuMulti:
                Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                String mult = "result:" + " Valua A:" + etValA.getText().toString() + "*" + "Valua B:" + etValB.getText().toString();
                Log.i(TAG, mult);
                multiply();
                return true;
            case R.id.menuDivide:
                Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                String div = "result:" + " Valua A:" + etValA.getText().toString() + "/" + "Valua B:" + etValB.getText().toString();
                Log.i(TAG, div);
                divide();
                return true;
            default:
                return super.onContextItemSelected(item);

        }

    }

    public void addEvent(View view) {
        add();
    }

    public void add() {
        Log.i(TAG, "on add Function");
        if (etValA.getText().toString().matches(regex) && etValB.getText().toString().matches(regex)) {
            if (etValA.getText().toString().length() == 0 || etValB.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Value A or B is null", Toast.LENGTH_LONG).show();
            } else {
                Log.i(TAG, "adding Value A:" + etValA.getText().toString() + " and Value B:" + etValB.getText().toString());
                int result = Integer.parseInt(etValA.getText().toString()) + Integer.parseInt(etValB.getText().toString());
                etResult.setText(String.valueOf(result));
            }
        } else {
            Log.i(TAG, "Values not number");
            Toast.makeText(getApplicationContext(), "Values  must be number ", Toast.LENGTH_LONG).show();
        }
    }

    public void substractEvent(View view) {
        substract();
    }

    public void substract() {
        Log.i(TAG, "on substract Function");
        if (etValA.getText().toString().matches(regex) && etValB.getText().toString().matches(regex)) {
            if (etValA.getText().toString().length() == 0 || etValB.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Value A or B is null", Toast.LENGTH_LONG).show();
            } else {
                int result = Integer.parseInt(etValA.getText().toString()) - Integer.parseInt(etValB.getText().toString());
                Log.i(TAG, "substract Value A:" + etValA.getText().toString() + " and Value B:" + etValB.getText().toString());
                etResult.setText(String.valueOf(result));
            }
        } else {

            Log.i(TAG, "Values not number");
            Toast.makeText(getApplicationContext(), "Values  must be number ", Toast.LENGTH_LONG).show();
        }
    }

    public void multiplyEvent(View view) {
        multiply();
    }

    public void multiply() {
        Log.i(TAG, "on multiply Function");
        if (etValA.getText().toString().matches(regex) && etValB.getText().toString().matches(regex)) {
            if (etValA.getText().toString().length() == 0 || etValB.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Value A or B is null", Toast.LENGTH_LONG).show();
            } else {
                int result = Integer.parseInt(etValA.getText().toString()) * Integer.parseInt(etValB.getText().toString());

                Log.i(TAG, "multiply Value A:" + etValA.getText().toString() + " and Value B:" + etValB.getText().toString());
                etResult.setText(String.valueOf(result));
            }
        } else {

            Log.i(TAG, "Values not number");
            Toast.makeText(getApplicationContext(), "Values  must be number ", Toast.LENGTH_LONG).show();
        }
    }


    public void divideEvent(View view) {
        divide();
    }

    public void divide() {
        float result = 0;
        Log.i(TAG, "on divide Function");

        if (etValB.getText().toString().matches(this.regex) && etValA.getText().toString().matches(this.regex)) {
            if (etValA.getText().toString().length() == 0 || etValB.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Value A or B is null", Toast.LENGTH_LONG).show();
            } else {
                try {
                    result = Float.parseFloat(etValA.getText().toString()) / dividingZero(Float.parseFloat(etValB.getText().toString()));
                } catch (Exception e) {

                    Log.i(TAG, "dividing Value A:" + etValA.getText().toString() + " and Value B:" + etValB.getText().toString());
                    Toast.makeText(getApplicationContext(), "Dividing zero pls", Toast.LENGTH_LONG).show();
                }

                etResult.setText(String.valueOf(result));

            }
        } else {
            Log.i(TAG, "Values not number");
            Toast.makeText(getApplicationContext(), "Values  must be number ", Toast.LENGTH_LONG).show();
        }
    }

    public float dividingZero(float num) {
        if (num == 0) {
            Toast.makeText(getApplicationContext(), "dividing num is zero", Toast.LENGTH_LONG).show();
        }
        return num;
    }

    /*
        @Override
        public boolean onContextItemSelected(MenuItem item) {
            Log.i(TAG, "onContextItemSelected");
            Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();

            switch (item.getItemId()) {
                case 1:
                    Log.i("Example", "Add item clicked");
                    return true;
                case R.id.menuSub:

                    Log.i(TAG, "substract item clicked");
                    return true;
                case R.id.menuMulti:

                    Log.i("Example", "Multi item clicked");
                    return true;
                case R.id.menuDivide:

                    Log.i("Example", "divide item clicked");
                    return true;
                default:
                    return super.onContextItemSelected(item);

            }
        }

      */

}
