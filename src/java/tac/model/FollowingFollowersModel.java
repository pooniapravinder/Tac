package tac.model;

import utils.URLConfig;

public class FollowingFollowersModel {

    private long userId;
    private String userName;
    private String fullName;
    private String profilePic;
    private boolean following;

    public FollowingFollowersModel(long userId, String userName, String fullName, String profilePic, boolean following) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.profilePic = profilePic != null ? URLConfig.profile_photo_url + profilePic : null;
        this.following = following;
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

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

}
