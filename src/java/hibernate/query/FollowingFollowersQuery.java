package hibernate.query;

import HibernateUtility.SetHibernateUtility;
import hibernate.mapping.FollowingFollowers;
import hibernate.mapping.FollowingFollowersId;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tac.model.FollowingFollowersModel;

public class FollowingFollowersQuery {

    public void addFollower(FollowingFollowers acc) {
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

    public void removeFollower(long userOne, long userTwo) {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("delete from FollowingFollowers tb1 where tb1.id=:id");
            safehqlquery.setParameter("id", new FollowingFollowersId(userOne, userTwo));
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

    public FollowingFollowers getFollower(long userOne, long userTwo) {
        Session sess = null;
        Transaction tx = null;
        FollowingFollowers account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("from FollowingFollowers tb1 where tb1.id=:id");
            safehqlquery.setParameter("id", new FollowingFollowersId(userOne, userTwo));
            account = (FollowingFollowers) safehqlquery.uniqueResult();
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

    public ArrayList<FollowingFollowers> getProfileInfoExtra(long userOne, long userTwo) {
        Session sess = null;
        Transaction tx = null;
        ArrayList<FollowingFollowers> account = null;
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery = sess.createQuery("from FollowingFollowers tb1 where (tb1.id=:idOne or tb1.id=:idTwo)");
            safehqlquery.setParameter("idOne", new FollowingFollowersId(userOne, userTwo));
            safehqlquery.setParameter("idTwo", new FollowingFollowersId(userTwo, userOne));
            account = (ArrayList<FollowingFollowers>) safehqlquery.list();
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

    public List<FollowingFollowersModel> getFollowingFollowers(long userId, long sessionUser, boolean isFollowing, int pagination) {
        Session sess = null;
        Transaction tx = null;
        List<FollowingFollowersModel> account = new ArrayList<>();
        try {
            sess = SetHibernateUtility.getSessionFactory().openSession();
            tx = sess.getTransaction();
            tx.begin();
            Query safehqlquery;
            if (isFollowing) {
                safehqlquery = sess.createQuery("select new tac.model.FollowingFollowersModel(tb2.userId,tb3.userName,tb2.fullName,tb2.profilePic,tb4<>null) from FollowingFollowers tb1 inner join tb1.profileByUserTwo tb2 inner join tb2.accounts tb3 left outer join tb2.followingFollowersesForUserTwo tb4 on tb4.id.userOne=:sessionUser where tb1.id.userOne=:userId order by tb1.followTime desc");
            } else {
                safehqlquery = sess.createQuery("select new tac.model.FollowingFollowersModel(tb2.userId,tb3.userName,tb2.fullName,tb2.profilePic,tb4<>null) from FollowingFollowers tb1 inner join tb1.profileByUserOne tb2 inner join tb2.accounts tb3 left outer join tb2.followingFollowersesForUserTwo tb4 on tb4.id.userOne=:sessionUser where tb1.id.userTwo=:userId order by tb1.followTime desc");
            }
            safehqlquery.setParameter("userId", userId);
            safehqlquery.setParameter("sessionUser", sessionUser);
            safehqlquery.setFirstResult(pagination);
            safehqlquery.setMaxResults(20);
            account = (List<FollowingFollowersModel>) safehqlquery.list();
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
