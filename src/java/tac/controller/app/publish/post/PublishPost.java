package tac.controller.app.publish.post;

import cookies.set.get.VerifyLogin;
import hibernate.mapping.Activity;
import hibernate.mapping.Hashtags;
import hibernate.mapping.Music;
import hibernate.mapping.Post;
import hibernate.mapping.Profile;
import hibernate.query.ActivityQuery;
import hibernate.query.HashtagsQuery;
import hibernate.query.PostQuery;
import hibernate.query.ProfileQuery;
import tac.model.UploadVideoModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.ActivityTypeConstants;
import utils.URLConfig;
import tac.controller.app.notifications.FCM;

@Controller
public class PublishPost {

    private static final String HASHTAG_PATTERN = ".*?(#\\w+).*?";
    private static final String MENTION_PATTERN = ".*?(@\\w+).*?";

    @RequestMapping(value = "/rest_api/app/publish/post/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appPublishPost(UploadVideoModel uploadVideoModel, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        PostQuery uploadPostQuery = new PostQuery();
        Profile profile = new Profile();
        profile.setUserId(verifyLogin.getUserId());
        String caption = uploadVideoModel.getCaption().replaceAll("[\\n\\t]", " ").trim();
        HashtagsQuery hashtagsQuery = new HashtagsQuery();
        Set<String> captionHashtags = getHashtags(caption);
        ArrayList<Long> hashtagsUpdate = new ArrayList<>();
        Set<Hashtags> hashtags = new HashSet<>();
        Hashtags tempHashtag;
        for (String hashtag : captionHashtags) {
            tempHashtag = hashtagsQuery.getHashtag(hashtag);
            if (tempHashtag == null) {
                tempHashtag = new Hashtags(hashtag, 1, verifyLogin.getUserId(), System.currentTimeMillis());
            } else {
                hashtagsUpdate.add(tempHashtag.getHashtagId());
            }
            hashtags.add(tempHashtag);
        }
        Music music = new Music(uploadVideoModel.getAudio(), 0, verifyLogin.getUserId());
        if (uploadVideoModel.getMusicId() != null) {
            music.setMusicId(Long.parseLong(uploadVideoModel.getMusicId()));
        }
        long postId = uploadPostQuery.savePost(music, new Post(music, profile, caption, (byte) uploadVideoModel.getViewPrivacy(), uploadVideoModel.isAllowComments(), uploadVideoModel.isAllowDownloads(), uploadVideoModel.getThumbnail(), uploadVideoModel.getVideo(), uploadVideoModel.getSize(), uploadVideoModel.getHeight(), uploadVideoModel.getWidth(), uploadVideoModel.getDuration(), 0, 0, System.currentTimeMillis(), hashtags));
        hashtagsQuery.updateHashtagsTotal(hashtagsUpdate, true);
        addMentions(getMentions(caption), postId, verifyLogin.getUserId(), uploadVideoModel.getThumbnail());
        map.put("success", true);
        return map;
    }

    private void addMentions(Set<String> captionMentions, long postId, long sessionUser, String mediaUrl) {
        if (captionMentions.size() <= 0) {
            return;
        }
        Profile profile = new Profile();
        profile.setUserId(sessionUser);
        Activity activity;
        ArrayList<Activity> activities = new ArrayList<>();
        List<Long> userIds = new ProfileQuery().getUserIds(convertSetToList(captionMentions));
        for (long userId : userIds) {
            activity = new Activity(profile, userId, null, null, postId, ActivityTypeConstants.MENTION_POST, ActivityTypeConstants.getCategoryType(ActivityTypeConstants.MENTION_POST), true, System.currentTimeMillis(), null);
            activities.add(activity);
        }
        new ActivityQuery().saveActivities(activities);
        FCM.sendFCMNotification(userIds, sessionUser, Integer.parseInt(postId + "" + sessionUser), "Tac", ActivityTypeConstants.MENTION_POST, URLConfig.cover_image_url + mediaUrl, "low");
    }

    private Set<String> getHashtags(String text) {
        Pattern tagMatcher = Pattern.compile(HASHTAG_PATTERN);
        Set<String> hashtags = new HashSet<>();
        Matcher m = tagMatcher.matcher(text);
        while (m.find()) {
            hashtags.add(m.group(1).replace("#", ""));
        }
        return hashtags;
    }

    private Set<String> getMentions(String text) {
        Pattern tagMatcher = Pattern.compile(MENTION_PATTERN);
        Set<String> mentions = new HashSet<>();
        Matcher m = tagMatcher.matcher(text);
        while (m.find()) {
            mentions.add(m.group(1).replace("@", ""));
        }
        return mentions;
    }

    public static <T> List<T> convertSetToList(Set<T> set) {
        List<T> list = new ArrayList<>();
        for (T t : set) {
            list.add(t);
        }
        return list;
    }
}
