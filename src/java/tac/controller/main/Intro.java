package tac.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Intro {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String IntroView() {
        return "intro";
    }
}
