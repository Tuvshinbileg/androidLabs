package mn.edu.num.tuvshinbileg.lab06.model;

public class Teacher extends  Person{
    private String degree;
    private String subjects;
    public Teacher(String firstName, String lastName, String gender, int age,String degree,String subjects) {
        super(firstName, lastName, gender, age);
        setDegree(degree);
        setSubjects(subjects);
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }
}
