package tac.controller.app.profile;

import cookies.set.get.VerifyLogin;
import hibernate.mapping.Accounts;
import hibernate.mapping.FollowingFollowers;
import hibernate.query.FollowingFollowersQuery;
import hibernate.query.LoginSignupQuery;
import hibernate.query.ProfileQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tac.model.ProfileModel;
import tac.model.UpdateProfileModel;

@Controller
public class ProfileData {

    @RequestMapping(value = "/rest_api/app/get/profile/info/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> getProfileInfo(@RequestParam(value = "user", required = false) String user, @RequestParam(value = "user_type", required = true) int userType, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        ProfileQuery profileQuery = new ProfileQuery();
        ProfileModel profileModel = profileQuery.getProfileInfo(user != null ? user : String.valueOf(verifyLogin.getUserId()), user != null ? userType : 0);
        if (profileModel == null) {
            map.put("error", "User not found");
            return map;
        }
        map.put("data", profileModel);
        long userOne, userTwo;
        if (profileModel.getUserId() != verifyLogin.getUserId()) {
            FollowingFollowersQuery followingFollowersQuery = new FollowingFollowersQuery();
            ArrayList<FollowingFollowers> followingFollowers = followingFollowersQuery.getProfileInfoExtra(verifyLogin.getUserId(), profileModel.getUserId());
            ProfileModel.ExtraData extraData = profileModel.new ExtraData();
            for (FollowingFollowers followingFollower : followingFollowers) {
                userOne = followingFollower.getProfileByUserOne().getUserId();
                userTwo = followingFollower.getProfileByUserTwo().getUserId();
                if (userOne == verifyLogin.getUserId() && userTwo == profileModel.getUserId()) {
                    extraData.setFollowing(true);
                } else {
                    extraData.setFollowedBy(true);
                }
            }
            map.put("extra_data", extraData);
        }
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/get/profile/{data}/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> getProfileDetail(@PathVariable("data") String data, @RequestParam(value = "user_id", required = false) String userId, @RequestParam(value = "pagination", required = false) String pagination, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        ProfileQuery profileQuery = new ProfileQuery();
        if (null != data) {
            switch (data) {
                case "videos":
                    map.put("data", profileQuery.getProfileData(userId != null ? Long.parseLong(userId) : verifyLogin.getUserId(), verifyLogin.getUserId(), 0, pagination == null ? 0 : Integer.parseInt(pagination)));
                    break;
                case "likes":
                    map.put("data", profileQuery.getProfileData(userId != null ? Long.parseLong(userId) : verifyLogin.getUserId(), verifyLogin.getUserId(), 1, pagination == null ? 0 : Integer.parseInt(pagination)));
                    break;
                case "saved":
                    map.put("data", profileQuery.getProfileData(verifyLogin.getUserId(), verifyLogin.getUserId(), 2, pagination == null ? 0 : Integer.parseInt(pagination)));
                    break;
            }
        }
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/update/profile/info/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> updateProfileInfo(UpdateProfileModel updateProfileModel, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        if (updateProfileModel.getUsername() == null) {
            map.put("error", "The username cannot be empty.");
            return map;
        } else if (updateProfileModel.getUsername().length() < 2) {
            map.put("error", "The username " + updateProfileModel.getUsername() + " is not available.");
            return map;
        } else if (updateProfileModel.getUsername().length() > 20) {
            map.put("error", "Enter username under 20 characters.");
            return map;
        } else if ((!updateProfileModel.getUsername().matches("^[a-zA-Z0-9_.]*$"))) {
            map.put("error", "Usernames can only use letters, numbers, underscores and periods.");
            return map;
        }
        Accounts accounts = new LoginSignupQuery().getAccount(updateProfileModel.getUsername());
        if (accounts != null && accounts.getUserId() != verifyLogin.getUserId()) {
            map.put("error", "The username " + updateProfileModel.getUsername() + " is not available.");
            return map;
        } else if (updateProfileModel.getFullname() == null || updateProfileModel.getFullname().length() < 2 || updateProfileModel.getFullname().length() > 30) {
            map.put("error", "Name must have 2 to 30 characters.");
            return map;
        } else if (!updateProfileModel.getFullname().matches("^(([A-Za-z\\s]){2,30})*$")) {
            map.put("error", "Name can only use letters and spaces.");
            return map;
        } else if (updateProfileModel.getBio().length() > 100) {
            map.put("error", "Bio can have only 100 characters.");
            return map;
        }
        ProfileQuery profileQuery = new ProfileQuery();
        profileQuery.updateProfileInfo(verifyLogin.getUserId(), updateProfileModel);
        map.put("success", true);
        return map;
    }
}
