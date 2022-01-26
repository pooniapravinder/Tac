package cookies.set.get;

import hibernate.query.VerifyCookies;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class LoginVerified {

    private final HttpServletRequest request;

    public LoginVerified(HttpServletRequest request) {
        this.request = request;
    }

    public void updateVerified() {
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
        VerifyCookies getCookies = new VerifyCookies();
        getCookies.updateVerified(abc, xyz);
    }
}
