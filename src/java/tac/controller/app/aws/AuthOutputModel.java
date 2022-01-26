package tac.controller.app.aws;

public class AuthOutputModel {

    private int id;
    private String auth;
    private String key;

    public AuthOutputModel() {

    }

    public AuthOutputModel(int id, String auth, String key) {
        this.id = id;
        this.auth = auth;
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
