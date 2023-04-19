package obniavka.timemanagment.controller;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.helper.Tabs;
import obniavka.timemanagment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collection;

@ControllerAdvice
public class FragmentsController {
    private final UserService userService;

    @Autowired
    private HttpServletRequest request;

    public FragmentsController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("tabsMenu")
    public Collection<Tabs> managementTabs() {
        Tabs.setActiveTab(request.getRequestURI());
        return Tabs.listMenuTabs();
    }

    @ModelAttribute("current")
    public UserDto assignHeader(Model model){
        UserDto user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();

            user = userService.findUserByEmail(userDetail.getUsername());

            if (userDetail != null) {
                model.addAttribute("current", user);
            }
        }
        return user;
    }

    @ModelAttribute("selected")
    public String setCurrentMonthAndYear(Model model) {
        return LocalDate.now().getYear() + "-" + String.format("%02d", LocalDate.now().getMonthValue());
    }

}
