package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import static obniavka.timemanagment.utils.Constants.USER;


@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class ProfileController {
    private final UserService userService;

    @GetMapping("/profile")
    public String updateProfile(final Model model, @ModelAttribute("current") UserDto user){
        model.addAttribute("origin", "/profile");
        model.addAttribute(USER, userService.findUserByEmail(user.getEmail()));
        return USER;
    }

    @PostMapping("/profile")
    public String persistUser(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            user.setImageUrl(multipartFile.getBytes());
        }

        if (Objects.requireNonNull(user.getImageUrl()).length != 0)
            user.setConvertedImage(Base64.getEncoder().encodeToString(user.getImageUrl()));

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return USER;
        }

        if (userService.checkIfEmailAlreadyExist(user, user.getEmail())) {
            model.addAttribute("user", user);
            model.addAttribute("doesExist", true);
            return USER;
        }
        userService.persistUserInDb(user);

        return "redirect:/profile";
    }
}
