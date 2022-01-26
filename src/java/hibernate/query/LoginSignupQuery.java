package hibernate.query;

import HibernateUtility.SetHibernateUtility;
import hibernate.mapping.Accounts;
import hibernate.mapping.Profile;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LoginSignupQuery {

    public long addAccount(Profile account) {
        long userId = 0;
        Session sess = null;
        Transaction tr = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tr = sess.getTransaction();
            sess.getTransaction().setTimeout(1000);
            tr.begin();
            sess.save(account);
            userId = account.getUserId();
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
        return userId;
    }

    public Accounts getAccount(String userInput) {
        Session sess = null;
        Transaction tx = null;
        Accounts account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (userInput.matches("^\\d{10}$")) {
                safehqlquery = sess.createQuery("from Accounts where phone=:phone");
                safehqlquery.setParameter("phone", Long.parseLong(userInput));
            } else {
                safehqlquery = sess.createQuery("from Accounts where userName=:username");
                safehqlquery.setParameter("username", userInput);
            }
            account = (Accounts) safehqlquery.uniqueResult();
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

    public Accounts getUserAccount(long userId) {
        Session sess = null;
        Transaction tx = null;
        Accounts account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("from Accounts where userId=:userId");
            safehqlquery.setParameter("userId", userId);
            account = (Accounts) safehqlquery.uniqueResult();
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

    public void updatePassword(String password, long userId) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("update Accounts set password=:password where userId=:userId");
            safehqlquery.setParameter("password", password);
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
