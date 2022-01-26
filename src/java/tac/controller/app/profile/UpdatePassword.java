package tac.controller.app.profile;

import hibernate.query.LoginSignupQuery;
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

@Controller
public class UpdatePassword {

    @RequestMapping(value = "/rest_api/app/update/password/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appUpdatePassword(@RequestParam(value = "password", required = true) String password, @RequestParam(value = "confPassword", required = true) String confPassword, @RequestParam(value = "token", required = true) String token, @RequestParam(value = "user", required = true) long user, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        if (!SafeToken.isValidToken(user, token)) {
            map.put("error", "Token has expired. Please restart app and try again to change password");
            return map;
        }
        if (password == null || password.length() < 5 || password.length() > 16) {
            map.put("error", "Password must be 5 to 16 characters long.");
            return map;
        } else if (confPassword == null || !password.equals(confPassword)) {
            map.put("error", "Password and confirm password do not match.");
            return map;
        }
        new LoginSignupQuery().updatePassword(SCryptUtil.scrypt(password, 16, 16, 16), user);
        map.put("success", "true");
        return map;
    }
}
