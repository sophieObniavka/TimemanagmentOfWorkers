package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.data.Report;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.domain.VacationDto;
import obniavka.timemanagment.services.ReportService;
import obniavka.timemanagment.services.UserService;
import obniavka.timemanagment.services.VacationService;
import obniavka.timemanagment.utils.ReportMapper;
import obniavka.timemanagment.utils.UserMapper;
import org.aspectj.util.FileUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private static final String USERS = "users";
    private static final String USER = "user";
    private static final String CURRENT = "current";
    private static final String IMAGE = "image";
    private static final String REPORT = "report";
    private static final String VACATION = "vacation";

    private final UserService userService;
    private final ReportService reportService;
    private final VacationService vacationService;


    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    private User authentificatedUser(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            user = userService.findUserByEmail(userDetail.getUsername());
            model.addAttribute(CURRENT, user);
            model.addAttribute(VACATION, new VacationDto());
        }

        return user;
    }


    @GetMapping("/homeAdmin")
    public String homeAdmin(Model model) {
        authentificatedUser(model);
        return "homeAdmin";
    }


    @GetMapping("/users")
    public String usersAll(final Model model) throws IOException {
        authentificatedUser(model);
        model.addAttribute("usersList", userService.fetchAllUsersFromDb().size());
        model.addAttribute("usersSize", userService.fetchAllUsersFromDb().isEmpty());

        Map<UserDto, String> users = new HashMap<>();
        for (UserDto user : userService.fetchAllUsersFromDb()) {

            if (user.getImageUrl() == null || user.getImageUrl().length == 0) {
                users.put(user, Base64.getEncoder().encodeToString(setDefaultImage()));
            } else {
                users.put(user, Base64.getEncoder().encodeToString(user.getImageUrl()));
            }

        }

        if (model.getAttribute(USERS) == null) {
            model.addAttribute(USERS, users);
        }


        return USERS;
    }

    @GetMapping("/users/{id}")
    public String user(final Model model, final @PathVariable("id") String id) throws IOException {
        System.out.println(userService.findUserById(Long.parseLong(id)).getPassword());

        authentificatedUser(model);

        if (id == null && model.getAttribute(USER) == null) {
            model.addAttribute(USER, new UserDto());
            model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(setDefaultImage()));
        } else {
            model.addAttribute(USER, userService.findUserById(Long.parseLong(id)));
            model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(userService.findUserById(Long.parseLong(id)).getImageUrl()));
            model.addAttribute("reports", userService.findUserById(Long.parseLong(id)).getReports());
        }


        return "user";
    }

    @GetMapping("/users/profile/{id}")
    public String profileOfUser(final Model model, final @PathVariable("id") String id) throws IOException {

        authentificatedUser(model);

        if (id == null && model.getAttribute(USER) == null) {
            model.addAttribute(USER, new UserDto());
            model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(setDefaultImage()));
        } else {
            model.addAttribute(USER, userService.findUserById(Long.parseLong(id)));

            model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(userService.findUserById(Long.parseLong(id)).getImageUrl()));
        }
        return "profile";
    }

    @GetMapping("/users/create")
    public String createUser(final Model model) throws IOException {
        authentificatedUser(model);
        model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(setDefaultImage()));

        if (model.getAttribute(USER) == null) {
            model.addAttribute(USER, new UserDto());
        }

        return "user";
    }

    @PostMapping("/users")
    public String persistUser(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        authentificatedUser(model);

        if (userService.findUserById(user.getId()) != null) {
            if (user.getPassword() == null || user.getPassword().length() == 0) {
                userService.updateUser(user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getBirth(),
                        user.getHired(),
                        user.getPhoneNumber(),
                        user.getEmployeeId(),
                        user.getRole(),
                        user.getCountry(),
                        user.getCity(),
                        user.getStreet(),
                        user.getHouseNumber(),
                        user.getPostCode(),
                        user.getEmail(),
                        user.getImageUrl()

                );
            } else {
                if (bindingResult.hasErrors()) {
                    if (multipartFile.isEmpty()) {
                        if (user.getId() == null) {
                            model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(setDefaultImage()));
                            user.setImageUrl(setDefaultImage());
                        } else {
                            model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(user.getImageUrl()));
                        }
                    } else {
                        model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(multipartFile.getBytes()));
                        user.setImageUrl(multipartFile.getBytes());
                    }


                    return USER;
                }

                if (multipartFile.isEmpty()) {
                    if (user.getImageUrl().length == 0) {
                        model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(setDefaultImage()));
                        user.setImageUrl(setDefaultImage());
                    } else {
                        model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(user.getImageUrl()));
                    }
                } else {
                    model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(multipartFile.getBytes()));
                    user.setImageUrl(multipartFile.getBytes());
                }


                userService.persistUserInDb(user);
            }
        }
        return "redirect:/admin/" + USERS;
    }

    @GetMapping("/users/delete/{id}")
    public String removeUser(final @PathVariable("id") String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            model.addAttribute(CURRENT, userService.findUserByEmail(userDetail.getUsername()));
        }

        userService.dropUserFromDb(Long.parseLong(id));

        return "redirect:/admin/" + USERS;
    }

    private byte[] setDefaultImage() throws IOException {
        return FileUtil.readAsByteArray(new File("src/main/resources/images/icon.png"));
    }

    @GetMapping("/profile")
    public String page(final Model model) {
        return "profile";
    }

    @GetMapping("/report")
    public String report(final Model model) {

        User user = authentificatedUser(model);
            model.addAttribute("reports", user.getReports());
            model.addAttribute("reportNew", new ReportDto());
            model.addAttribute("reportOnChange", new ReportDto());


        return "report";
    }

    @GetMapping("/report/selectedDate/{month}")
    public String pageM(final Model model, @PathVariable("month") String month) {

        User user = authentificatedUser(model);

            model.addAttribute("reportOnChange", new ReportDto());
            model.addAttribute("someBean", month);
            model.addAttribute("reports", user.getReports().stream().filter(x -> x.getWorkDay().getMonthValue() == Integer.parseInt(month.substring(5)) && x.getWorkDay().getYear() == Integer.parseInt(month.substring(0, 4))).toList());
            model.addAttribute("reportNew", new ReportDto());


        return REPORT;
    }


    @PostMapping("/report")
    public String persistReport(@Valid @ModelAttribute("report") ReportDto report, Model model) {
        User user =  authentificatedUser(model);

        report.setUser(mapper.map(user));
        reportService.persistReportInDb(report);

        model.addAttribute("reportOnChange", new ReportDto());
        model.addAttribute("reports", user.getReports());
        model.addAttribute("reportNew", new ReportDto());

        return "redirect:/admin/" + REPORT;
    }

    @PostMapping("/report/edit")
    public String popUpSave(final Model model, @ModelAttribute("reportOnChange") ReportDto report) {
        User user = authentificatedUser(model);
        System.out.println("Found: " + report.getBegin());
            ReportDto found = reportService.findReportById(report.getId());

            found.setWorkDay(report.getWorkDay());
            found.setBegin(report.getBegin());
            found.setEnd(report.getEnd());
            found.setPause(report.getPause());
            found.setUser(mapper.map(user));
            found.setDescription(report.getDescription());

            reportService.persistReportInDb(found);

        return "redirect:/admin/report";
    }

    @GetMapping("/report/delete/{id}")
    public String removeReport(final @PathVariable("id") String id, Model model) {
        authentificatedUser(model);

        reportService.dropReportFromDb(Long.parseLong(id));

        return "redirect:/admin/" + REPORT;
    }


    @PostMapping("/vacation")
    public String createVacation(@Valid @ModelAttribute("vacation") VacationDto vacation, Model model) throws IOException {
        User user = authentificatedUser(model);

        System.out.println(vacation);
        user.setVacationDays(user.getVacationDays() - getWorkingDaysBetweenTwoDates(vacation.getBegin(), vacation.getEnd()));

        vacation.setUser(mapper.map(user));
        userService.persistUserInDb(mapper.map(user));
        vacationService.persistVacationInDB(vacation);


        return"redirect:/admin/homeAdmin";
    }

    private int getWorkingDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
        int businessDays = 0;
        LocalDate d = startDate;
        while (d.isBefore(endDate)) {
            DayOfWeek dw = d.getDayOfWeek();
            if (dw != DayOfWeek.SATURDAY && dw != DayOfWeek.SUNDAY) {
                businessDays++;
            }
            d = d.plusDays(1);
        }
        return businessDays;
    }
}
