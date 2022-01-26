package hibernate.query;

import HibernateUtility.SetHibernateUtility;
import hibernate.mapping.Tokens;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TokensQuery {

    public void saveToken(Tokens acc) {
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
            if (sess != null) sess.close();
        }
    }

    public Tokens getToken(long userId) {
        Session sess = null;
        Transaction tx = null;
        Tokens account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("from Tokens where userId=:userId");
            safehqlquery.setParameter("userId", userId);
            account = (Tokens) safehqlquery.uniqueResult();
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

    public void updateToken(Tokens token) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("update Tokens set token=:token,timestmp=:timestmp where userId=:userId");
            safehqlquery.setParameter("token", token.getToken());
            safehqlquery.setParameter("timestmp", System.currentTimeMillis());
            safehqlquery.setParameter("userId", token.getUserId());
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
