package utils;

public class ActivityTypeConstants {

    public static final byte FOLLOW_USER = 1;
    public static final byte LIKE_POST = 2;
    public static final byte COMMENT_POST = 3;
    public static final byte LIKE_COMMENT = 4;
    public static final byte MENTION_COMMENT = 5;
    public static final byte MENTION_POST = 6;
    public static final byte LIKE_COMMENT_REPLY = 7;

    public static final String getTypeName(byte type) {
        String typeName = "NotFound";
        switch (type) {
            case 1:
                typeName = "FollowUser";
                break;
            case 2:
                typeName = "LikePost";
                break;
            case 3:
                typeName = "CommentPost";
                break;
            case 4:
                typeName = "LikeComment";
                break;
            case 5:
                typeName = "MentionComment";
                break;
            case 6:
                typeName = "MentionPost";
                break;
            case 7:
                typeName = "LikeCommentReply";
                break;
        }
        return typeName;
    }
    
    public static final byte getCategoryType(byte type) {
        byte categoryType = 0;
        switch (type) {
            case 2:
            case 4:
            case 7:
                categoryType = 1;
                break;
            case 3:
                categoryType = 2;
                break;
            case 5:
            case 6:
                categoryType = 3;
                break;
            case 1:
                categoryType = 4;
                break;
        }
        return categoryType;
    }
    
    public static final String notificationText(byte type){
        String text = "";
        switch(type){
            case 1:
                text = "started following you.";
                break;
            case 2:
                text = "liked your post.";
                break;
            case 3:
                text = "commented on your post.";
                break;
            case 4:
                text = "liked your comment.";
                break;
            case 5:
                text = "mentioned you in a comment.";
                break;
            case 6:
                text = "mentioned you in a post.";
                break;
            case 7:
                text = "liked your comment.";
                break;
        }
        return text;
    }
}
