package tac.controller.app.profile;

import cookies.set.get.VerifyLogin;
import hibernate.mapping.Profile;
import hibernate.query.ProfileQuery;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProfilePhoto {

    @RequestMapping(value = "/rest_api/app/profile/photo/upload/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> updateProfilePhoto(@RequestParam(value = "profile_photo", required = true) String photo, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        ProfileQuery profileQuery = new ProfileQuery();
        Profile profile = new Profile();
        profile.setUserId(verifyLogin.getUserId());
        profileQuery.updateProfilePic(verifyLogin.getUserId(), photo);
        map.put("success", true);
        return map;
    }
}
