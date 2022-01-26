package tac.controller.app.home;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hibernate.mapping.Records;
import hibernate.query.SaveFeed;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tac.controller.app.aws.Auth;
import tac.model.FeedModel;
import tac.model.UploadFeed;

@Controller
public class Home {

    @RequestMapping(value = "/app/post/feed/data/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appPost(@RequestParam(value = "id", required = false) String id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Records> records = new SaveFeed().getFeed(id == null ? 0 : Integer.parseInt(id));
        ArrayList<FeedModel> feedModel = new ArrayList<>();
        for (Records record : records) {
            feedModel.add(new FeedModel(record.getId(), record.getCover(), record.getVideo(), record.getTimestmp()));
        }
        map.put("data", feedModel);
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "/app/post/feed/upload/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String appUpload(HttpServletRequest request) {
        String body = getBody(request);
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Records> records = new ArrayList<>();
        try {
            ArrayList<UploadFeed> json = mapper.readValue(body, new TypeReference<ArrayList<UploadFeed>>() {
            });
            for (UploadFeed uploadFeed : json) {
                records.add(new Records(uploadFeed.getVideo(), uploadFeed.getCover(), uploadFeed.getTimestamp()));
            }
            new SaveFeed().saveFeed(records);
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Upload(" + records.size() + ") success...";
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
}
