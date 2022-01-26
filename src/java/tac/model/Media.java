package tac.model;

import utils.URLConfig;

public class Media {

    private long id;
    private String thumbnail;

    public Media(long id, String thumbnail) {
        this.id = id;
        this.thumbnail = URLConfig.cover_image_url + thumbnail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = URLConfig.cover_image_url + thumbnail;
    }
}
