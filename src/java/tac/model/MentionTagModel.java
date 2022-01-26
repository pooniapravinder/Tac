package tac.model;

import utils.URLConfig;

public class MentionTagModel {

    private String image;
    private String titleOne;
    private String titleTwo;
    private long titleThree;
    private boolean hashtag;

    public MentionTagModel(String image, String titleOne, String titleTwo, long titleThree, String hashtag) {
        this.image = !Boolean.parseBoolean(hashtag) ? image != null ? URLConfig.profile_photo_url + image : null : "";
        this.titleOne = titleOne;
        this.titleTwo = titleTwo;
        this.titleThree = titleThree;
        this.hashtag = Boolean.parseBoolean(hashtag);
    }

    public MentionTagModel(String image, String titleOne, String titleTwo, String titleThree, String hashtag) {
        this.image = !Boolean.parseBoolean(hashtag) ? image != null ? URLConfig.profile_photo_url + image : null : "";
        this.titleOne = titleOne;
        this.titleTwo = titleTwo;
        this.titleThree = Long.parseLong(titleThree);
        this.hashtag = Boolean.parseBoolean(hashtag);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = !hashtag ? image != null ? URLConfig.profile_photo_url + image : null : "";
    }

    public String getTitleOne() {
        return titleOne;
    }

    public void setTitleOne(String titleOne) {
        this.titleOne = titleOne;
    }

    public String getTitleTwo() {
        return titleTwo;
    }

    public void setTitleTwo(String titleTwo) {
        this.titleTwo = titleTwo;
    }

    public long getTitleThree() {
        return titleThree;
    }

    public void setTitleThree(long titleThree) {
        this.titleThree = titleThree;
    }

    public boolean isHashtag() {
        return hashtag;
    }

    public void setHashtag(boolean hashtag) {
        this.hashtag = hashtag;
    }
}
