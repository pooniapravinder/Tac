package cookies.set.get;

import javax.servlet.http.HttpServletRequest;

public class VerifyLogin extends LoginStatus {

    public VerifyLogin(HttpServletRequest request) {
        super(request);
        super.loginStatus();
    }

    public long getUserId() {
        return userId;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }
}
