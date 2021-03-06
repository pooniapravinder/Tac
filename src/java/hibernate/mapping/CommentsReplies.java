package hibernate.mapping;
// Generated Aug 14, 2020 2:46:58 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * CommentsReplies generated by hbm2java
 */
public class CommentsReplies  implements java.io.Serializable {


     private long replyId;
     private Comments comments;
     private long repliedBy;
     private String reply;
     private long likes;
     private long replyTime;
     private long activityId;
     private Set commentsRepliesLikeses = new HashSet(0);

    public CommentsReplies() {
    }

	
    public CommentsReplies(Comments comments, long repliedBy, String reply, long likes, long replyTime, long activityId) {
        this.replyId = replyId;
        this.comments = comments;
        this.repliedBy = repliedBy;
        this.reply = reply;
        this.likes = likes;
        this.replyTime = replyTime;
       this.activityId = activityId;
    }
    public CommentsReplies(Comments comments, long repliedBy, String reply, long likes, long replyTime, long activityId, Set commentsRepliesLikeses) {
       this.replyId = replyId;
       this.comments = comments;
       this.repliedBy = repliedBy;
       this.reply = reply;
       this.likes = likes;
       this.replyTime = replyTime;
       this.activityId = activityId;
       this.commentsRepliesLikeses = commentsRepliesLikeses;
    }
   
    public long getReplyId() {
        return this.replyId;
    }
    
    public void setReplyId(long replyId) {
        this.replyId = replyId;
    }
    public Comments getComments() {
        return this.comments;
    }
    
    public void setComments(Comments comments) {
        this.comments = comments;
    }
    public long getRepliedBy() {
        return this.repliedBy;
    }
    
    public void setRepliedBy(long repliedBy) {
        this.repliedBy = repliedBy;
    }
    public String getReply() {
        return this.reply;
    }
    
    public void setReply(String reply) {
        this.reply = reply;
    }
    public long getLikes() {
        return this.likes;
    }
    
    public void setLikes(long likes) {
        this.likes = likes;
    }
    public long getReplyTime() {
        return this.replyTime;
    }
    
    public void setReplyTime(long replyTime) {
        this.replyTime = replyTime;
    }
    public Set getCommentsRepliesLikeses() {
        return this.commentsRepliesLikeses;
    }
    
    public void setCommentsRepliesLikeses(Set commentsRepliesLikeses) {
        this.commentsRepliesLikeses = commentsRepliesLikeses;
    }
    public long getActivityId() {
        return this.activityId;
    }
    
    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }




}


