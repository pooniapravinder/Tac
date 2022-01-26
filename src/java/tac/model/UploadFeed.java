package tac.model;

public class UploadFeed {

    private String video;
    private String cover;
    private long timestamp;

    public UploadFeed() {
    }

    public UploadFeed(String video, String cover, long timestamp) {
        this.video = video;
        this.cover = cover;
        this.timestamp = timestamp;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
