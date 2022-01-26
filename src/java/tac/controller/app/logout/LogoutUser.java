package tac.controller.app.logout;

import cookies.set.get.VerifyLogin;
import hibernate.mapping.FollowingFollowers;
import hibernate.query.FollowingFollowersQuery;
import hibernate.query.ProfileQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tac.model.ProfileModel;

@Controller
public class LogoutUser {

    @RequestMapping(value = "/rest_api/app/logout/user/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> logoutUser(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        map.put("success", true);
        return map;
    }
}
