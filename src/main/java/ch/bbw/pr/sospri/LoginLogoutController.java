package ch.bbw.pr.sospri;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginLogoutController {
    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }
    @RequestMapping("/logout")
    public String logout(Model model) {
        return "logout";
    }
}
