package tac.controller.app.aws;

public class AuthInputModel {

    private int id;
    private String contentType;
    private long contentLength;
    private String contentMD5;

    public AuthInputModel() {

    }

    public AuthInputModel(int id, String contentType, long contentLength, String contentMD5) {
        this.id = id;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.contentMD5 = contentMD5;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentMD5() {
        return contentMD5;
    }

    public void setContentMD5(String contentMD5) {
        this.contentMD5 = contentMD5;
    }
}
