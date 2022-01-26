package tac.model;

public class FeedModel {

    private long postId;
    private String coverImage;
    private String media;
    private long timestamp;

    public FeedModel(long postId, String coverImage, String media, long timestamp) {
        this.postId = postId;
        this.coverImage = coverImage;
        this.media = media;
        this.timestamp = timestamp;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
