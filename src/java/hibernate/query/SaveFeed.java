package hibernate.query;

import HibernateUtility.SetHibernateUtility;
import hibernate.mapping.Records;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SaveFeed {

    public void saveFeed(ArrayList<Records> acc) {
        Session sess = null;
        Transaction tr = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tr = sess.getTransaction();
            sess.getTransaction().setTimeout(1000);
            tr.begin();
            for (Records record : acc) {
                sess.save(record);
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
    }

    public ArrayList<Records> getFeed(long id) {
        Session sess = null;
        Transaction tx = null;
        ArrayList<Records> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("from Records where id>:id and timestmp>:timestmp");
            safehqlquery.setParameter("timestmp", System.currentTimeMillis() - (19800000));
            safehqlquery.setParameter("id", id);
            safehqlquery.setMaxResults(100);
            account = (ArrayList<Records>) safehqlquery.list();
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
}
