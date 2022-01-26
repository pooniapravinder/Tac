package tac.controller.app.following.followers;

import cookies.set.get.VerifyLogin;
import hibernate.mapping.Activity;
import hibernate.mapping.FollowingFollowers;
import hibernate.mapping.FollowingFollowersId;
import hibernate.mapping.Profile;
import hibernate.query.ActivityQuery;
import hibernate.query.FollowingFollowersQuery;
import hibernate.query.ProfileQuery;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.ActivityTypeConstants;
import tac.controller.app.notifications.FCM;

@Controller
public class FollowUnfollow {

    ProfileQuery profileQuery = new ProfileQuery();
    FollowingFollowersQuery followingFollowersQuery = new FollowingFollowersQuery();

    @RequestMapping(value = "/rest_api/app/top/users/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appNewUserFollow(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        map.put("data", profileQuery.getTopUsers(verifyLogin.isLoggedIn() ? verifyLogin.getUserId() : 0));
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/followers/remove/user/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appFollowerRemove(@RequestParam(value = "user", required = true) long user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        if (user == verifyLogin.getUserId()) {
            map.put("error", "Something went wrong");
            return map;
        }
        followingFollowersQuery.removeFollower(user, verifyLogin.getUserId());
        FollowingFollowers followingFollowers = followingFollowersQuery.getFollower(user, verifyLogin.getUserId());
        if (followingFollowers == null) {
            map.put("error", "Something went wrong");
            return map;
        }
        new ActivityQuery().deleteActivity(followingFollowers.getActivityId());
        profileQuery.updateFollowers(verifyLogin.getUserId(), false);
        profileQuery.updateFollowing(user, false);
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/follow/user/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appFollowUser(@RequestParam(value = "user", required = true) long user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        if (user == verifyLogin.getUserId()) {
            map.put("error", "Something went wrong");
            return map;
        }
        FollowingFollowers followingFollowersExist = followingFollowersQuery.getFollower(verifyLogin.getUserId(), user);
        if (followingFollowersExist != null) {
            map.put("error", "Something went wrong");
            return map;
        }
        Profile profileOne = new Profile();
        profileOne.setUserId(verifyLogin.getUserId());
        Profile profileTwo = new Profile();
        profileTwo.setUserId(user);
        Activity activity = new Activity(profileOne, user, ActivityTypeConstants.FOLLOW_USER, ActivityTypeConstants.getCategoryType(ActivityTypeConstants.FOLLOW_USER), true, System.currentTimeMillis());
        FollowingFollowers followingFollowers = new FollowingFollowers(new FollowingFollowersId(verifyLogin.getUserId(), user), profileOne, profileTwo, System.currentTimeMillis(), new ActivityQuery().saveActivity(activity));
        followingFollowersQuery.addFollower(followingFollowers);
        profileQuery.updateFollowers(user, true);
        profileQuery.updateFollowing(verifyLogin.getUserId(), true);
        FCM.sendFCMNotification(user, verifyLogin.getUserId(), Integer.parseInt(user + "" + verifyLogin.getUserId()), "Tac", ActivityTypeConstants.FOLLOW_USER, null, "low");
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/unfollow/user/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appUnfollowUser(@RequestParam(value = "user", required = true) long user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        FollowingFollowers followingFollowers = followingFollowersQuery.getFollower(verifyLogin.getUserId(), user);
        if (followingFollowers == null) {
            map.put("error", "Something went wrong");
            return map;
        }
        new ActivityQuery().deleteActivity(followingFollowers.getActivityId());
        followingFollowersQuery.removeFollower(verifyLogin.getUserId(), user);
        profileQuery.updateFollowers(user, false);
        profileQuery.updateFollowing(verifyLogin.getUserId(), false);
        FCM.cancelFCMNotification(user, Integer.parseInt(user + "" + verifyLogin.getUserId()));
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/user/following/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appGetFollowing(@RequestParam(value = "user", required = false) String user, @RequestParam(value = "pagination", required = false) String pagination, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        map.put("data", followingFollowersQuery.getFollowingFollowers(user != null ? Long.parseLong(user) : verifyLogin.getUserId(), verifyLogin.getUserId(), true, pagination == null ? 0 : Integer.parseInt(pagination)));
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/rest_api/app/user/followers/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appGetFollowers(@RequestParam(value = "user", required = false) String user, @RequestParam(value = "pagination", required = false) String pagination, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        map.put("data", followingFollowersQuery.getFollowingFollowers(user != null ? Long.parseLong(user) : verifyLogin.getUserId(), verifyLogin.getUserId(), false, pagination == null ? 0 : Integer.parseInt(pagination)));
        map.put("success", true);
        return map;
    }
}
