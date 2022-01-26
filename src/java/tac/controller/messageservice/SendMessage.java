package tac.controller.messageservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendMessage {

    public static String sendMessage(long phone, int otp) {
        try {
            URL obj = new URL("https://2factor.in/API/V1/75c34bab-9735-11ea-9fa5-0200cd936042/SMS/+91" + phone + "/" + otp + "/Tac+Otp");
            HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
            BufferedReader bufferedReader;
            if (httpConnection.getResponseCode() == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
            return content.toString();
        } catch (Exception ex) {
            System.out.println(ex);
            return "{'status':500,'message':'Internal Server Error'}";
        }
    }
}
