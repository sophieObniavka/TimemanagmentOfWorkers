package obniavka.timemanagment.controller;

import obniavka.timemanagment.domain.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Base64;

@Controller
public class LoginController {
    private static final String USER = "user";
    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @GetMapping("/doLogin")
    public String loginUser(final Model model) throws IOException {
        if (model.getAttribute(USER) == null) {
            model.addAttribute(USER, new UserDto());
        }

        return "user";
    }
}
