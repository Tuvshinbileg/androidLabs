package mn.edu.num.tuvshinbileg.lab06.model;


public class Student extends Person {
    private String course;
    private String stuClass;
    private String avgPoint;
    public Student(String firstName, String lastName, String gender, int age) {
        super(firstName, lastName, gender, age);
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    public String getAvgPoint() {
        return avgPoint;
    }

    public void setAvgPoint(String avgPoint) {
        this.avgPoint = avgPoint;
    }
}
