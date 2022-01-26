package tac.model;

import utils.URLConfig;

public class HashtagModel {

    private long hashtagId;
    private String hashtagName;
    private long hashtagTotal;
    private String thumbnail;

    public HashtagModel(long hashtagId, String hashtagName, long hashtagTotal, String thumbnail) {
        this.hashtagId = hashtagId;
        this.hashtagName = hashtagName;
        this.hashtagTotal = hashtagTotal;
        this.thumbnail = thumbnail != null ? URLConfig.profile_photo_url + thumbnail : null;
    }

    public long getHashtagId() {
        return hashtagId;
    }

    public void setHashtagId(long hashtagId) {
        this.hashtagId = hashtagId;
    }

    public String getHashtagName() {
        return hashtagName;
    }

    public void setHashtagName(String hashtagName) {
        this.hashtagName = hashtagName;
    }

    public long getHashtagTotal() {
        return hashtagTotal;
    }

    public void setHashtagTotal(long hashtagTotal) {
        this.hashtagTotal = hashtagTotal;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail != null ? URLConfig.profile_photo_url + thumbnail : null;
    }
}
