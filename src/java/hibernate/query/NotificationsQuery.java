package hibernate.query;

import HibernateUtility.SetHibernateUtility;
import hibernate.mapping.NotificationsToken;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class NotificationsQuery {

    public void saveNotificationToken(NotificationsToken acc) {
        Session sess = null;
        Transaction tr = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tr = sess.getTransaction();
            sess.getTransaction().setTimeout(1000);
            tr.begin();
            sess.saveOrUpdate(acc);
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
            if (sess != null) sess.close();
        }
    }

    public List<String> getNotificationsTokens(long userId) {
        Session sess = null;
        Transaction tx = null;
        List<String> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("select token from NotificationsToken where userId=:userId");
            safehqlquery.setParameter("userId", userId);
            account = (List<String>) safehqlquery.list();
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

    public List<String> getNotificationsTokens(List<Long> userIds) {
        Session sess = null;
        Transaction tx = null;
        List<String> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("select token from NotificationsToken where userId in (:userIds)");
            safehqlquery.setParameterList("userIds", userIds);
            account = (List<String>) safehqlquery.list();
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
