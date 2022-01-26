package tac.controller.app.post.react;

import cookies.set.get.VerifyLogin;
import hibernate.mapping.Post;
import hibernate.mapping.PostSaved;
import hibernate.query.PostQuery;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SavePost {

    PostQuery postQuery = new PostQuery();

    @RequestMapping(value = "/rest_api/app/post/save/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> savePost(@RequestParam(value = "post_id", required = true) long postId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        boolean isSaved = postQuery.isPostSaved(postId, verifyLogin.getUserId());
        if (isSaved) {
            postQuery.deleteSavedPost(postId, verifyLogin.getUserId());
        } else {
            Post post = new Post();
            post.setPostId(postId);
            postQuery.savePost(new PostSaved(post, verifyLogin.getUserId()));
        }
        map.put("success", true);
        return map;
    }
}
