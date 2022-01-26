package hibernate.query;

import HibernateUtility.SetHibernateUtility;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tac.model.MentionTagModel;

public class SuggestionsQuery {
    
    public ArrayList<MentionTagModel> getSuggestions(String query, long userId) {
        Session sess = null;
        Transaction tx = null;
        ArrayList<MentionTagModel> account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (query.startsWith("#")) {
                query = query.replace("#", "");
                safehqlquery = sess.createQuery("select new tac.model.MentionTagModel('null',tb1.hashtagName,'null',tb1.hashtagTotal,'true') from Hashtags tb1 where (tb1.hashtagName like :query) order by tb1.hashtagTotal desc");
            } else {
                query = query.replace("@", "");
                safehqlquery = sess.createQuery("select new tac.model.MentionTagModel(tb1.profilePic,tb2.userName,tb1.fullName,'0','false') from Profile tb1 inner join tb1.accounts tb2 where (tb1.fullName like :query or tb2.userName like :query) and tb2.isBlocked=false and tb2.userId<>:userId order by tb1.accounts.userId asc");
                safehqlquery.setParameter("userId", userId);
            }
            safehqlquery.setParameter("query", "%\\" + query + "%");
            account = (ArrayList<MentionTagModel>) safehqlquery.list();
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
