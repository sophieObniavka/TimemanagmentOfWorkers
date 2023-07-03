package obniavka.timemanagment.controller;

import obniavka.timemanagment.domain.TaskDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.helper.Tabs;
import obniavka.timemanagment.services.TaskService;
import obniavka.timemanagment.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ControllerAdvice
public class FragmentsController {
    private final UserService userService;
    private final TaskService taskService;


    public FragmentsController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @ModelAttribute("tabsMenu")
    public Collection<Tabs> menuTabs() {
        return Tabs.listMenuTabs();
    }

    @ModelAttribute("tabsProfile")
    public Collection<Tabs> profileTabs() {
        return Tabs.listProfileTabs();
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
    public String setCurrentMonthAndYear() {
        return LocalDate.now().getYear() + "-" + String.format("%02d", LocalDate.now().getMonthValue());
    }

    @ModelAttribute("tasksWithDeadline")
    public List<TaskDto> undoneTasks(@ModelAttribute("current") UserDto user){
        if (user != null) {
            UserDto userDto = userService.findUserByEmail(user.getEmail());
           // System.out.println(taskService.fetchAllTasksBetweenYesterdayAndTomorrow(userDto).size());
            return taskService.fetchAllTasksBetweenYesterdayAndTomorrow(userDto);
        }
        return new ArrayList<>();
    }


}
