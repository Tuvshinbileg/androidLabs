package mn.edu.num.tuvshinbileg.lab06.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import mn.edu.num.tuvshinbileg.lab06.R;
import mn.edu.num.tuvshinbileg.lab06.model.Person;
import mn.edu.num.tuvshinbileg.lab06.model.Student;


 public class StudentFragment extends Fragment {
    TextView allText;
    EditText etClass;
    EditText etCourse;
    EditText etAvg;
    Button myBtn;
    Person person;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.student_fragment, container, false);
        allText=(TextView) rootView.findViewById(R.id.frag2Alltxt);
        etClass=(EditText) rootView.findViewById(R.id.etClass);
        etCourse=(EditText) rootView.findViewById(R.id.etCourse);
        etAvg=(EditText) rootView.findViewById(R.id.etAvaragePoint);
        myBtn=(Button) rootView.findViewById(R.id.btnSt);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStdu();
            }
        });
        Bundle bundle=getArguments();
        person= (Person) bundle.getSerializable("person");
        Log.i("StudentFragment",person.toString());
        allText.setText(person.toString());
        return rootView;
    }
    public void btnStdu(){
        if(!isNull()) {
            String stuClass = etClass.getText().toString();
            String course = etCourse.getText().toString();
            String avg = etAvg.getText().toString();
            Student student = new Student(person.getFirstName(), person.getLastName(), person.getGender(), person.getAge());
            student.setCourse(course);
            student.setAvgPoint(avg);
            student.setStuClass(stuClass);
        }else{
            Toast.makeText(getContext(), "Null", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isNull(){
        return etClass.getText().toString().length()==0||etCourse.getText().toString().length()==0||etAvg.getText().toString().length()==0;
    }

}
