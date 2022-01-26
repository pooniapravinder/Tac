package tac.model;

public class UploadVideoModel {

    private String caption;
    private int viewPrivacy;
    private boolean allowComments;
    private boolean allowDownloads;
    private String thumbnail;
    private int height;
    private int width;
    private long duration;
    private long size;
    private String audio;
    private String video;
    private String musicId;

    public UploadVideoModel() {
    }

    public UploadVideoModel(String caption, int viewPrivacy, boolean allowComments, boolean allowDownloads, String thumbnail, int height, int width, long duration, long size, String audio, String video, String musicId) {
        this.caption = caption;
        this.viewPrivacy = viewPrivacy;
        this.allowComments = allowComments;
        this.allowDownloads = allowDownloads;
        this.height = height;
        this.width = width;
        this.duration = duration;
        this.size = size;
        this.thumbnail = thumbnail;
        this.audio = audio;
        this.video = video;
        this.musicId = musicId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getViewPrivacy() {
        return viewPrivacy;
    }

    public void setViewPrivacy(int viewPrivacy) {
        this.viewPrivacy = viewPrivacy;
    }

    public boolean isAllowComments() {
        return allowComments;
    }

    public void setAllowComments(boolean allowComments) {
        this.allowComments = allowComments;
    }

    public boolean isAllowDownloads() {
        return allowDownloads;
    }

    public void setAllowDownloads(boolean allowDownloads) {
        this.allowDownloads = allowDownloads;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }
}
