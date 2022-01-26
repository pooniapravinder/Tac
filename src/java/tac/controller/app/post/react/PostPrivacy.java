package tac.controller.app.post.react;

import cookies.set.get.VerifyLogin;
import hibernate.query.PostQuery;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostPrivacy {

    @RequestMapping(value = "/rest_api/app/post/turn/{type}/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> postPrivacy(@PathVariable("type") String type, @RequestParam(value = "post_id", required = true) long postId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        PostQuery postQuery = new PostQuery();
        postQuery.postPrivacy(postId, verifyLogin.getUserId(), type.equals("comments"));
        map.put("success", true);
        return map;
    }
}
