package tac.model;

import utils.URLConfig;

public class ProfileModel {

    private long userId;
    private String userName;
    private boolean verified;
    private String fullName;
    private long following;
    private long followers;
    private long likes;
    private String profilePic;
    private byte gender;
    private String dob;
    private String bio;

    public ProfileModel(long userId, String userName, boolean verified, String fullName, long following, long followers, long likes, String profilePic, byte gender, String dob, String bio) {
        this.userId = userId;
        this.userName = userName;
        this.verified = verified;
        this.fullName = fullName;
        this.following = following;
        this.followers = followers;
        this.likes = likes;
        this.profilePic = profilePic != null ? URLConfig.profile_photo_url + profilePic : null;
        this.gender = gender;
        this.dob = dob;
        this.bio = bio;
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

    public long getFollowing() {
        return following;
    }

    public void setFollowing(long following) {
        this.following = following;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic != null ? URLConfig.profile_photo_url + profilePic : null;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public class ExtraData {

        private boolean following;
        private boolean followedBy;

        public ExtraData() {
        }

        public ExtraData(boolean following, boolean followedBy) {
            this.following = following;
            this.followedBy = followedBy;
        }

        public boolean isFollowing() {
            return this.following;
        }

        public void setFollowing(boolean following) {
            this.following = following;
        }

        public boolean isFollowedBy() {
            return this.followedBy;
        }

        public void setFollowedBy(boolean followedBy) {
            this.followedBy = followedBy;
        }
    }
}
