package tac.controller.app.notifications;

import cookies.set.get.VerifyLogin;
import hibernate.mapping.NotificationsToken;
import hibernate.query.NotificationsQuery;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterToken {

    @RequestMapping(value = "/rest_api/app/notifications/register/token/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appNotificationsRegisterToken(@RequestParam(value = "token", required = true) String token, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        NotificationsQuery notificationsQuery = new NotificationsQuery();
        notificationsQuery.saveNotificationToken(new NotificationsToken(token, verifyLogin.isLoggedIn() ? verifyLogin.getUserId() : 0));
        map.put("success", "true");
        return map;
    }
}
