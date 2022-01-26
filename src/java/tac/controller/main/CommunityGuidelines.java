package tac.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommunityGuidelines {

    @RequestMapping(value = "/community/guidelines/", method = RequestMethod.GET)
    public String CommunityGuidelinesView() {
        return "community_guidelines";
    }
}
