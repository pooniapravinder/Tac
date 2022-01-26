package hibernate.query;

import tac.model.ActivityModel;
import HibernateUtility.SetHibernateUtility;
import hibernate.mapping.Activity;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ActivityQuery {

    public long saveActivity(Activity acc) {
        Session sess = null;
        Transaction tr = null;
        long id = 0;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tr = sess.getTransaction();
            sess.getTransaction().setTimeout(1000);
            tr.begin();
            sess.save(acc);
            id = acc.getActivityId();
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
        return id;
    }

    public void saveActivities(ArrayList<Activity> activities) {
        Session sess = null;
        Transaction tr = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tr = sess.getTransaction();
            sess.getTransaction().setTimeout(1000);
            tr.begin();
            for(Activity activity : activities){
                sess.save(activity);
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
            if (sess != null) sess.close();
        }
    }

    public ArrayList<ActivityModel> getActivites(long ownerId, byte categoryType, int pagination) {
        Session sess = null;
        Transaction tx = null;
        ArrayList<ActivityModel> account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (categoryType == 0) {
                safehqlquery = sess.createQuery("select new tac.model.ActivityModel(tb1.activityId,tb1.activityType,tb2<>null,tb1.creationTime,tb3.userId,tb3.fullName,tb4.userName,tb3.profilePic,tb3.verified,tb5.postId,tb5.coverImage) from Activity tb1 left join FollowingFollowers tb2 on (tb2.id.userOne=tb1.ownerId and tb2.id.userTwo=tb1.profile.userId) inner join tb1.profile tb3 inner join tb3.accounts tb4 left join Post tb5 on tb5.postId=tb1.postId where tb1.ownerId=:ownerId order by tb1.creationTime desc");
            } else {
                safehqlquery = sess.createQuery("select new tac.model.ActivityModel(tb1.activityId,tb1.activityType,tb2<>null,tb1.creationTime,tb3.userId,tb3.fullName,tb4.userName,tb3.profilePic,tb3.verified,tb5.postId,tb5.coverImage) from Activity tb1 left join FollowingFollowers tb2 on (tb2.id.userOne=tb1.ownerId and tb2.id.userTwo=tb1.profile.userId) inner join tb1.profile tb3 inner join tb3.accounts tb4 left join Post tb5 on tb5.postId=tb1.postId where tb1.ownerId=:ownerId and tb1.categoryType=:categoryType order by tb1.creationTime desc");
                safehqlquery.setParameter("categoryType", categoryType);
            }
            safehqlquery.setParameter("ownerId", ownerId);
            safehqlquery.setFirstResult(pagination);
            safehqlquery.setMaxResults(30);
            account = (ArrayList<ActivityModel>) safehqlquery.list();
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

    public void deleteActivity(long activityId) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("delete from Activity where activityId=:activityId");
            safehqlquery.setParameter("activityId", activityId);
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
