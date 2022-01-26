package tac.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Terms {

    @RequestMapping(value = "/terms/", method = RequestMethod.GET)
    public String TermsView() {
        return "terms";
    }
}
