package hibernate.query;

import HibernateUtility.SetHibernateUtility;
import hibernate.mapping.Activity;
import hibernate.mapping.Comments;
import hibernate.mapping.CommentsLikes;
import hibernate.mapping.CommentsLikesId;
import hibernate.mapping.CommentsReplies;
import hibernate.mapping.CommentsRepliesLikes;
import hibernate.mapping.CommentsRepliesLikesId;
import hibernate.mapping.Music;
import hibernate.mapping.PostLikes;
import hibernate.mapping.Post;
import hibernate.mapping.PostLikesId;
import hibernate.mapping.PostSaved;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tac.model.CommentsModel;
import tac.model.CommentsRepliesModel;
import tac.model.FeedModel;

public class PostQuery {

    public long savePost(Music music, Post acc) {
        Session sess = null;
        Transaction tr = null;
        long postId = 0;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tr = sess.getTransaction();
            sess.getTransaction().setTimeout(1000);
            tr.begin();
            if (music.getMusicId() == 0) {
                sess.save(music);
            }
            sess.save(acc);
            postId = acc.getPostId();
            tr.commit();
            if (sess.getTransaction().isActive()) {
                System.out.println("Session is active");
            } else {
                System.out.println("Session is inactive");
            }
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return postId;
    }

    public List<FeedModel> getPost(long userId, boolean isPopular, int pagination) {
        Session sess = null;
        Transaction tx = null;
        List<FeedModel> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (isPopular) {
                safehqlquery = sess.createQuery("select new tac.model.FeedModel(tb1.profile.userId,tb4.fullName,tb4.profilePic,tb5.userName,tb4.verified,tb1.caption,tb1.coverImage,tb1.mediaKey,tb1.width,tb1.height,tb1.duration,tb1.mediaSize,tb2<>null,tb3<>null,tb1.likes,tb1.comments,tb1.postId,tb1.allowComments,tb1.allowDownloads, tb6<>null,tb7.musicId,tb7.musicKey,tb7.album,tb7.artist,tb7.coverImage,tb7.sourceType,tb9.userName,tb8.fullName,tb8.profilePic) from Post tb1 left outer join tb1.postLikeses tb2 on tb2.id.userId=:userId left outer join tb1.postSaveds tb3 on tb3.userId=:userId inner join tb1.profile tb4 inner join tb4.accounts tb5 left outer join tb4.followingFollowersesForUserTwo tb6 on tb6.id.userOne=:userId inner join tb1.music tb7 left join Profile tb8 on tb7.originalUser=tb8.userId inner join tb8.accounts tb9 where tb4.userId<>:userId and tb1.viewPrivacy=1 order by tb1.postId desc");         //viewPrivacy=1 for public
            } else {
                safehqlquery = sess.createQuery("select new tac.model.FeedModel(tb1.profile.userId,tb4.fullName,tb4.profilePic,tb5.userName,tb4.verified,tb1.caption,tb1.coverImage,tb1.mediaKey,tb1.width,tb1.height,tb1.duration,tb1.mediaSize,tb2<>null,tb3<>null,tb1.likes,tb1.comments,tb1.postId,tb1.allowComments,tb1.allowDownloads, tb6<>null,tb7.musicId,tb7.musicKey,tb7.album,tb7.artist,tb7.coverImage,tb7.sourceType,tb9.userName,tb8.fullName,tb8.profilePic) from Post tb1 left outer join tb1.postLikeses tb2 on tb2.id.userId=:userId left outer join tb1.postSaveds tb3 on tb3.userId=:userId inner join tb1.profile tb4 inner join tb4.accounts tb5 inner join tb4.followingFollowersesForUserTwo tb6 on tb6.id.userOne=:userId inner join tb1.music tb7 left join Profile tb8 on tb7.originalUser=tb8.userId inner join tb8.accounts tb9 where tb1.viewPrivacy<>3 order by tb1.postId desc");         //viewPrivacy=1 for public
            }
            safehqlquery.setParameter("userId", userId);
            safehqlquery.setFirstResult(pagination);
            safehqlquery.setMaxResults(20);
            account = (List<FeedModel>) safehqlquery.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return account;
    }

