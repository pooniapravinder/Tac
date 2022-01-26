package hibernate.query;

import HibernateUtility.SetHibernateUtility;
import hibernate.mapping.Otp;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class OtpQuery {

    public void saveOTP(Otp acc) {
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

    public Otp getOtp(long phone) {
        Session sess = null;
        Transaction tx = null;
        Otp account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("from Otp where phone=:phone");
            safehqlquery.setParameter("phone", phone);
            account = (Otp) safehqlquery.uniqueResult();
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

    public void updateOtp(Otp otp) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("update Otp set otp=:otp,timestmp=:timestmp,tries=0 where phone=:phone");
            safehqlquery.setParameter("otp", otp.getOtp());
            safehqlquery.setParameter("timestmp", System.currentTimeMillis());
            safehqlquery.setParameter("phone", otp.getPhone());
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

    public void updateOtpTries(Otp otp) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("update Otp set tries=tries+1 where phone=:phone");
            safehqlquery.setParameter("phone", otp.getPhone());
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
