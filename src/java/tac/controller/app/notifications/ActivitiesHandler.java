package tac.controller.app.notifications;

import cookies.set.get.VerifyLogin;
import hibernate.query.ActivityQuery;
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
public class ActivitiesHandler {
    
    @RequestMapping(value = "/rest_api/app/account/activity/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appActivities(@RequestParam(value = "pagination", required = false) String pagination, @RequestParam(value = "type", required = false) String type, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        ActivityQuery activityQuery = new ActivityQuery();
        map.put("data", activityQuery.getActivites(verifyLogin.getUserId(), type == null ? 0 : Byte.parseByte(type), pagination == null ? 0 : Integer.parseInt(pagination)));
        map.put("success", "true");
        return map;
    }
}
