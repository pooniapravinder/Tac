package tac.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import utils.URLConfig;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private long id;
    private String fullName;
    private String userName;
    private String profilePic;
    private boolean verified;

    public User(long id, String fullName, String userName, String profilePic, boolean verified) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.profilePic = profilePic != null ? profilePic : null;
        this.verified = verified;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic != null ? profilePic : null;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
