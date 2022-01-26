package hibernate.query;

import HibernateUtility.SetHibernateUtility;
import hibernate.mapping.Hashtags;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tac.model.DiscoverItemsModel;
import tac.model.FeedModel;
import tac.model.HashtagModel;

public class HashtagsQuery {

    public ArrayList<DiscoverItemsModel> getDiscoverData(long userId, int pagination) {
        Session sess = null;
        Transaction tx = null;
        ArrayList<DiscoverItemsModel> discoverItemsModels = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("select tb1 from Hashtags tb1 order by tb1.hashtagTotal desc");
            safehqlquery.setFirstResult(pagination);
            safehqlquery.setMaxResults(10);
            ArrayList<Hashtags> hashtags = (ArrayList<Hashtags>) safehqlquery.list();
            for (Hashtags hashtag : hashtags) {
                safehqlquery = sess.createQuery("select new tac.model.FeedModel(tb1.profile.userId,tb4.fullName,tb4.profilePic,tb5.userName,tb4.verified,tb1.caption,tb1.coverImage,tb1.mediaKey,tb1.width,tb1.height,tb1.duration,tb1.mediaSize,tb2<>null,tb3<>null,tb1.likes,tb1.comments,tb1.postId,tb1.allowComments,tb1.allowDownloads,tb7<>null,tb8.musicId,tb8.musicKey,tb8.album,tb8.artist,tb8.coverImage,tb8.sourceType,tb10.userName,tb9.fullName,tb9.profilePic) from Post tb1 left outer join tb1.postLikeses tb2 on tb2.id.userId=:userId left outer join tb1.postSaveds tb3 on tb3.userId=:userId inner join tb1.profile tb4 inner join tb4.accounts tb5 inner join tb1.hashtagses tb6 left outer join tb4.followingFollowersesForUserTwo tb7 on tb7.id.userOne=:userId inner join tb1.music tb8 left join Profile tb9 on tb8.originalUser=tb9.userId inner join tb9.accounts tb10 where tb1.viewPrivacy=1 and tb6.hashtagName=:hashtagName");
                safehqlquery.setParameter("userId", userId);
                safehqlquery.setParameter("hashtagName", hashtag.getHashtagName());
                safehqlquery.setMaxResults(10);
                discoverItemsModels.add(new DiscoverItemsModel(hashtag.getHashtagId(), hashtag.getHashtagName(), hashtag.getHashtagTotal(), (ArrayList<FeedModel>) safehqlquery.list()));
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            if (sess != null) sess.close();
        }
        return discoverItemsModels;
    }

    public Hashtags getHashtag(String hashtag) {
        Session sess = null;
        Transaction tx = null;
        Hashtags account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("from Hashtags where hashtagName=:hashtag");
            safehqlquery.setParameter("hashtag", hashtag);
            account = (Hashtags) safehqlquery.uniqueResult();
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

    public ArrayList<FeedModel> getHashtagPostById(long userId, long hashtagId, int type, int pagination) {
        Session sess = null;
        Transaction tx = null;
        ArrayList<FeedModel> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (type == 0) {
                safehqlquery = sess.createQuery("select new tac.model.FeedModel(tb1.profile.userId,tb4.fullName,tb4.profilePic,tb5.userName,tb4.verified,tb1.caption,tb1.coverImage,tb1.mediaKey,tb1.width,tb1.height,tb1.duration,tb1.mediaSize,tb2<>null,tb3<>null,tb1.likes,tb1.comments,tb1.postId,tb1.allowComments,tb1.allowDownloads,tb7<>null,tb8.musicId,tb8.musicKey,tb8.album,tb8.artist,tb8.coverImage,tb8.sourceType,tb10.userName,tb9.fullName,tb9.profilePic) from Post tb1 left outer join tb1.postLikeses tb2 on tb2.id.userId=:userId left outer join tb1.postSaveds tb3 on tb3.userId=:userId inner join tb1.profile tb4 inner join tb4.accounts tb5 inner join tb1.hashtagses tb6 left outer join tb4.followingFollowersesForUserTwo tb7 on tb7.id.userOne=:userId inner join tb1.music tb8 left join Profile tb9 on tb8.originalUser=tb9.userId inner join tb9.accounts tb10 where tb6.hashtagId=:hashtagId");
            } else {
                safehqlquery = sess.createQuery("select new tac.model.FeedModel(tb1.profile.userId,tb4.fullName,tb4.profilePic,tb5.userName,tb4.verified,tb1.caption,tb1.coverImage,tb1.mediaKey,tb1.width,tb1.height,tb1.duration,tb1.mediaSize,tb2<>null,tb3<>null,tb1.likes,tb1.comments,tb1.postId,tb1.allowComments,tb1.allowDownloads,tb7<>null,tb8.musicId,tb8.musicKey,tb8.album,tb8.artist,tb8.coverImage,tb8.sourceType,tb10.userName,tb9.fullName,tb9.profilePic) from Post tb1 left outer join tb1.postLikeses tb2 on tb2.id.userId=:userId left outer join tb1.postSaveds tb3 on tb3.userId=:userId inner join tb1.profile tb4 inner join tb4.accounts tb5 inner join tb1.hashtagses tb6 left outer join tb4.followingFollowersesForUserTwo tb7 on tb7.id.userOne=:userId inner join tb1.music tb8 left join Profile tb9 on tb8.originalUser=tb9.userId inner join tb9.accounts tb10 where tb6.hashtagId=:hashtagId order by tb6.hashtagId desc");
            }
            safehqlquery.setParameter("userId", userId);
            safehqlquery.setParameter("hashtagId", hashtagId);
            safehqlquery.setFirstResult(pagination);
            safehqlquery.setMaxResults(21);
            account = (ArrayList<FeedModel>) safehqlquery.list();
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

    public void updateHashtagsTotal(ArrayList<Long> hashtagIds, boolean isIncrease) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.beginTransaction();
            Query safehqlquery;
            String query = isIncrease ? "update Hashtags set hashtagTotal=hashtagTotal+1 where hashtagId=:hashtagId" : "update Hashtags set hashtagTotal=hashtagTotal-1 where hashtagId=:hashtagId";
            for (int i = 0; i < hashtagIds.size(); i++) {
                safehqlquery = sess.createQuery(query);
                safehqlquery.setParameter("hashtagId", hashtagIds.get(i));
                safehqlquery.executeUpdate();
                if (i % 50 == 0) {
                    sess.flush();
                    sess.clear();
                }
            }
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

    public HashtagModel getHashtagData(String hashtag, int hashtagType) {
        Session sess = null;
        Transaction tx = null;
        HashtagModel account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (hashtagType == 0) {     //Search hashtag for id
                safehqlquery = sess.createQuery("select new tac.model.HashtagModel(tb1.hashtagId,tb1.hashtagName,tb1.hashtagTotal,tb2.profilePic) from Hashtags tb1, Profile tb2 where tb1.hashtagId=:hashtagId and tb2.accounts.userId=tb1.userId");
                safehqlquery.setParameter("hashtagId", Long.parseLong(hashtag));
            } else {                    //Search hashtag for hashtag name
                safehqlquery = sess.createQuery("select new tac.model.HashtagModel(tb1.hashtagId,tb1.hashtagName,tb1.hashtagTotal,tb2.profilePic) from Hashtags tb1, Profile tb2 where tb1.hashtagName=:hashtagName and tb2.accounts.userId=tb1.userId");
                safehqlquery.setParameter("hashtagName", hashtag);
            }
            account = (HashtagModel) safehqlquery.uniqueResult();
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
}
