package tac.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import utils.URLConfig;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Music {

    private long id;
    private String url;
    private String album;
    private String artist;
    private String coverImage;
    private int sourceType;
    private String username;
    private String fullName;
    private String profilePic;

    public Music(long id, String url, String album, String artist, String coverImage, int sourceType, String username, String fullName, String profilePic) {
        this.id = id;
        this.url = url != null ? url : null;
        this.album = album;
        this.artist = artist;
        this.coverImage = coverImage;
        this.sourceType = sourceType;
        this.username = username;
        this.fullName = fullName;
        this.profilePic = profilePic != null ? profilePic : null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

}
