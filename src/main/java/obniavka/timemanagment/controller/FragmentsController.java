package obniavka.timemanagment.controller;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentsController {
    private final UserService userService;

    public FragmentsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/header")
    public String assignHeader(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();

            User user = userService.findUserByEmail(userDetail.getUsername());


            if (userDetail != null) {
                model.addAttribute("current", user);
            }
        }

        return "header.html";
    }
}