    public List<FeedModel> getPostByMusic(long userId, long musicId, int pagination) {
        Session sess = null;
        Transaction tx = null;
        List<FeedModel> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("select new tac.model.FeedModel(tb1.profile.userId,tb4.fullName,tb4.profilePic,tb5.userName,tb4.verified,tb1.caption,tb1.coverImage,tb1.mediaKey,tb1.width,tb1.height,tb1.duration,tb1.mediaSize,tb2<>null,tb3<>null,tb1.likes,tb1.comments,tb1.postId,tb1.allowComments,tb1.allowDownloads, tb6<>null,tb7.musicId,tb7.musicKey,tb7.album,tb7.artist,tb7.coverImage,tb7.sourceType,tb9.userName,tb8.fullName,tb8.profilePic) from Post tb1 left outer join tb1.postLikeses tb2 on tb2.id.userId=:userId left outer join tb1.postSaveds tb3 on tb3.userId=:userId inner join tb1.profile tb4 inner join tb4.accounts tb5 left outer join tb4.followingFollowersesForUserTwo tb6 on tb6.id.userOne=:userId inner join tb1.music tb7 left join Profile tb8 on tb7.originalUser=tb8.userId inner join tb8.accounts tb9 where tb7.musicId=:musicId order by tb1.postId desc");         //viewPrivacy=1 for public
            safehqlquery.setParameter("userId", userId);
            safehqlquery.setParameter("musicId", musicId);
            safehqlquery.setFirstResult(pagination);
            safehqlquery.setMaxResults(20);
            account = (List<FeedModel>) safehqlquery.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return account;
    }

