package tac.controller.app.post.react;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import cookies.set.get.VerifyLogin;
import hibernate.mapping.Hashtags;
import hibernate.mapping.Post;
import hibernate.query.HashtagsQuery;
import hibernate.query.PostQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import user.content.process.AWSConstants;

@Controller
public class DeletePost {

    private static final String HASHTAG_PATTERN = ".*?(#\\w+).*?";

    @RequestMapping(value = "/rest_api/app/post/delete/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> deletePost(@RequestParam(value = "post_id", required = true) long postId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            map.put("error", "Not logged in");
            return map;
        }
        PostQuery postQuery = new PostQuery();
        Post post = postQuery.getPostById(postId, verifyLogin.getUserId());
        if (post == null) {
            map.put("error", "Something went wrong");
            return map;
        }
        HashtagsQuery hashtagsQuery = new HashtagsQuery();
        ArrayList<Long> hashtagsUpdate = new ArrayList<>();
        Set<String> captionHashtags = getHashtags(post.getCaption());
        Hashtags tempHashtag;
        for (String hashtag : captionHashtags) {
            tempHashtag = hashtagsQuery.getHashtag(hashtag);
            if (tempHashtag != null) {
                hashtagsUpdate.add(tempHashtag.getHashtagId());
            }
        }
        postQuery.deletePost(postId, verifyLogin.getUserId());
        hashtagsQuery.updateHashtagsTotal(hashtagsUpdate, false);
        deleteFile(AWSConstants.POST_VIDEOS_THUMBNAILS_BUCKET_NAME, post.getCoverImage());
        deleteFile(AWSConstants.POST_VIDEOS_BUCKET_NAME, post.getMediaKey());
        map.put("success", true);
        return map;
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

    public void deleteFile(final String bucketName, final String keyName) {
        AWSCredentials credentials = new BasicAWSCredentials(AWSConstants.ACCESS_KEY_ID, AWSConstants.ACCESS_SEC_KEY);
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1).enableAccelerateMode().build();
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, keyName);
        s3.deleteObject(deleteObjectRequest);
    }
}
