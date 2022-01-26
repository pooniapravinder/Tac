package tac.model;

import java.util.ArrayList;
import utils.URLConfig;

public class CommentsModel {

    private long commentId;
    private String profilePic;
    private long userId;
    private String userName;
    private String comment;
    private long likes;
    private long replies;
    private long postId;
    private long time;
    private boolean liked;
    private ArrayList<CommentsRepliesModel> commentsReplies;

    public CommentsModel(long commentId, String profilePic, long userId, String userName, String comment, long likes, long replies, long postId, long time, boolean liked) {
        this.commentId = commentId;
        this.profilePic = profilePic != null ? URLConfig.profile_photo_url + profilePic : null;
        this.userId = userId;
        this.userName = userName;
        this.comment = comment;
        this.likes = likes;
        this.replies = replies;
        this.postId = postId;
        this.time = time;
        this.liked = liked;
        this.commentsReplies = new ArrayList<>();
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
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

    public long getReplies() {
        return replies;
    }

    public void setReplies(long replies) {
        this.replies = replies;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
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

    public ArrayList<CommentsRepliesModel> getCommentsReplies() {
        return commentsReplies;
    }

    public void setCommentsReplies(ArrayList<CommentsRepliesModel> commentsReplies) {
        this.commentsReplies = commentsReplies;
}
}
