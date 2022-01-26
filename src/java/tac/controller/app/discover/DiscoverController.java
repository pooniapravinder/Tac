package tac.controller.app.discover;

import cookies.set.get.VerifyLogin;
import hibernate.query.HashtagsQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tac.model.DiscoverBannerModel;

@Controller
public class DiscoverController {

    @RequestMapping(value = "/rest_api/app/discover/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> getDiscoverData(@RequestParam(value = "pagination", required = false) String pagination, HttpServletRequest request) {
        VerifyLogin verifyLogin = new VerifyLogin(request);
        Map<String, Object> map = new HashMap<>();
        HashtagsQuery hashtagsQuery = new HashtagsQuery();
        ArrayList<DiscoverBannerModel> discoverBannerModels = new ArrayList<>();
        discoverBannerModels.add(new DiscoverBannerModel("https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072823_1280.jpg", 1));
        discoverBannerModels.add(new DiscoverBannerModel("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg", 1));
        map.put("data", hashtagsQuery.getDiscoverData(verifyLogin.isLoggedIn() ? verifyLogin.getUserId() : 0, pagination == null ? 0 : Integer.parseInt(pagination)));
        map.put("banner", discoverBannerModels);
        map.put("success", true);
        return map;
    }
}