    public boolean savePostLike(PostLikes acc, Activity activity, long userId, long postId) {
        Session sess = null;
        Transaction tr = null;
        boolean isSaved = false;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tr = sess.getTransaction();
            sess.getTransaction().setTimeout(1000);
            tr.begin();
            if (activity == null) {
                acc.setActivityId(0);
            } else {
                sess.save(activity);
                acc.setActivityId(activity.getActivityId());
            }
            sess.save(acc);
            isSaved = true;
            if (isSaved) {
                sess.createQuery("update Profile tb1 set tb1.likes=likes+1 where tb1.accounts.userId=:userId").setParameter("userId", userId).executeUpdate();
                sess.createQuery("update Post set likes=likes+1 where postId=:postId").setParameter("postId", postId).executeUpdate();
            }
            tr.commit();
            if (sess.getTransaction().isActive()) {
                System.out.println("Session is active");
            } else {
                System.out.println("Session is inactive");
            }
        } catch (Exception e) {
            isSaved = false;
            if (tr != null) {
                tr.rollback();
                // throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return isSaved;
    }

    public boolean deletePostLike(long postId, long sessionUserId, long userId) {
        Session sess = null;
        Transaction tx = null;
        boolean isDeleted = false;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            sess.createQuery("delete from Activity where activityId=(select activityId from PostLikes where id=:postLikesId)").setParameter("postLikesId", new PostLikesId(postId, sessionUserId)).executeUpdate();
            isDeleted = sess.createQuery("delete from PostLikes tb1 where tb1.id=:postLikesId").setParameter("postLikesId", new PostLikesId(postId, sessionUserId)).executeUpdate() > 0;
            if (isDeleted) {
                sess.createQuery("update Profile tb1 set tb1.likes=likes-1 where tb1.accounts.userId=:userId").setParameter("userId", userId).executeUpdate();
                sess.createQuery("update Post set likes=likes-1 where postId=:postId").setParameter("postId", postId).executeUpdate();
            }
            tx.commit();
        } catch (Exception e) {
            isDeleted = false;
            if (tx != null) {
                tx.rollback();
                // throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return isDeleted;
    }

    public CommentsModel saveComment(Comments acc) {
        Session sess = null;
        Transaction tr = null;
        CommentsModel account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tr = sess.getTransaction();
            sess.getTransaction().setTimeout(1000);
            tr.begin();
            sess.save(acc);
            if (acc.getCommentId() != 0) {
                sess.createQuery("update Post set comments=comments+1 where postId=:postId").setParameter("postId", acc.getPost().getPostId()).executeUpdate();
                account = (CommentsModel) sess.createQuery("select new tac.model.CommentsModel(tb2.commentId,tb3.profilePic,tb2.profile.userId,tb4.userName,tb2.comment,tb2.likes,tb2.replies,tb2.post.postId,tb2.commentTime,tb1<>null) from CommentsLikes tb1 right outer join tb1.comments tb2 on tb1.id.userId=:userId inner join tb2.profile tb3 inner join tb3.accounts tb4 where tb2.commentId=:commentId").setParameter("commentId", acc.getCommentId()).setParameter("userId", acc.getProfile().getUserId()).uniqueResult();
            }
            tr.commit();
            if (sess.getTransaction().isActive()) {
                System.out.println("Session is active");
            } else {
                System.out.println("Session is inactive");
            }
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return account;
    }

    public void deleteComment(long commentId, long postId, long replies) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            if (sess.createQuery("delete from Comments where commentId=:commentId").setParameter("commentId", commentId).executeUpdate() > 0) {
                sess.createQuery("update Post set comments=comments-1-:replies where postId=:postId").setParameter("replies", replies).setParameter("postId", postId).executeUpdate();
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    public List<CommentsModel> getComments(long postId, long userId, long commentId) {
        Session sess = null;
        Transaction tx = null;
        List<CommentsModel> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (commentId == 0) {
                safehqlquery = sess.createQuery("select new tac.model.CommentsModel(tb2.commentId,tb3.profilePic,tb2.profile.userId,tb4.userName,tb2.comment,tb2.likes,tb2.replies,tb2.post.postId,tb2.commentTime,tb1<>null) from CommentsLikes tb1 right outer join tb1.comments tb2 on tb1.id.userId=:userId inner join tb2.profile tb3 inner join tb3.accounts tb4 where tb2.post.postId=:postId order by tb2.commentId desc");
            } else {
                safehqlquery = sess.createQuery("select new tac.model.CommentsModel(tb2.commentId,tb3.profilePic,tb2.profile.userId,tb4.userName,tb2.comment,tb2.likes,tb2.replies,tb2.post.postId,tb2.commentTime,tb1<>null) from CommentsLikes tb1 right outer join tb1.comments tb2 on tb1.id.userId=:userId inner join tb2.profile tb3 inner join tb3.accounts tb4 where tb2.post.postId=:postId and tb2.commentId<:commentId order by tb2.commentId desc");
                safehqlquery.setParameter("commentId", commentId);
            }
            safehqlquery.setParameter("postId", postId);
            safehqlquery.setParameter("userId", userId);
            safehqlquery.setMaxResults(20);
            account = (List<CommentsModel>) safehqlquery.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return account;
    }

    public Object[] getCommentById(long commentId) {
        Session sess = null;
        Transaction tx = null;
        Object[] account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("select tb1, tb2 from Comments tb1 inner join tb1.post tb2 where tb1.commentId=:commentId");
            safehqlquery.setParameter("commentId", commentId);
            account = (Object[]) safehqlquery.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return account;
    }

    public Object[] getCommentReplyById(long replyId) {
        Session sess = null;
        Transaction tx = null;
        Object[] account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            safehqlquery = sess.createQuery("select tb1, tb2 from CommentsReplies tb1 inner join tb1.comments tb2 where tb1.replyId=:replyId");
            safehqlquery.setParameter("replyId", replyId);
            account = (Object[]) safehqlquery.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return account;
    }

    public long getPostId(long commentId) {
        Session sess = null;
        Transaction tx = null;
        long account = 0;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("select post.postId from Comments where commentId=:commentId");
            safehqlquery.setParameter("commentId", commentId);
            account = (long) safehqlquery.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return account;
    }

    public CommentsRepliesModel saveCommentReply(CommentsReplies acc, long userId) {
        Session sess = null;
        Transaction tr = null;
        CommentsRepliesModel account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tr = sess.getTransaction();
            sess.getTransaction().setTimeout(1000);
            tr.begin();
            sess.save(acc);
            if (acc.getReplyId() != 0) {
                sess.createQuery("update Post set comments=comments+1 where postId=(select post.postId from Comments where commentId=:commentId)").setParameter("commentId", acc.getComments().getCommentId()).executeUpdate();
                sess.createQuery("update Comments set replies=replies+1 where commentId=:commentId").setParameter("commentId", acc.getComments().getCommentId()).executeUpdate();
                account = (CommentsRepliesModel) sess.createQuery("select new tac.model.CommentsRepliesModel(tb2.replyId,tb5.profilePic,tb2.repliedBy,tb6.userName,tb2.reply,tb2.likes,tb2.comments.commentId,tb2.replyTime,tb1<>null) from CommentsRepliesLikes tb1 right outer join tb1.commentsReplies tb2 on tb1.id.userId=:userId inner join tb2.comments tb3 inner join tb3.post tb4 inner join Profile tb5 on tb2.repliedBy=tb5.userId inner join tb5.accounts tb6 where tb2.replyId=:replyId").setParameter("replyId", acc.getReplyId()).setParameter("userId", userId).uniqueResult();
            }
            tr.commit();
            if (sess.getTransaction().isActive()) {
                System.out.println("Session is active");
            } else {
                System.out.println("Session is inactive");
            }
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return account;
    }

    public void deleteCommentReply(long replyId, long postId, long commentId) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            if(sess.createQuery("delete from CommentsReplies where replyId=:replyId").setParameter("replyId", replyId).executeUpdate() > 0){
                sess.createQuery("update Post set comments=comments-1 where postId=:postId").setParameter("postId", postId).executeUpdate();
                sess.createQuery("update Comments set replies=replies-1 where commentId=:commentId").setParameter("commentId", commentId).executeUpdate();
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    public void postPrivacy(long postId, long userId, boolean isComment) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (isComment) {
                safehqlquery = sess.createQuery("update Post set allowComments = case allowComments when true then false else true end where postId=:postId and profile.userId=:userId");
            } else {
                safehqlquery = sess.createQuery("update Post set allowDownloads = case allowDownloads when true then false else true end where postId=:postId and profile.userId=:userId");
            }
            safehqlquery.setParameter("postId", postId);
            safehqlquery.setParameter("userId", userId);
            safehqlquery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    public List<CommentsRepliesModel> getCommentReplies(long commentId, long userId, int pagination) {
        Session sess = null;
        Transaction tx = null;
        List<CommentsRepliesModel> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            safehqlquery = sess.createQuery("select new tac.model.CommentsRepliesModel(tb2.replyId,tb5.profilePic,tb2.repliedBy,tb6.userName,tb2.reply,tb2.likes,tb2.comments.commentId,tb2.replyTime,tb1<>null) from CommentsRepliesLikes tb1 right outer join tb1.commentsReplies tb2 on tb1.id.userId=:userId inner join tb2.comments tb3 inner join tb3.post tb4 inner join Profile tb5 on tb2.repliedBy=tb5.userId inner join tb5.accounts tb6 where tb2.comments.commentId=:commentId order by tb2.replyId desc");
            safehqlquery.setParameter("commentId", commentId);
            safehqlquery.setParameter("userId", userId);
            safehqlquery.setFirstResult(pagination);
            safehqlquery.setMaxResults(3);
            account = (List<CommentsRepliesModel>) safehqlquery.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return account;
    }

    public boolean isPostSaved(long postId, long userId) {
        Session sess = null;
        Transaction tx = null;
        boolean isSaved = false;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("from PostSaved where userId=:userId and post.postId=:postId");
            safehqlquery.setParameter("postId", postId);
            safehqlquery.setParameter("userId", userId);
            isSaved = safehqlquery.uniqueResult() != null;
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return isSaved;
    }

    public void savePost(PostSaved acc) {
        Session sess = null;
        Transaction tr = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tr = sess.getTransaction();
            sess.getTransaction().setTimeout(1000);
            tr.begin();
            sess.save(acc);
            tr.commit();
            if (sess.getTransaction().isActive()) {
                System.out.println("Session is active");
            } else {
                System.out.println("Session is inactive");
            }
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    public void deleteSavedPost(long postId, long userId) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("delete from PostSaved where post.postId=:postId and userId=:userId");
            safehqlquery.setParameter("postId", postId);
            safehqlquery.setParameter("userId", userId);
            safehqlquery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    public void deletePost(long postId, long userId) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("delete from Post where postId=:postId and profile.userId=:userId");
            safehqlquery.setParameter("postId", postId);
            safehqlquery.setParameter("userId", userId);
            safehqlquery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    public Post getPostById(long postId, long userId) {
        Session sess = null;
        Transaction tx = null;
        Post post = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("from Post where postId=:postId and profile.userId=:userId");
            safehqlquery.setParameter("postId", postId);
            safehqlquery.setParameter("userId", userId);
            post = (Post) safehqlquery.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return post;
    }

    public Post getPostById(long postId) {
        Session sess = null;
        Transaction tx = null;
        Post post = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("from Post where postId=:postId");
            safehqlquery.setParameter("postId", postId);
            post = (Post) safehqlquery.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return post;
    }

    public boolean deleteCommentLike(long commentId, long sessionUserId) {
        Session sess = null;
        Transaction tx = null;
        boolean isDeleted = false;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            sess.createQuery("delete from Activity where activityId=(select activityId from CommentsLikes where id=:commentsLikesId)").setParameter("commentsLikesId", new CommentsLikesId(commentId, sessionUserId)).executeUpdate();
            isDeleted = sess.createQuery("delete from CommentsLikes tb1 where tb1.id=:commentsLikesId").setParameter("commentsLikesId", new CommentsLikesId(commentId, sessionUserId)).executeUpdate() > 0;
            if (isDeleted) {
                sess.createQuery("update Comments set likes=likes-1 where commentId=:commentId").setParameter("commentId", commentId).executeUpdate();
            }
            tx.commit();
        } catch (Exception e) {
            isDeleted = false;
            if (tx != null) {
                tx.rollback();
                // throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return isDeleted;
    }

    public boolean saveCommentLike(CommentsLikes acc, Activity activity, long commentId) {
        Session sess = null;
        Transaction tr = null;
        boolean isSaved = false;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tr = sess.getTransaction();
            sess.getTransaction().setTimeout(1000);
            tr.begin();
            if (activity == null) {
                acc.setActivityId(0);
            } else {
                sess.save(activity);
                acc.setActivityId(activity.getActivityId());
            }
            sess.save(acc);
            isSaved = true;
            if (isSaved) {
                sess.createQuery("update Comments set likes=likes+1 where commentId=:commentId").setParameter("commentId", commentId).executeUpdate();
            }
            tr.commit();
            if (sess.getTransaction().isActive()) {
                System.out.println("Session is active");
            } else {
                System.out.println("Session is inactive");
            }
        } catch (Exception e) {
            isSaved = false;
            if (tr != null) {
                tr.rollback();
                // throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return isSaved;
    }

    public boolean deleteCommentReplyLike(long replyId, long sessionUserId) {
        Session sess = null;
        Transaction tx = null;
        boolean isDeleted = false;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            sess.createQuery("delete from Activity where activityId=(select activityId from CommentsRepliesLikes where id=:commentsRepliesLikesId)").setParameter("commentsRepliesLikesId", new CommentsRepliesLikesId(replyId, sessionUserId)).executeUpdate();
            isDeleted = sess.createQuery("delete from CommentsRepliesLikes tb1 where tb1.id=:commentsRepliesLikesId").setParameter("commentsRepliesLikesId", new CommentsRepliesLikesId(replyId, sessionUserId)).executeUpdate() > 0;
            if (isDeleted) {
                sess.createQuery("update CommentsReplies set likes=likes-1 where replyId=:replyId").setParameter("replyId", replyId).executeUpdate();
            }
            tx.commit();
        } catch (Exception e) {
            isDeleted = false;
            if (tx != null) {
                tx.rollback();
                // throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return isDeleted;
    }

    public boolean saveCommentReplyLike(CommentsRepliesLikes acc, Activity activity, long replyId) {
        Session sess = null;
        Transaction tr = null;
        boolean isSaved = false;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tr = sess.getTransaction();
            sess.getTransaction().setTimeout(1000);
            tr.begin();
            if (activity == null) {
                acc.setActivityId(0);
            } else {
                sess.save(activity);
                acc.setActivityId(activity.getActivityId());
            }
            sess.save(acc);
            isSaved = true;
            if (isSaved) {
                sess.createQuery("update CommentsReplies set likes=likes+1 where replyId=:replyId").setParameter("replyId", replyId).executeUpdate();
            }
            tr.commit();
            if (sess.getTransaction().isActive()) {
                System.out.println("Session is active");
            } else {
                System.out.println("Session is inactive");
            }
        } catch (Exception e) {
            isSaved = false;
            if (tr != null) {
                tr.rollback();
                // throw e;
            }
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return isSaved;
    }
}
