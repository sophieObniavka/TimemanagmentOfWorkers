package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.services.UserService;
import org.aspectj.util.FileUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/users")
public class UserController {
    private static final String USERS = "users";
    private static final String USER = "user";
    private static final String IMAGE = "image";
    private final UserService userService;

    @GetMapping("/homeAdmin")
    public  String adminMain(final Model model){
        return "homeAdmin";
    }

    @GetMapping()
    public  String usersAll(final Model model) {
        model.addAttribute("usersList", userService.fetchAllUsersFromDb().size());
        model.addAttribute("usersSize", userService.fetchAllUsersFromDb().isEmpty());

        Map<UserDto, String> users = new HashMap<>();

        for(UserDto user : userService.fetchAllUsersFromDb()){

                users.put(user, Base64.getEncoder().encodeToString(user.getImageUrl()));

        }

        if (model.getAttribute(USERS) == null) {
            model.addAttribute(USERS, users);
        }

        return USERS;
    }

    @GetMapping("/{id}")
    public String user(final Model model, final @PathVariable("id") String id) throws IOException {
        if (id == null && model.getAttribute(USER) == null) {
            model.addAttribute(USER, new UserDto());
            model.addAttribute(IMAGE,Base64.getEncoder().encodeToString(setDefaultImage()));
        } else {
            model.addAttribute(USER, userService.findUserById(Long.parseLong(id)));
            model.addAttribute(IMAGE,Base64.getEncoder().encodeToString(userService.findUserById(Long.parseLong(id)).getImageUrl()));
        }
        return "user";
    }

    @GetMapping("/profile/{id}")
    public String profileOfUser(final Model model, final @PathVariable("id") String id) throws IOException {
        if (id == null && model.getAttribute(USER) == null) {
            model.addAttribute(USER, new UserDto());
            model.addAttribute(IMAGE,Base64.getEncoder().encodeToString(setDefaultImage()));
        } else {
            model.addAttribute(USER, userService.findUserById(Long.parseLong(id)));
            model.addAttribute(IMAGE,Base64.getEncoder().encodeToString(userService.findUserById(Long.parseLong(id)).getImageUrl()));
        }
        return "profile";
    }

    @GetMapping("/create")
    public String createUser(final Model model) throws IOException {

        model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(setDefaultImage()));

        if (model.getAttribute(USER) == null) {
            model.addAttribute(USER, new UserDto());
        }

        return "user";
    }

    @PostMapping
    public String persistUser(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {

        if(bindingResult.hasErrors()){
            if (multipartFile.isEmpty()) {
                if (user.getId() == null) {
                    model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(setDefaultImage()));
                    user.setImageUrl(setDefaultImage());
                } else {
                    model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(user.getImageUrl()));
                }
            }
            else {
                model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(multipartFile.getBytes()));
                user.setImageUrl(multipartFile.getBytes());
            }


            return USER;
        }

        if(multipartFile.isEmpty()){
            if (user.getImageUrl().length == 0) {
                model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(setDefaultImage()));
                user.setImageUrl(setDefaultImage());
            }
            else {
                model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(user.getImageUrl()));
            }
        }
        else {
            model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(multipartFile.getBytes()));
            user.setImageUrl(multipartFile.getBytes());
        }

        userService.persistUserInDb(user);

        return "redirect:/" + USERS;
    }

    @GetMapping("/delete/{id}")
    public String removeUser(final @PathVariable("id") String id) {
        userService.dropUserFromDb(Long.parseLong(id));

        return "redirect:/" + USERS;
    }

    private byte[] setDefaultImage() throws IOException {
        return FileUtil.readAsByteArray(new File("src/main/resources/images/icon.png"));
    }

    @GetMapping("/profile")
    public String page(final Model model){
        return "profile";
    }

    @GetMapping("/page")
    public String pageP(final Model model){
        return "page";
    }

}
