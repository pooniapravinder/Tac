package hibernate.query;

import HibernateUtility.SetHibernateUtility;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tac.model.HashtagModel;
import tac.model.ProfileModel;

public class SearchQuery {

    public List<ProfileModel> searchAccounts(String query, int pagination, long userId) {
        Session sess = null;
        Transaction tx = null;
        List<ProfileModel> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("select new tac.model.ProfileModel(tb1.userId,tb2.userName,tb1.verified,tb1.fullName,tb1.following,tb1.followers,tb1.likes,tb1.profilePic,tb1.gender,tb1.dob,tb1.bio) from Profile tb1 inner join tb1.accounts tb2 where (tb1.fullName like :query or tb2.userName like :query) and tb2.isBlocked=false and tb2.userId<>:userId order by tb1.accounts.userId asc");
            safehqlquery.setParameter("query", "%\\" + query + "%");
            safehqlquery.setParameter("userId", userId);
            safehqlquery.setFirstResult(pagination);
            safehqlquery.setMaxResults(20);
            account = (List<ProfileModel>) safehqlquery.list();
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

    public List<HashtagModel> searchHashtag(String query, int pagination) {
        Session sess = null;
        Transaction tx = null;
        List<HashtagModel> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("select new tac.model.HashtagModel(tb1.hashtagId,tb1.hashtagName,tb1.hashtagTotal,'') from Hashtags tb1 where (tb1.hashtagName like :query) order by tb1.hashtagTotal desc");
            safehqlquery.setParameter("query", "%\\" + query + "%");
            safehqlquery.setFirstResult(pagination);
            safehqlquery.setMaxResults(20);
            account = (List<HashtagModel>) safehqlquery.list();
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
