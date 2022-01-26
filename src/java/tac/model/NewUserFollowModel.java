package tac.model;

import utils.URLConfig;

public class NewUserFollowModel {

    private long userId;
    private String userName;
    private boolean verified;
    private String fullName;
    private String profilePic;

    public NewUserFollowModel(long userId, String userName, boolean verified, String fullName, String profilePic) {
        this.userId = userId;
        this.userName = userName;
        this.verified = verified;
        this.fullName = fullName;
        this.profilePic = profilePic != null ? URLConfig.profile_photo_url + profilePic : null;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic != null ? URLConfig.profile_photo_url + profilePic : null;
    }
}
