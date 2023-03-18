package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.domain.VacationDto;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vacation")
public class VacationController {
    private static final String VACATION = "vacation";



//    @PostMapping
//    public String persistUser(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {
//
//        if(bindingResult.hasErrors()){
//            if (multipartFile.isEmpty()) {
//                if (user.getId() == null) {
//                    model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(setDefaultImage()));
//                    user.setImageUrl(setDefaultImage());
//                } else {
//                    model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(user.getImageUrl()));
//                }
//            }
//            else {
//                model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(multipartFile.getBytes()));
//                user.setImageUrl(multipartFile.getBytes());
//            }
//
//
//            return USER;
//        }
//
//        if(multipartFile.isEmpty()){
//            if (user.getImageUrl().length == 0) {
//                model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(setDefaultImage()));
//                user.setImageUrl(setDefaultImage());
//            }
//            else {
//                model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(user.getImageUrl()));
//            }
//        }
//        else {
//            model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(multipartFile.getBytes()));
//            user.setImageUrl(multipartFile.getBytes());
//        }
//
//        userService.persistUserInDb(user);
//
//        return "redirect:/" + USERS;
//    }
}
