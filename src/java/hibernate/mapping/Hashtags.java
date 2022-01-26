package hibernate.mapping;
// Generated Aug 14, 2020 2:46:58 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Hashtags generated by hbm2java
 */
public class Hashtags  implements java.io.Serializable {


     private long hashtagId;
     private String hashtagName;
     private long hashtagTotal;
     private long userId;
     private long insertTime;
     private Set posts = new HashSet(0);

    public Hashtags() {
    }

	
    public Hashtags(String hashtagName, long hashtagTotal, long userId, long insertTime) {
        this.hashtagId = hashtagId;
        this.hashtagName = hashtagName;
        this.hashtagTotal = hashtagTotal;
        this.userId = userId;
        this.insertTime = insertTime;
    }
    public Hashtags(String hashtagName, long hashtagTotal, long userId, long insertTime, Set posts) {
       this.hashtagId = hashtagId;
       this.hashtagName = hashtagName;
       this.hashtagTotal = hashtagTotal;
       this.userId = userId;
       this.insertTime = insertTime;
       this.posts = posts;
    }
   
    public long getHashtagId() {
        return this.hashtagId;
    }
    
    public void setHashtagId(long hashtagId) {
        this.hashtagId = hashtagId;
    }
    public String getHashtagName() {
        return this.hashtagName;
    }
    
    public void setHashtagName(String hashtagName) {
        this.hashtagName = hashtagName;
    }
    public long getHashtagTotal() {
        return this.hashtagTotal;
    }
    
    public void setHashtagTotal(long hashtagTotal) {
        this.hashtagTotal = hashtagTotal;
    }
    public long getUserId() {
        return this.userId;
    }
    
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getInsertTime() {
        return this.insertTime;
    }
    
    public void setInsertTime(long insertTime) {
        this.insertTime = insertTime;
    }
    public Set getPosts() {
        return this.posts;
    }
    
    public void setPosts(Set posts) {
        this.posts = posts;
    }




}


