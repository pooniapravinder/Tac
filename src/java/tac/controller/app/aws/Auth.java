package tac.controller.app.aws;

import client.server.cryptography.ServerDecrypt;
import client.server.cryptography.ServerEncrypt;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import cookies.set.get.VerifyLogin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import user.content.process.AWSConstants;

@Controller
public class Auth {

    @RequestMapping(value = "/rest_api/app/publish/post/auth/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getPostAuth(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            return "Not Authorized";
        }
        String authorization = decode(request.getHeader("Authorization"));
        String cipherText = decode(getBody(request));
        ObjectMapper objectMapper = new ObjectMapper();
        String keyName = (UUID.randomUUID()).toString().replace("-", "");
        try {
            ArrayList<AuthOutputModel> authOutModels = new ArrayList<>();
            AuthInputModel[] authInModels = objectMapper.readValue(ServerDecrypt.decryptText(authorization, cipherText), AuthInputModel[].class);
            for (AuthInputModel authModel : authInModels) {
                switch (authModel.getId()) {
                    case 101:
                        authOutModels.add(new AuthOutputModel(authModel.getId(), getUrl(keyName + ".jpg", AWSConstants.POST_VIDEOS_THUMBNAILS_BUCKET_NAME, authModel.getContentMD5()), keyName + ".jpg"));
                        break;
                    case 102:
                        authOutModels.add(new AuthOutputModel(authModel.getId(), getUrl(keyName + ".mp3", AWSConstants.POST_VIDEOS_MUSIC_BUCKET_NAME, authModel.getContentMD5()), keyName + ".mp3"));
                        break;
                    case 103:
                        authOutModels.add(new AuthOutputModel(authModel.getId(), getUrl(keyName + ".mp4", AWSConstants.POST_VIDEOS_BUCKET_NAME, authModel.getContentMD5()), keyName + ".mp4"));
                        break;
                    default:
                        break;
                }
            }
            map.put("data", authOutModels);
            map.put("success", true);
            Map<String, String> map2 = ServerEncrypt.a(new ObjectMapper().writeValueAsString(map));
            String text = "";
            String auth = "";
            for (Map.Entry<String, String> entry : map2.entrySet()) {
                if (entry.getKey().equals("text")) {
                    text = entry.getValue();
                }
                if (entry.getKey().equals("key")) {
                    auth = entry.getValue();
                }
            }
            response.setHeader("Authorization", encode(auth));
            return encode(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error Occurred";
    }

    @RequestMapping(value = "/rest_api/app/profile/pic/auth/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getProfilePicAuth(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (!verifyLogin.isLoggedIn()) {
            return "Not Authorized";
        }
        String authorization = decode(request.getHeader("Authorization"));
        String cipherText = decode(getBody(request));
        ObjectMapper objectMapper = new ObjectMapper();
        String keyName = (UUID.randomUUID()).toString().replace("-", "");
        try {
            ArrayList<AuthOutputModel> authOutModels = new ArrayList<>();
            AuthInputModel[] authInModels = objectMapper.readValue(ServerDecrypt.decryptText(authorization, cipherText), AuthInputModel[].class);
            authOutModels.add(new AuthOutputModel(authInModels[0].getId(), getUrl(keyName + ".jpg", AWSConstants.PROFILE_PHOTO_BUCKET_NAME, authInModels[0].getContentMD5()), keyName + ".jpg"));
            map.put("data", authOutModels);
            map.put("success", true);
            Map<String, String> map2 = ServerEncrypt.a(new ObjectMapper().writeValueAsString(map));
            String text = "";
            String auth = "";
            for (Map.Entry<String, String> entry : map2.entrySet()) {
                if (entry.getKey().equals("text")) {
                    text = entry.getValue();
                }
                if (entry.getKey().equals("key")) {
                    auth = entry.getValue();
                }
            }
            response.setHeader("Authorization", encode(auth));
            return encode(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error Occurred";
    }

    public String getUrl(String keyName, String bucketName, String contentMD5) {
        AWSCredentials credentials = new BasicAWSCredentials(AWSConstants.ACCESS_KEY_ID, AWSConstants.ACCESS_SEC_KEY);
        // AmazonS3 s3 = AmazonS3ClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://tac-p-v.wookes.com","ap-south-1")).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1).enableAccelerateMode().build();
        String result = null;
        try {
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 30; // 30 minutes
            expiration.setTime(expTimeMillis);
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, keyName);
            generatePresignedUrlRequest.setMethod(HttpMethod.PUT);
            generatePresignedUrlRequest.setExpiration(expiration);
            generatePresignedUrlRequest.putCustomRequestHeader("Content-MD5", contentMD5);
            result = s3.generatePresignedUrl(generatePresignedUrlRequest).toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return result;
    }

    public String getBody(HttpServletRequest request) {
        String body;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            try {
                throw ex;
            } catch (IOException ex1) {
                Logger.getLogger(Auth.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    try {
                        throw ex;
                    } catch (IOException ex1) {
                        Logger.getLogger(Auth.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
        body = stringBuilder.toString();
        return body;
    }

    private String decode(String text) {
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Auth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private String encode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Auth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
