package tac.controller.app.suggestions;

import cookies.set.get.VerifyLogin;
import hibernate.query.SuggestionsQuery;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MentionsTags {
    
    @RequestMapping(value = "/rest_api/app/get/suggestions/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> getSuggestions(@RequestParam(value = "query", required = true) String query, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        map.put("data", (new SuggestionsQuery()).getSuggestions(query, verifyLogin.isLoggedIn() ? verifyLogin.getUserId() : 0));
        map.put("success", true);
        return map;
    }
}
