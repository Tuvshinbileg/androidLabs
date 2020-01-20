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
import mn.edu.num.tuvshinbileg.lab06.model.Teacher;

public class TeacherFragment extends Fragment {
    TextView alltxt;
    EditText etDegree;
    EditText etSubjects;
    Person person;
    Button btnSubmit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView=inflater.inflate(R.layout.teacher_fragment, container, false);
       alltxt=(TextView) rootView.findViewById(R.id.allTxtPre);
       etDegree=(EditText) rootView.findViewById(R.id.etDegree);
       etSubjects=(EditText) rootView.findViewById(R.id.etSubjects);
       btnSubmit=(Button) rootView.findViewById(R.id.btnTeach);
       btnSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               btnTeach();
           }
       });
       Bundle bundle=getArguments();
         person= (Person) bundle.getSerializable("person");
        Log.i("TeacherFragment",person.toString());
            alltxt.setText(person.toString());
        return rootView ;
    }

    public void btnTeach( ){
        if(etDegree.getText().toString().length()!=0||etSubjects.getText().toString().length()!=0) {
            String degree = etDegree.getText().toString();
            String subjects = etSubjects.getText().toString();
            Teacher teacher = new Teacher(person.getFirstName(), person.getLastName(), person.getGender(), person.getAge(), degree, subjects);
        }else{
            Toast.makeText(getContext(),"Null",Toast.LENGTH_LONG).show();
        }
    }

}
