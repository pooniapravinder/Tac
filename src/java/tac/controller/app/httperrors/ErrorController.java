package tac.controller.app.httperrors;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping(value = "/error/http/", method = RequestMethod.GET)
    public ModelAndView httpErrorPages(HttpServletRequest request) {
        ModelAndView webPage = new ModelAndView("httperrors");
        int httpErrorCode = (int) request.getAttribute("javax.servlet.error.status_code");
        webPage.addObject("error", httpErrorCode);
        return webPage;
    }

}
