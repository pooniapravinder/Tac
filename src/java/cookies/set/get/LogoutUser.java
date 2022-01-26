package cookies.set.get;

import hibernate.query.VerifyCookies;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutUser {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public LogoutUser(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void logoutUser() {
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
        getCookies.LogoutUser(abc, xyz);
        for (Cookie cookie : cookies) {
            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
}
