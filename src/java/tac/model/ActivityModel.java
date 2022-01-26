package tac.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import utils.ActivityTypeConstants;

@JsonInclude(Include.NON_NULL)
public class ActivityModel {

    private long activityId;
    private byte type;
    private String typeName;
    private boolean isFollowing;
    private long timestamp;
    private User user;
    private Media media;

    public ActivityModel(long activityId, byte type, boolean isFollowing, long timestamp, long id, String fullName, String userName, String profilePic, boolean verified, Long postId, String thumbnail) {
        this.activityId = activityId;
        this.type = type;
        this.typeName = ActivityTypeConstants.getTypeName(type);
        this.isFollowing = isFollowing;
        this.timestamp = timestamp;
        this.user = new User(id, fullName, userName, profilePic, verified);
        this.media = postId != null ? new Media(postId, thumbnail) : null;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
