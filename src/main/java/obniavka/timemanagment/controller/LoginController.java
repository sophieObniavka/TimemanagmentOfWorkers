package obniavka.timemanagment.controller;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.services.UserService;
import obniavka.timemanagment.utils.UserMapper;
import org.aspectj.util.FileUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
    private static final String USER = "user";
    @Autowired
    private UserService userService;
    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@RequestParam String email, @RequestParam String password, Model model) {
        UserDetails userDetails = userService.loadUserByUsername(email);

        System.out.println("Pyzda");
        System.out.println(userDetails.getPassword());
        System.out.println("Does it match? " + userDetails.getPassword().matches(password));
        System.out.println("Does it equal? " + userDetails.getPassword().equals(password));
        if (userDetails != null && userDetails.getPassword().equals(password)) {

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);



            //model.addAttribute("user", user);
            return "redirect:/admin/homeAdmin";
        } else {

            return "login";
        }
    }

//    @GetMapping("/page")
//    public String loginPage(final Model model) {
//
//
//        return "page";
//    }


}
