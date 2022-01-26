package cookies.set.get;

import hibernate.mapping.Validlogin;
import hibernate.query.VerifyCookies;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class LoginStatus {

    private final HttpServletRequest request;
    protected long userId;
    protected boolean loggedIn;

    public LoginStatus(HttpServletRequest request) {
        this.request = request;
    }

    public void loginStatus() {
        String abc = null, xyz = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("abc")) {
                abc = cookie.getValue();
            }
            if (cookie.getName().equals("xyz")) {
                xyz = cookie.getValue();
            }
        }
        if ((abc == null) || (xyz == null)) {
            return;
        }
        long current_timestamp = System.currentTimeMillis();
        VerifyCookies getCookies = new VerifyCookies();
        Validlogin result = getCookies.findCookies(current_timestamp - (1000L * 60L * 60L * 24L * 61L), abc, xyz);
        if (result == null) {
            return;
        }
        userId = result.getUserId();
        loggedIn = true;
    }
}
