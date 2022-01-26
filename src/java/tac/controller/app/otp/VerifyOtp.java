package tac.controller.app.otp;

import cookies.set.get.SaveLogin;
import cookies.set.get.VerifyLogin;
import hibernate.mapping.Accounts;
import hibernate.mapping.Otp;
import hibernate.mapping.Profile;
import hibernate.query.LoginSignupQuery;
import hibernate.query.OtpQuery;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import password.encryption.decryption.SCryptUtil;
import utils.SafeToken;
import tac.model.SignupModel;

@Controller
public class VerifyOtp {

    @RequestMapping(value = "/rest_api/app/verify/send/otp/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appVerifySendOtp(@RequestParam(value = "phone", required = true) String phone, @RequestParam(value = "flow", required = false) String flow, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (verifyLogin.isLoggedIn()) {
            map.put("error", "Already logged in");
            return map;
        }
        if ((phone == null) || (!phone.matches("^(?=.*[0-9]).{10}$"))) {
            map.put("error", "Enter a valid phone number");
            return map;
        } else {
            if (flow == null && new LoginSignupQuery().getAccount(phone) != null) {
                map.put("error", "The phone number is already registered.");
                return map;
            } else if (flow != null && flow.equals("forgot_password") && new LoginSignupQuery().getAccount(phone) == null) {
                map.put("error", "The phone number does not exist.");
                return map;
            }
        }
        OtpQuery otpQuery = new OtpQuery();
        Otp otp = otpQuery.getOtp(Long.parseLong(phone));
        if (otp != null) {
            if (otp.getTimestmp() > (System.currentTimeMillis() - (1000 * 60))) {
                map.put("error", "Please try again after " + (((otp.getTimestmp()) - (System.currentTimeMillis() - (1000 * 60))) / 1000) + " seconds");
            } else {
                SendOtp.sendOTP(Long.parseLong(phone));
                map.put("success", true);
            }
        } else {
            SendOtp.sendOTP(Long.parseLong(phone));
            map.put("success", true);
        }
        map.put("phone", "+91" + SendOtp.replacedMobileNo(Long.parseLong(phone)));
        return map;
    }

    @RequestMapping(value = "/rest_api/app/verify/otp/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appVerifyOtp(SignupModel signupModel, @RequestParam(value = "phone", required = true) long phone, @RequestParam(value = "otp", required = true) String otpInput, @RequestParam(value = "flow", required = false) String flow, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (verifyLogin.isLoggedIn()) {
            map.put("error", "Already logged in");
            return map;
        }
        OtpQuery otpQuery = new OtpQuery();
        Otp otp = otpQuery.getOtp(phone);
        if (otp == null) {
            map.put("error", "Something went wrong. Please try again");
            return map;
        } else if ((otpInput == null) || (!otpInput.matches("^(?=.*[0-9]).{6}$"))) {
            map.put("error", "Enter 6 digits valid OTP");
            return map;
        } else if ((otp.getTries() > 5) || (otp.getTimestmp() < (System.currentTimeMillis() - (1000 * 60 * 5)))) {
            map.put("error", "OTP is expired. Please resend OTP and try again");
            return map;
        } else if (otp.getOtp() != (Integer.parseInt(otpInput))) {
            otpQuery.updateOtpTries(otp);
            map.put("error", "You have entered wrong OTP");
            return map;
        }
        if (flow != null && flow.equals("forgot_password")) {
            Accounts accounts = new LoginSignupQuery().getAccount(String.valueOf(phone));
            if (accounts == null) {
                map.put("error", "Something went wrong");
            } else {
                map.put("token", SafeToken.generateToken(accounts.getUserId()));
                map.put("user", accounts.getUserId());
                map.put("success", "true");
            }
            return map;
        }
        if (signupModel.getUsername() == null) {
            map.put("error", "The username cannot be empty.");
            return map;
        } else if (signupModel.getUsername().length() < 2) {
            map.put("error", "The username " + signupModel.getUsername() + " is not available.");
            return map;
        } else if (signupModel.getUsername().length() > 20) {
            map.put("error", "Enter username under 20 characters.");
            return map;
        } else if ((!signupModel.getUsername().matches("^[a-zA-Z0-9_.]*$"))) {
            map.put("error", "Usernames can only use letters, numbers, underscores and periods.");
            return map;
        } else if (new LoginSignupQuery().getAccount(signupModel.getUsername()) != null) {
            map.put("error", "The username " + signupModel.getUsername() + " is not available.");
            return map;
        } else if (new LoginSignupQuery().getAccount(signupModel.getPhone()) != null) {
            map.put("error", "The phone number is already registered.");
            return map;
        } else if (signupModel.getFullname() == null || signupModel.getFullname().length() < 2 || signupModel.getFullname().length() > 30) {
            map.put("error", "Name must have 2 to 30 characters.");
            return map;
        } else if (!signupModel.getFullname().matches("^(([A-Za-z\\s]){2,30})*$")) {
            map.put("error", "Name can only use letters and spaces.");
            return map;
        } else if (signupModel.getPassword() == null || signupModel.getPassword().length() < 5 || signupModel.getPassword().length() > 16) {
            map.put("error", "Password must be 5 to 16 characters long.");
            return map;
        } else if (signupModel.getGender() == null || (!signupModel.getGender().toLowerCase().equals("male") && !signupModel.getGender().toLowerCase().equals("female"))) {
            map.put("error", "Not a valid gender.");
            return map;
        }
        LoginSignupQuery loginSignupQuery = new LoginSignupQuery();
        Accounts acc = new Accounts(Long.parseLong(signupModel.getPhone()), signupModel.getUsername().toLowerCase(), SCryptUtil.scrypt(signupModel.getPassword(), 16, 16, 16), false);
        Profile profile = new Profile(acc, signupModel.getFullname(), (byte) (signupModel.getGender().toLowerCase().equals("male") ? 1 : 2), 0, 0, 0, false);
        long userId = loginSignupQuery.addAccount(profile);
        if (userId == 0) {
            map.put("error", "Something went wrong. Please restart app and try to login.");
            return map;
        }
        new SaveLogin(response, userId).saveCookie();
        map.put("success", "true");
        return map;
    }
}
