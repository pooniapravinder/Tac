package tac.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PrivacyPolicy {

    @RequestMapping(value = "/privacy/policy/", method = RequestMethod.GET)
    public String PrivacyPolicyView() {
        return "privacy_policy";
    }
}
