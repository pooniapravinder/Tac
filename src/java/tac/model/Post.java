package tac.model;

import utils.URLConfig;

public class Post {

    private long id;
    private String caption;
    private String thumbnail;
    private String url;
    private int width;
    private int height;
    private long duration;
    private long size;
    private boolean liked;
    private boolean saved;
    private long likes;
    private long comments;
    private boolean allowComments;
    private boolean allowDownloads;

    public Post(long id, String caption, String thumbnail, String media, int width, int height, long duration, long size, boolean liked, boolean saved, long likes, long comments, boolean allowComments, boolean allowDownloads) {
        this.id = id;
        this.caption = caption;
        this.thumbnail = thumbnail;
        this.url = media;
        this.liked = liked;
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.size = size;
        this.saved = saved;
        this.likes = likes;
        this.comments = comments;
        this.allowComments = allowComments;
        this.allowDownloads = allowDownloads;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isSaved() {
        return saved;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public void setAllowComments(boolean allowComments) {
        this.allowComments = allowComments;
    }

    public boolean isAllowComments() {
        return this.allowComments;
    }

    public void setAllowDownloads(boolean allowDownloads) {
        this.allowDownloads = allowDownloads;
    }

    public boolean isAllowDownloads() {
        return this.allowDownloads;
    }
}
