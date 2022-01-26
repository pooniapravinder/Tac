package tac.model;

public class SignupModel {

    private String phone;
    private String username;
    private String fullname;
    private String gender;
    private String password;

    public SignupModel() {
    }

    public SignupModel(String phone, String username, String fullname, String gender, String password) {
        this.phone = phone;
        this.username = username;
        this.fullname = fullname;
        this.gender = gender;
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
