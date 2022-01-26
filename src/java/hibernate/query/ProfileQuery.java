package hibernate.query;

import HibernateUtility.SetHibernateUtility;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tac.model.FeedModel;
import tac.model.NewUserFollowModel;
import tac.model.ProfileModel;
import tac.model.UpdateProfileModel;

public class ProfileQuery {

    public List<FeedModel> getProfileData(long userId, long sessionUser, int type, int pagination) {
        Session sess = null;
        Transaction tx = null;
        List<FeedModel> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (type == 0) {
                safehqlquery = sess.createQuery("select new tac.model.FeedModel(tb1.profile.userId,tb4.fullName,tb4.profilePic,tb5.userName,tb4.verified,tb1.caption,tb1.coverImage,tb1.mediaKey,tb1.width,tb1.height,tb1.duration,tb1.mediaSize,tb2<>null,tb3<>null,tb1.likes,tb1.comments,tb1.postId,tb1.allowComments,tb1.allowDownloads,tb6<>null,tb7.musicId,tb7.musicKey,tb7.album,tb7.artist,tb7.coverImage,tb7.sourceType,tb9.userName,tb8.fullName,tb8.profilePic) from Post tb1 left outer join tb1.postLikeses tb2 on tb2.id.userId=:sessionUser left outer join tb1.postSaveds tb3 on tb3.userId=:sessionUser inner join tb1.profile tb4 inner join tb4.accounts tb5 left outer join tb4.followingFollowersesForUserTwo tb6 on tb6.id.userOne=:sessionUser inner join tb1.music tb7 left join Profile tb8 on tb7.originalUser=tb8.userId inner join tb8.accounts tb9 where tb1.profile.userId=:userId order by tb1.postId desc");
                safehqlquery.setParameter("sessionUser", sessionUser);
            } else if (type == 1) {
                safehqlquery = sess.createQuery("select new tac.model.FeedModel(tb1.profile.userId,tb4.fullName,tb4.profilePic,tb5.userName,tb4.verified,tb1.caption,tb1.coverImage,tb1.mediaKey,tb1.width,tb1.height,tb1.duration,tb1.mediaSize,tb2<>null,tb3<>null,tb1.likes,tb1.comments,tb1.postId,tb1.allowComments,tb1.allowDownloads,tb6<>null,tb7.musicId,tb7.musicKey,tb7.album,tb7.artist,tb7.coverImage,tb7.sourceType,tb9.userName,tb8.fullName,tb8.profilePic) from Post tb1 inner join tb1.postLikeses postLikeses on postLikeses.id.userId=:userId left outer join tb1.postLikeses tb2 on tb2.id.userId=:sessionUser left outer join tb1.postSaveds tb3 on tb3.userId=:sessionUser inner join tb1.profile tb4 inner join tb4.accounts tb5 left outer join tb4.followingFollowersesForUserTwo tb6 on tb6.id.userOne=:sessionUser inner join tb1.music tb7 left join Profile tb8 on tb7.originalUser=tb8.userId inner join tb8.accounts tb9 order by tb2.id desc");
                safehqlquery.setParameter("sessionUser", sessionUser);
            } else {
                safehqlquery = sess.createQuery("select new tac.model.FeedModel(tb1.profile.userId,tb4.fullName,tb4.profilePic,tb5.userName,tb4.verified,tb1.caption,tb1.coverImage,tb1.mediaKey,tb1.width,tb1.height,tb1.duration,tb1.mediaSize,tb2<>null,tb3<>null,tb1.likes,tb1.comments,tb1.postId,tb1.allowComments,tb1.allowDownloads,tb6<>null,tb7.musicId,tb7.musicKey,tb7.album,tb7.artist,tb7.coverImage,tb7.sourceType,tb9.userName,tb8.fullName,tb8.profilePic) from Post tb1 left outer join tb1.postLikeses tb2 on tb2.id.userId=:userId inner join tb1.postSaveds tb3 on tb3.userId=:userId inner join tb1.profile tb4 inner join tb4.accounts tb5 left outer join tb4.followingFollowersesForUserTwo tb6 on tb6.id.userOne=:userId inner join tb1.music tb7 left join Profile tb8 on tb7.originalUser=tb8.userId inner join tb8.accounts tb9 order by tb3.savedId desc");
            }
            safehqlquery.setParameter("userId", userId);
            safehqlquery.setFirstResult(pagination);
            safehqlquery.setMaxResults(21);
            account = (List<FeedModel>) safehqlquery.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) sess.close();
        }
        return account;
    }

    public List<NewUserFollowModel> getTopUsers(long userId) {
        Session sess = null;
        Transaction tx = null;
        List<NewUserFollowModel> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("select new tac.model.NewUserFollowModel(tb1.userId,tb2.userName,tb1.verified,tb1.fullName,tb1.profilePic) from Profile tb1 inner join tb1.accounts tb2 where tb1.accounts.userId<>:userId");
            safehqlquery.setParameter("userId", userId);
            safehqlquery.setMaxResults(20);
            account = (List<NewUserFollowModel>) safehqlquery.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) sess.close();
        }
        return account;
    }

    public void updateProfilePic(long userId, String profilePic) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("update Profile set profilePic=:profilePic where userId=:userId");
            safehqlquery.setParameter("userId", userId);
            safehqlquery.setParameter("profilePic", profilePic);
            safehqlquery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) sess.close();
        }
    }

    public void updateProfileInfo(long userId, UpdateProfileModel updateProfileModel) {
        Session sess = null;
        Transaction tx = null;
        updateUsername(userId, updateProfileModel.getUsername());
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("update Profile set fullName=:fullName,bio=:bio where userId=:userId");
            safehqlquery.setParameter("userId", userId);
            safehqlquery.setParameter("fullName", updateProfileModel.getFullname());
            safehqlquery.setParameter("bio", updateProfileModel.getBio());
            safehqlquery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) sess.close();
        }
    }

    public void updateUsername(long userId, String username) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("update Accounts set userName=:username where userId=:userId");
            safehqlquery.setParameter("userId", userId);
            safehqlquery.setParameter("username", username.toLowerCase());
            safehqlquery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) sess.close();
        }
    }

    public ProfileModel getProfileInfo(String user, int userType) {
        Session sess = null;
        Transaction tx = null;
        ProfileModel account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (userType == 0) {     //Search user for id
                safehqlquery = sess.createQuery("select new tac.model.ProfileModel(tb1.userId,tb2.userName,tb1.verified,tb1.fullName,tb1.following,tb1.followers,tb1.likes,tb1.profilePic,tb1.gender,tb1.dob,tb1.bio) from Profile tb1 inner join tb1.accounts tb2 where tb1.userId=:userId");
                safehqlquery.setParameter("userId", Long.parseLong(user));
            } else {                    //Search user for user name
                safehqlquery = sess.createQuery("select new tac.model.ProfileModel(tb1.userId,tb2.userName,tb1.verified,tb1.fullName,tb1.following,tb1.followers,tb1.likes,tb1.profilePic,tb1.gender,tb1.dob,tb1.bio) from Profile tb1 inner join tb1.accounts tb2 where tb2.userName=:userName");
                safehqlquery.setParameter("userName", user);
            }
            account = (ProfileModel) safehqlquery.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) sess.close();
        }
        return account;
    }

    public List<Long> getUserIds(List<String> usernames) {
        Session sess = null;
        Transaction tx = null;
        List<Long> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("select tb1.userId from Accounts tb1 where tb1.userName in (:userNames)");
            safehqlquery.setParameterList("userNames", usernames);
            account = (List<Long>) safehqlquery.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) sess.close();
        }
        return account;
    }

    public void updateFollowers(long userId, boolean isAdd) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (isAdd) {
                safehqlquery = sess.createQuery("update Profile set followers=followers+1 where userId=:userId");
            } else {
                safehqlquery = sess.createQuery("update Profile set followers=followers-1 where userId=:userId");
            }
            safehqlquery.setParameter("userId", userId);
            safehqlquery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) sess.close();
        }
    }

    public void updateFollowing(long userId, boolean isAdd) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (isAdd) {
                safehqlquery = sess.createQuery("update Profile set following=following+1 where userId=:userId");
            } else {
                safehqlquery = sess.createQuery("update Profile set following=following-1 where userId=:userId");
            }
            safehqlquery.setParameter("userId", userId);
            safehqlquery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) sess.close();
        }
    }
}
