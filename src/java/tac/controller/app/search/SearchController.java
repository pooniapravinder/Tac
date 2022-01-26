package tac.controller.app.search;

import cookies.set.get.VerifyLogin;
import hibernate.query.SearchQuery;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

    @RequestMapping(value = "/rest_api/app/search/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> getSearch(@RequestParam(value = "type", required = true) String type, @RequestParam(value = "query", required = true) String query, @RequestParam(value = "pagination", required = false) String pagination, HttpServletRequest request) {
        VerifyLogin verifyLogin = new VerifyLogin(request);
        Map<String, Object> map = new HashMap<>();
        SearchQuery searchQuery = new SearchQuery();
        map.put("data", (type.equals("popular") || type.equals("accounts")) ? searchQuery.searchAccounts(query, pagination == null ? 0 : Integer.parseInt(pagination), verifyLogin.isLoggedIn() ? verifyLogin.getUserId() : 0) : searchQuery.searchHashtag(query, pagination == null ? 0 : Integer.parseInt(pagination)));
        map.put("success", true);
        return map;
    }
}
