package hibernate.mapping;
// Generated Nov 20, 2020 6:30:10 PM by Hibernate Tools 4.3.1



/**
 * PostLikesId generated by hbm2java
 */
public class PostLikesId  implements java.io.Serializable {


     private long postId;
     private long userId;

    public PostLikesId() {
    }

    public PostLikesId(long postId, long userId) {
       this.postId = postId;
       this.userId = userId;
    }
   
    public long getPostId() {
        return this.postId;
    }
    
    public void setPostId(long postId) {
        this.postId = postId;
    }
    public long getUserId() {
        return this.userId;
    }
    
    public void setUserId(long userId) {
        this.userId = userId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PostLikesId) ) return false;
		 PostLikesId castOther = ( PostLikesId ) other; 
         
		 return (this.getPostId()==castOther.getPostId())
 && (this.getUserId()==castOther.getUserId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + (int) this.getPostId();
         result = 37 * result + (int) this.getUserId();
         return result;
   }   


}

