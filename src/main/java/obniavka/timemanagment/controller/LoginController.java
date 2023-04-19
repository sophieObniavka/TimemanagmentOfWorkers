package obniavka.timemanagment.controller;


import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class LoginController {
    public static final String TITLE = "title";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String LOGOUT = "logout";
    public static final String ERROR = "error";
    public static final String ERROR_NO_USER = "NO.USER";
    public static final String ERROR_NO_PW = "NO.PW";
    public static final String ERROR_BAD_CREDENTIALS = "BAD.CREDENTIALS";

    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String username, @RequestParam(required = false) String error, @RequestParam(required = false) String logout){
       // userService.updatePassword("sofiia", 4L);

        model.addAttribute(TITLE, "Login - Page");
        model.addAttribute(USERNAME, username);
        model.addAttribute(ERROR, error);
        model.addAttribute(LOGOUT, logout);
        return "login";
    }

    @PostMapping("/login/failure")
    public RedirectView fail(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String username = request.getParameter(USERNAME);
        redirectAttributes.addAttribute(USERNAME, username);
        if (!StringUtils.hasText(username)) {
            redirectAttributes.addAttribute(ERROR, ERROR_NO_USER);
        } else if (!StringUtils.hasText(request.getParameter(PASSWORD))) {
            redirectAttributes.addAttribute(ERROR, ERROR_NO_PW);
        } else {
            redirectAttributes.addAttribute(ERROR, ERROR_BAD_CREDENTIALS);
        }
        return new RedirectView("/login");
    }


}
