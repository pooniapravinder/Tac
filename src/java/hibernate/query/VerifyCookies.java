package hibernate.query;

import HibernateUtility.SetHibernateUtility;
import hibernate.mapping.Validlogin;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class VerifyCookies {

    public Validlogin findCookies(long timestamp, String abc, String xyz) {
        Session sess = null;
        Transaction tx = null;
        Validlogin account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("from Validlogin where abc=:abc and xyz=:xyz and loggedIn=true and loginTimestamp>:timestamp order by autoId desc");
            safehqlquery.setParameter("abc", abc);
            safehqlquery.setParameter("xyz", xyz);
            safehqlquery.setParameter("timestamp", timestamp);
            safehqlquery.setMaxResults(1);
            account = (Validlogin) safehqlquery.uniqueResult();
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

    public void updateVerified(String abc, String xyz) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("update Validlogin set verified=true where abc=:abc and xyz=:xyz");
            safehqlquery.setParameter("abc", abc);
            safehqlquery.setParameter("xyz", xyz);
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

    public void LogoutUser(String abc, String xyz) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("update Validlogin set loggedIn=false where abc=:abc and xyz=:xyz");
            safehqlquery.setParameter("abc", abc);
            safehqlquery.setParameter("xyz", xyz);
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
