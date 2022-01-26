package cookies.set.get;

import Random.String.Generator.RandomString;
import hibernate.mapping.Validlogin;
import hibernate.query.LoginToUser;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class SaveLogin {

    private final HttpServletResponse response;
    private final long user;

    public SaveLogin(HttpServletResponse response, long user) {
        this.response = response;
        this.user = user;
    }

    public void saveCookie() {
        RandomString randomString = new RandomString();
        String abc = randomString.generateRandomAlphaNumeric(190), xyz = randomString.generateRandomAlphaNumeric(450);
        new LoginToUser().addLoginAccount(new Validlogin(user, System.currentTimeMillis(), true, abc, xyz));
        setCookie("abc", abc);
        setCookie("xyz", xyz);
    }

    private void setCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 61);
        response.addCookie(cookie);
    }

}
