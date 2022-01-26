package tac.controller.app.post.react;

import cookies.set.get.VerifyLogin;
import hibernate.mapping.Activity;
import hibernate.mapping.Comments;
import hibernate.mapping.CommentsLikes;
import hibernate.mapping.CommentsLikesId;
import hibernate.mapping.CommentsReplies;
import hibernate.mapping.CommentsRepliesLikes;
import hibernate.mapping.CommentsRepliesLikesId;
import hibernate.mapping.PostLikes;
import hibernate.mapping.Post;
import hibernate.mapping.PostLikesId;
import hibernate.mapping.Profile;
import hibernate.query.ActivityQuery;
import hibernate.query.PostQuery;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.ActivityTypeConstants;
import utils.URLConfig;
import tac.controller.app.notifications.FCM;
import tac.model.CommentsModel;
import tac.model.CommentsRepliesModel;

@Controller
public class PostReact {

    PostQuery postQuery = new PostQuery();

    @RequestMapping(value = "/rest_api/app/post/like/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> postLike(@RequestParam(value = "post_id", required = true) long postId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        Profile profileOne = new Profile();
        profileOne.setUserId(verifyLogin.getUserId());
        Post post = postQuery.getPostById(postId);
        if (post == null) {
            map.put("error", "Something went wrong");
            return map;
        }
        if (!postQuery.deletePostLike(postId, verifyLogin.getUserId(), post.getProfile().getUserId())) {
            Post setPost = new Post();
            setPost.setPostId(postId);
            Activity activity = new Activity(profileOne, post.getProfile().getUserId(), null, null, postId, ActivityTypeConstants.LIKE_POST, ActivityTypeConstants.getCategoryType(ActivityTypeConstants.LIKE_POST), true, System.currentTimeMillis(), null);
            if (postQuery.savePostLike(new PostLikes(new PostLikesId(postId, verifyLogin.getUserId()), setPost, 0), (post.getProfile().getUserId() != verifyLogin.getUserId()) ? activity : null, post.getProfile().getUserId(), postId)) {
                if (post.getProfile().getUserId() != verifyLogin.getUserId()) {
                    FCM.sendFCMNotification(post.getProfile().getUserId(), verifyLogin.getUserId(), Integer.parseInt(postId + "" + verifyLogin.getUserId()), "Tac", ActivityTypeConstants.LIKE_POST, URLConfig.cover_image_url + post.getCoverImage(), "low");
                }
            } else {
                map.put("success", false);
                return map;
            }
        } else {
            if (post.getProfile().getUserId() != verifyLogin.getUserId()) {
                FCM.cancelFCMNotification(post.getProfile().getUserId(), Integer.parseInt(post.getPostId() + "" + verifyLogin.getUserId()));
            }
        }
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/get/comments/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> getComments(@RequestParam(value = "post_id", required = true) long postId, @RequestParam(value = "comment_id", required = false) String commentId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        map.put("data", postQuery.getComments(postId, verifyLogin.isLoggedIn() ? verifyLogin.getUserId() : 0, commentId != null ? Long.parseLong(commentId) : 0));
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/post/comments/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> postComments(@RequestParam(value = "post_id", required = true) long postId, @RequestParam(value = "comment", required = true) String comment, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        Post post = new Post();
        post.setPostId(postId);
        Profile profile = new Profile();
        profile.setUserId(verifyLogin.getUserId());
        CommentsModel commentsModel = postQuery.saveComment(new Comments(post, profile, comment, 0, 0, System.currentTimeMillis(),0));
        map.put("success", commentsModel != null);
        map.put("data", commentsModel);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/delete/comments/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> deleteComments(@RequestParam(value = "comment_id", required = true) long commentId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        Object[] comments = postQuery.getCommentById(commentId);
        if (comments == null) {
            map.put("error", "Something went wrong");
            return map;
        }
        Comments comment = (Comments) comments[0];
        Post post = (Post) comments[1];
        if (((comment.getProfile().getUserId() != verifyLogin.getUserId()) && (post.getProfile().getUserId() != verifyLogin.getUserId()))) {
            map.put("error", "Something went wrong");
            return map;
        }
        postQuery.deleteComment(commentId, post.getPostId(), comment.getReplies());
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/comments/reply/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> postCommentsReply(@RequestParam(value = "comment_id", required = true) long commentId, @RequestParam(value = "comment", required = true) String reply, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        Comments comments = new Comments();
        comments.setCommentId(commentId);
        CommentsRepliesModel commentsRepliesModel = postQuery.saveCommentReply(new CommentsReplies(comments, verifyLogin.getUserId(), reply, 0, System.currentTimeMillis(), 0), verifyLogin.getUserId());
        map.put("success", commentsRepliesModel != null);
        map.put("data", commentsRepliesModel);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/delete/comments/reply/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> deleteCommentsReply(@RequestParam(value = "reply_id", required = true) long replyId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        Object[] replies = postQuery.getCommentReplyById(replyId);
        if (replies == null) {
            map.put("error", "Something went wrong");
            return map;
        }
        CommentsReplies commentsReplies = (CommentsReplies) replies[0];
        Comments comments = (Comments) replies[1];
        if (commentsReplies.getRepliedBy() != verifyLogin.getUserId()) {
            map.put("error", "Something went wrong");
            return map;
        }
        postQuery.deleteCommentReply(replyId, comments.getPost().getPostId(), comments.getCommentId());
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/get/comments/reply/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> getCommentReplies(@RequestParam(value = "comment_id", required = true) long commentId, @RequestParam(value = "pagination", required = false) String pagination, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        map.put("data", postQuery.getCommentReplies(commentId, verifyLogin.isLoggedIn() ? verifyLogin.getUserId() : 0, pagination == null ? 0 : Integer.parseInt(pagination)));
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/comment/like/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> commentLike(@RequestParam(value = "comment_id", required = true) long commentId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        Profile profileOne = new Profile();
        profileOne.setUserId(verifyLogin.getUserId());
        Object[] comments = postQuery.getCommentById(commentId);
        if (comments == null) {
            map.put("error", "Something went wrong");
            return map;
        }
        Comments comment = (Comments) comments[0];
        Post postData = (Post) comments[1];
        if (!postQuery.deleteCommentLike(commentId, verifyLogin.getUserId())) {
            Comments setComments = new Comments();
            setComments.setCommentId(commentId);
            Activity activity = new Activity(profileOne, comment.getProfile().getUserId(), null, null, commentId, ActivityTypeConstants.LIKE_COMMENT, ActivityTypeConstants.getCategoryType(ActivityTypeConstants.LIKE_COMMENT), true, System.currentTimeMillis(), comment.getComment());
            if (postQuery.saveCommentLike(new CommentsLikes(new CommentsLikesId(commentId, verifyLogin.getUserId()), setComments, 0), (comment.getProfile().getUserId() != verifyLogin.getUserId()) ? activity : null, commentId)) {
                if (comment.getProfile().getUserId() != verifyLogin.getUserId()) {
                    FCM.sendFCMNotification(comment.getProfile().getUserId(), verifyLogin.getUserId(), Integer.parseInt(commentId + "" + verifyLogin.getUserId()), "Tac", ActivityTypeConstants.LIKE_COMMENT, URLConfig.cover_image_url + postData.getCoverImage(), "low");
                }
            } else {
                map.put("success", false);
                return map;
            }
        } else {
            if (comment.getProfile().getUserId() != verifyLogin.getUserId()) {
                FCM.cancelFCMNotification(comment.getProfile().getUserId(), Integer.parseInt(commentId + "" + verifyLogin.getUserId()));
            }
        }
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/comment/reply/like/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> commentReplyLike(@RequestParam(value = "reply_id", required = true) long replyId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        Profile profileOne = new Profile();
        profileOne.setUserId(verifyLogin.getUserId());
        Object[] replies = postQuery.getCommentReplyById(replyId);
        if (replies == null) {
            map.put("error", "Something went wrong");
            return map;
        }
        CommentsReplies commentsReply = (CommentsReplies) replies[0];
        Comments comments = (Comments) replies[1];
        if (!postQuery.deleteCommentReplyLike(replyId, verifyLogin.getUserId())) {
            CommentsReplies setCommentsReply = new CommentsReplies();
            setCommentsReply.setReplyId(replyId);
            Activity activity = new Activity(profileOne, commentsReply.getRepliedBy(), null, null, replyId, ActivityTypeConstants.LIKE_COMMENT_REPLY, ActivityTypeConstants.getCategoryType(ActivityTypeConstants.LIKE_COMMENT_REPLY), true, System.currentTimeMillis(), commentsReply.getReply());
            if (postQuery.saveCommentReplyLike(new CommentsRepliesLikes(new CommentsRepliesLikesId(replyId, verifyLogin.getUserId()), setCommentsReply, 0), (commentsReply.getRepliedBy() != verifyLogin.getUserId()) ? activity : null, replyId)) {
                if (commentsReply.getRepliedBy() != verifyLogin.getUserId()) {
                    FCM.sendFCMNotification(commentsReply.getRepliedBy(), verifyLogin.getUserId(), Integer.parseInt(replyId + "" + verifyLogin.getUserId()), "Tac", ActivityTypeConstants.LIKE_COMMENT, URLConfig.cover_image_url + comments.getPost().getCoverImage(), "low");
                }
            } else {
                map.put("success", false);
                return map;
            }
        } else {
            if (commentsReply.getRepliedBy() != verifyLogin.getUserId()) {
                FCM.cancelFCMNotification(commentsReply.getRepliedBy(), Integer.parseInt(replyId + "" + verifyLogin.getUserId()));
            }
        }
        map.put("success", true);
        return map;
    }
}
