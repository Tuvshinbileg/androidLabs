package mn.edu.num.tuvshinbileg.myexam.model;

public class User {
    public String username;
    public String phoneNumber;
    public User(){

    }

    public User(String username, String phoneNumber) {
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + phoneNumber + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String password) {
        this.phoneNumber = password;
    }
}
