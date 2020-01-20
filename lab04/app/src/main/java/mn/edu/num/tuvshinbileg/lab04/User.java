package mn.edu.num.tuvshinbileg.lab04;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String name;
    private String password;
    private int phonenumber;
    private int age;
    private String sex;
    private String date;

    public User(int id,String name, String password, int phonenumber, int age, String sex, String date) {
        this.id=id;
        this.name = name;
        this.password = password;
        this.phonenumber = phonenumber;
        this.age = age;
        this.sex = sex;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phonenumber=" + phonenumber +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
