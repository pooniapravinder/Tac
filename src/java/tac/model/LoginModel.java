package tac.model;

public class LoginModel {

    private String usernamePhone;
    private String password;

    public LoginModel() {
    }

    public LoginModel(String usernamePhone, String password) {
        this.usernamePhone = usernamePhone;
        this.password = password;
    }

    public String getUsernamePhone() {
        return this.usernamePhone;
    }

    public void setUsernamePhone(String usernamePhone) {
        this.usernamePhone = usernamePhone;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
