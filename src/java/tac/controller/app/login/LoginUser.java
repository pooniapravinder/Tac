package tac.controller.app.login;

import cookies.set.get.SaveLogin;
import cookies.set.get.VerifyLogin;
import hibernate.mapping.Accounts;
import hibernate.query.LoginSignupQuery;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import password.encryption.decryption.SCryptUtil;
import tac.model.LoginModel;

@Controller
public class LoginUser {

    @RequestMapping(value = "/rest_api/app/login/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    Map<String, Object> appLogin(LoginModel loginModel, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        VerifyLogin verifyLogin = new VerifyLogin(request);
        if (verifyLogin.isLoggedIn()) {
            map.put("error", "Already logged in");
            return map;
        }
        if ((loginModel.getUsernamePhone() == null) || (loginModel.getUsernamePhone().isEmpty()) || ((!loginModel.getUsernamePhone().matches("^(?=.*[0-9]).{10}$")) && (!loginModel.getUsernamePhone().matches("^[a-zA-Z0-9_.]*$")))) {
            map.put("error", "Invalid username or phone");
            return map;
        }
        LoginSignupQuery loginSignupQuery = new LoginSignupQuery();
        Accounts account = loginSignupQuery.getAccount(loginModel.getUsernamePhone());
        if (account == null) {
            map.put("error", "User does not exist, please signup");
            return map;
        } else if(account.isIsBlocked()){
            map.put("error", "Account has been blocked");
            return map;
        }
        boolean isAuthenticated = ((loginModel.getPassword() != null) && (!loginModel.getPassword().isEmpty())) ? SCryptUtil.check(loginModel.getPassword(), account.getPassword()) : false;
        if (!isAuthenticated) {
            map.put("error", "You have entered incorrect password");
        } else {
            new SaveLogin(response, account.getUserId()).saveCookie();
            map.put("success", "true");
        }
        return map;
    }
}
