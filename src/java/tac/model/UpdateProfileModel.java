package tac.model;

public class UpdateProfileModel {

    private String username;
    private String fullname;
    private String bio;

    public UpdateProfileModel() {
    }

    public UpdateProfileModel(String username, String fullname, String bio) {
        this.username = username;
        this.fullname = fullname;
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
