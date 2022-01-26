package tac.controller.app.hashtags;

import cookies.set.get.VerifyLogin;
import hibernate.query.HashtagsQuery;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tac.model.HashtagModel;

@Controller
public class HashtagsController {

    @RequestMapping(value = "/rest_api/app/hashtag/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appHashtag(@RequestParam(value = "hashtag", required = true) String hashtag, @RequestParam(value = "hashtag_type", required = true) int hashtagType, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        HashtagsQuery hashtagsQuery = new HashtagsQuery();
        HashtagModel hashtagModel = hashtagsQuery.getHashtagData(hashtag, hashtagType);
        if (hashtagModel == null) {
            map.put("error", "Hashtag Not Found");
        } else {
            map.put("data", hashtagModel);
            map.put("success", true);
        }
        return map;
    }

    @RequestMapping(value = "/rest_api/app/hashtags/{type}/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appHashtagsData(@PathVariable("type") String type, @RequestParam(value = "hashtag_id", required = true) long hashtagId, @RequestParam(value = "pagination", required = false) String pagination, HttpServletRequest request) {
        VerifyLogin verifyLogin = new VerifyLogin(request);
        Map<String, Object> map = new HashMap<>();
        HashtagsQuery hashtagsQuery = new HashtagsQuery();
        map.put("data", hashtagsQuery.getHashtagPostById(verifyLogin.isLoggedIn() ? verifyLogin.getUserId() : 0, hashtagId, type.equals("top") ? 0 : 1, pagination == null ? 0 : Integer.parseInt(pagination)));
        map.put("success", true);
        return map;
    }
}
