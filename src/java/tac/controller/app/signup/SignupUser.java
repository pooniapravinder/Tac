package tac.controller.app.signup;

import cookies.set.get.VerifyLogin;
import hibernate.mapping.Accounts;
import hibernate.query.LoginSignupQuery;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SignupUser {

    @RequestMapping(value = "/rest_api/app/signup/username/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appSignupUsername(@RequestParam(value = "username", required = true) String username, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        long userId = verifyLogin.isLoggedIn() ? verifyLogin.getUserId() : 0;
        if ((username == null) || ((username.isEmpty()))) {
            map.put("error", "The username cannot be empty.");
            return map;
        } else if (username.length() < 2) {
            map.put("error", "The username " + username + " is not available.");
            return map;
        } else if (username.length() > 20) {
            map.put("error", "Enter username under 20 characters.");
            return map;
        } else if ((!username.matches("^[a-zA-Z0-9_.]*$"))) {
            map.put("error", "Usernames can only use letters, numbers, underscores and periods.");
            return map;
        } else {
            LoginSignupQuery loginSignupQuery = new LoginSignupQuery();
            Accounts account = loginSignupQuery.getAccount(username);
            if (account != null && account.getUserName().equals(username) && (userId == 0 || userId != account.getUserId())) {
                map.put("error", "The username " + username + " is not available.");
                return map;
            }
        }
        map.put("success", true);
        return map;
    }
}
