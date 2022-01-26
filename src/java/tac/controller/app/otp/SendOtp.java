package tac.controller.app.otp;

import hibernate.mapping.Otp;
import hibernate.query.OtpQuery;
import java.util.Random;
import tac.controller.messageservice.SendMessage;

public class SendOtp {

    public static void sendOTP(long mobileNo) {
        OtpQuery otpQuery = new OtpQuery();
        Otp otp = otpQuery.getOtp(mobileNo);
        Otp newOTP = new Otp(mobileNo, generateOTP(), (byte) 0, System.currentTimeMillis());
        if (otp == null) {
            otpQuery.saveOTP(newOTP);
        } else {
            otpQuery.updateOtp(newOTP);
        }
        //SendMessage sendMessage = new SendMessage();
        //String msg = "Your one time password (OTP) for Wookes Tac verification is : " + newOTP.getOtp();
        SendMessage.sendMessage(mobileNo, newOTP.getOtp());
    }

    public static String replacedMobileNo(long mobileNo) {
        char ch = 'X';
        StringBuilder result = new StringBuilder(String.valueOf(mobileNo));
        for (int i = 2; i < 8; i++) {
            result.setCharAt(i, ch);
        }
        return result.toString();
    }

    private static int generateOTP() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}
