package tac.controller.app.notifications;

import hibernate.query.NotificationsQuery;
import hibernate.query.ProfileQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.ActivityTypeConstants;
import tac.model.ProfileModel;

public class FCM {

    private final static String FCM_URL = "https://fcm.googleapis.com/fcm/send";
    private final static String SERVER_KEY = "AAAAicKjMOc:APA91bHtWj149GsGAE5LYoETeiMWey4CnqZiSoPKC4L2C6GTq8rxH-LXQNY4DBm9LjBIqN1nU5r2J4Dc4MsRsBn-cdFOecYRnMuuSt0QLNp61YoWkG6LWNyqzVUopUVfi0QspsWD05EM";

    public static void sendFCMNotification(long notificationTo, long notificationBy, int notifyId, String title, byte type, String contentImage, String priority) {
        ProfileModel profileModel = new ProfileQuery().getProfileInfo(String.valueOf(notificationBy), 0);
        NotificationsQuery notificationsQuery = new NotificationsQuery();
        sendFCMNotificationMulti(notificationsQuery.getNotificationsTokens(notificationTo), notifyId, title, profileModel.getUserName() + " " + ActivityTypeConstants.notificationText(type), profileModel.getProfilePic(), contentImage, priority);
    }
    
    public static void sendFCMNotification(List<Long> notificationTos, long notificationBy, int notifyId, String title, byte type, String contentImage, String priority) {
        ProfileModel profileModel = new ProfileQuery().getProfileInfo(String.valueOf(notificationBy), 0);
        NotificationsQuery notificationsQuery = new NotificationsQuery();
        sendFCMNotificationMulti(notificationsQuery.getNotificationsTokens(notificationTos), notifyId, title, profileModel.getUserName() + " " + ActivityTypeConstants.notificationText(type), profileModel.getProfilePic(), contentImage, priority);
    }

    public static void cancelFCMNotification(long userId, int notifyId) {
        NotificationsQuery notificationsQuery = new NotificationsQuery();
        cancelFCMNotification(notificationsQuery.getNotificationsTokens(userId), notifyId);
    }

    public static void sendFCMNotification(String tokenId, String notifyId, String title, String message, String profilePic, String contentImage, String priority) {
        try {
            // Create URL instance.
            URL url = new URL(FCM_URL);
            // create connection.
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //set method as POST or GET
            conn.setRequestMethod("POST");
            //pass FCM server key
            conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);
            //Specify Message Format
            conn.setRequestProperty("Content-Type", "application/json");
            //Create JSON Object & pass value
            JSONObject data = new JSONObject();
            data.put("notify_id", notifyId);
            data.put("title", title);
            data.put("message", message);
            data.put("profile_pic", profilePic);
            data.put("content_image", contentImage);
            data.put("priority", priority);
            JSONObject json = new JSONObject();
            json.put("to", tokenId.trim());
            json.put("data", data);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            int status = 0;
            status = conn.getResponseCode();
            if (status != 0) {
                if (status == 200) {
                    //SUCCESS message
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    System.out.println("Android Notification Response : " + reader.readLine());
                } else if (status == 401) {
                    //client side error
                    System.out.println("Notification Response : TokenId : " + tokenId + " Error occurred :");
                } else if (status == 501) {
                    //server side error
                    System.out.println("Notification Response : [ errorCode=ServerError ] TokenId : " + tokenId);
                } else if (status == 503) {
                    //server side error
                    System.out.println("Notification Response : FCM Service is Unavailable " + tokenId + " tokenId");
                }
            }
        } catch (MalformedURLException mlfexception) {
            // Prototcal Error
            System.out.println("Error occurred while sending push Notification!.." + mlfexception.getMessage());
        } catch (Exception mlfexception) {
            //URL problem
            System.out.println("Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());
        }
    }

    public static void sendFCMNotificationMulti(List<String> putIds2, int notifyId, String title, String message, String profilePic, String contentImage, String priority) {
        try {
            // Create URL instance.
            URL url = new URL(FCM_URL);
            // create connection.
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //set method as POST or GET
            conn.setRequestMethod("POST");
            //pass FCM server key
            conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);
            //Specify Message Format
            conn.setRequestProperty("Content-Type", "application/json");
            //Create JSON Object & pass value
            JSONArray regId = new JSONArray();
            JSONObject objData = new JSONObject();
            JSONObject data = new JSONObject();
            for (String putIds21 : putIds2) {
                regId.add(putIds21);
            }
            data.put("notify_id", notifyId);
            data.put("title", title);
            data.put("message", message);
            data.put("profile_pic", profilePic);
            data.put("content_image", contentImage);
            data.put("priority", priority);
            objData.put("registration_ids", regId);
            objData.put("data", data);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(objData.toString());
            wr.flush();
            int status;
            status = conn.getResponseCode();
            if (status != 0) {
                if (status == 200) {
                    //SUCCESS message
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    System.out.println("Android Notification Response : " + reader.readLine());
                } else if (status == 401) {
                    //client side error
                    System.out.println("Notification Response : TokenId : Error occurred :");
                } else if (status == 501) {
                    //server side error
                    System.out.println("Notification Response : [ errorCode=ServerError ]  TokenId :");
                } else if (status == 503) {
                    //server side error
                    System.out.println("Notification Response : FCM Service is Unavailable  TokenId : ");
                }
            }
        } catch (MalformedURLException mlfexception) {
            // Prototcal Error
            System.out.println("Error occurred while sending push Notification!.." + mlfexception.getMessage());
        } catch (IOException mlfexception) {
            //URL problem
            System.out.println("Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());
        } catch (Exception exception) {
            //General Error or exception.
            System.out.println("Error occurred while sending push Notification!.." + exception.getMessage());
        }
    }

    public static void cancelFCMNotification(List<String> putIds2, int notifyId) {
        try {
            // Create URL instance.
            URL url = new URL(FCM_URL);
            // create connection.
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //set method as POST or GET
            conn.setRequestMethod("POST");
            //pass FCM server key
            conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);
            //Specify Message Format
            conn.setRequestProperty("Content-Type", "application/json");
            //Create JSON Object & pass value
            JSONArray regId = new JSONArray();
            JSONObject objData = new JSONObject();
            JSONObject data = new JSONObject();
            for (String putIds21 : putIds2) {
                regId.add(putIds21);
            }
            data.put("cancel_id", notifyId);
            objData.put("registration_ids", regId);
            objData.put("data", data);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(objData.toString());
            wr.flush();
            int status;
            status = conn.getResponseCode();
            if (status != 0) {
                if (status == 200) {
                    //SUCCESS message
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    System.out.println("Android Notification Response : " + reader.readLine());
                } else if (status == 401) {
                    //client side error
                    System.out.println("Notification Response : TokenId : Error occurred :");
                } else if (status == 501) {
                    //server side error
                    System.out.println("Notification Response : [ errorCode=ServerError ]  TokenId :");
                } else if (status == 503) {
                    //server side error
                    System.out.println("Notification Response : FCM Service is Unavailable  TokenId : ");
                }
            }
        } catch (MalformedURLException mlfexception) {
            // Prototcal Error
            System.out.println("Error occurred while sending push Notification!.." + mlfexception.getMessage());
        } catch (IOException mlfexception) {
            //URL problem
            System.out.println("Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());
        } catch (Exception exception) {
            //General Error or exception.
            System.out.println("Error occurred while sending push Notification!.." + exception.getMessage());
        }
    }
}
