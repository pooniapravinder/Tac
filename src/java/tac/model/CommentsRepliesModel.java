package tac.model;

import utils.URLConfig;

public class CommentsRepliesModel {

    private long replyId;
    private String profilePic;
    private long userId;
    private String userName;
    private String comment;
    private long likes;
    private long commentId;
    private long time;
    private boolean liked;

    public CommentsRepliesModel(long replyId, String profilePic, long userId, String userName, String comment, long likes, long commentId, long time, boolean liked) {
        this.replyId = replyId;
        this.profilePic = profilePic != null ? URLConfig.profile_photo_url + profilePic : null;
        this.userId = userId;
        this.userName = userName;
        this.comment = comment;
        this.likes = likes;
        this.commentId = commentId;
        this.time = time;
        this.liked = liked;
    }

    public long getReplyId() {
        return replyId;
    }

    public void setReplyId(long replyId) {
        this.replyId = replyId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
