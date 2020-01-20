package mn.edu.num.tuvshinbileg.lab06;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import mn.edu.num.tuvshinbileg.lab06.fragments.StudentFragment;
import mn.edu.num.tuvshinbileg.lab06.fragments.TeacherFragment;
import mn.edu.num.tuvshinbileg.lab06.model.Person;

public class MainActivity extends AppCompatActivity {
    RadioButton rdbtn1;
    RadioButton rdbtn2;
    RadioButton radioButton;
    RadioGroup radioGroup;
    EditText etFname;
    EditText etLastName;
    EditText etGender;
    EditText etAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                loadEt();
        radioGroup=(RadioGroup)findViewById(R.id.radioProf);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Log.i("Lab06.Main","Radion");
                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedId);
                Toast.makeText(getApplicationContext(),radioButton.getText(),Toast.LENGTH_SHORT).show();
                customFragment(String.valueOf(radioButton.getText()));
            }
        });
    }
    public void loadEt(){
        etFname=(EditText) findViewById(R.id.etFname);
        etLastName=(EditText) findViewById(R.id.etLname);
        etAge=(EditText) findViewById(R.id.etAge);
        etGender=(EditText) findViewById(R.id.etGender);
    }

    public void customFragment(String nameFragment){

        Bundle bundle=new Bundle();
        bundle.putSerializable("person",new Person(etFname.getText().toString(),etLastName.getText().toString(),
                etGender.getText().toString(),Integer.parseInt(etAge.getText().toString())));

        switch (nameFragment){
            case "Teacher":
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                TeacherFragment teacherFragment=new TeacherFragment();
                teacherFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.containerFrag2,teacherFragment);
                fragmentTransaction.commit();
                break;

            case "Student":
                FragmentManager fragmentManager1=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1=fragmentManager1.beginTransaction();
                StudentFragment studentFragment=new StudentFragment();
                studentFragment.setArguments(bundle);
                fragmentTransaction1.replace(R.id.containerFrag2, studentFragment);
                fragmentTransaction1.commit();
                break;
        }
    }

}
