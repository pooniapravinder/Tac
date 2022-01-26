package tac.controller.messageservice;

public class Messages {

    private String message;
    private long mobile;

    public Messages(String message, long mobile) {
        this.message = message;
        this.mobile = mobile;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getMobile() {
        return this.mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

}
