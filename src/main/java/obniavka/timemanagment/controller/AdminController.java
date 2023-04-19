package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.SickLeaveDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.domain.VacationDto;
import obniavka.timemanagment.helper.AmountOfDays;
import obniavka.timemanagment.services.SickLeavesService;
import obniavka.timemanagment.services.UserService;
import obniavka.timemanagment.services.VacationService;
import org.aspectj.util.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private static final String USERS = "users";
    private static final String USER = "user";
    private static final String IMAGE = "image";

    private static final String VACATIONS = "vacations";
    private static final String SICKLEAVES = "sickleaves";
    private static final String PAGE = "page";

    private final UserService userService;
    private final VacationService vacationService;
    private final SickLeavesService sickLeavesService;

    AmountOfDays amountOfDays = new AmountOfDays();

    @GetMapping("/homeAdmin")
    public String homeAdmin(Model model,@RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);
        model.addAttribute(VACATIONS,vacationService.selectAllUnconfirmedVacations(PageRequest.of(currentPage-1, 8)));
        return "homeAdmin";
    }

    @GetMapping("/bad")
    public String admin(Principal principal)
    {
        return "bad";
    }

    @GetMapping("/users")
    public String usersAll(final Model model){
        model.addAttribute("usersList", userService.fetchAllUsersFromDb().size());
        model.addAttribute("usersSize", userService.fetchAllUsersFromDb().isEmpty());

        model.addAttribute(USERS, userService.fetchAllUsersFromDb());

        return USERS;
    }

    @GetMapping("/users/{id}")
    public String user(final Model model, final @PathVariable("id") String id) throws IOException {
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
        model.addAttribute(IMAGE, Base64.getEncoder().encodeToString(setDefaultImage()));

        if (model.getAttribute(USER) == null) {
            model.addAttribute(USER, new UserDto());
        }

        return "user";
    }

    @PostMapping("/users")
    public String persistUser(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (userService.findUserById(user.getId()) != null) {
            if (user.getPassword().length() == 0) {
                userService.updateUser(user);
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


    @PostMapping("/vacation/{id}")
    public String confirmVacation( @PathVariable("id") String id){
        VacationDto vacation = vacationService.findVacationById(Long.parseLong(id));
        UserDto user = vacation.getUser();

        if(!vacation.isAtOwnExpense()) {
            user.setVacationDays(user.getVacationDays() - amountOfDays.getWorkingDaysBetweenTwoDates(vacation.getBegin(), vacation.getEnd()));
        }

        vacation.setConfirmed(true);
        vacation.setCheckedByUser(user.getFirstName() + " " + user.getLastName() );

        userService.updateUser(user);
        vacationService.persistVacationInDB(vacation);


        return"redirect:/admin/vacations/active";
    }

    @PostMapping("/sickleave/{id}")
    public String confirmSickleave( @PathVariable("id") String id){
        SickLeaveDto sickLeave = sickLeavesService.findSickLeaveById(Long.parseLong(id));
        UserDto user = sickLeave.getUser();
        if(!sickLeave.isAtOwnExpense()) {
            user.setVacationDays(user.getVacationDays() - amountOfDays.getWorkingDaysBetweenTwoDates(sickLeave.getBegin(), sickLeave.getEnd()));
        }

        sickLeave.setConfirmed(true);
        sickLeave.setCheckedByUser(user.getFirstName() + " " + user.getLastName() );

        userService.updateUser(user);
        sickLeavesService.persistSickLeaveInDB(sickLeave);


        return"redirect:/admin/sickleaves/active";
    }


    @PostMapping("/vacation/reject/{id}")
    public String rejectVacation( @ModelAttribute("comment") String comment, @PathVariable("id") String id,  @ModelAttribute("current") UserDto user) {

        VacationDto vacation = vacationService.findVacationById(Long.parseLong(id));

        vacation.setConfirmed(false);
        vacation.setComment(comment);
        vacation.setCheckedByUser(user.getFirstName() + " " + user.getLastName() );

        vacationService.persistVacationInDB(vacation);


        return"redirect:/admin/vacations/active";
    }

    @PostMapping("/sickleave/reject/{id}")
    public String rejectSickLeave( @ModelAttribute("comment") String comment, @PathVariable("id") String id,  @ModelAttribute("current") UserDto user) {

        SickLeaveDto sickLeave = sickLeavesService.findSickLeaveById(Long.parseLong(id));

        sickLeave.setConfirmed(false);
        sickLeave.setComment(comment);
        sickLeave.setCheckedByUser(user.getFirstName() + " " + user.getLastName() );

        sickLeavesService.persistSickLeaveInDB(sickLeave);


        return"redirect:/admin/sickleaves/active";
    }

    @GetMapping("/vacations/active")
    public String getActiveVacations(Model model, @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);


        model.addAttribute("colorActive", "#632673");
        model.addAttribute("colorClosed", "black");

        Page<VacationDto> vacations = vacationService.selectAllUnconfirmedVacations(PageRequest.of(currentPage-1, 8));

        model.addAttribute(VACATIONS, vacations);
        model.addAttribute(PAGE, vacations);

        int totalPages = vacations.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("currentPage", currentPage);

        return "vacationsActive";
    }

    @GetMapping("/sickleaves/active")
    public String getActiveSickleaves(Model model, @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(1);


        model.addAttribute("colorActive", "#632673");
        model.addAttribute("colorClosed", "black");

        Page<SickLeaveDto> sickLeaves = sickLeavesService.selectAllUncofirmedSickLeaves(PageRequest.of(currentPage-1, 8));
        model.addAttribute(SICKLEAVES, sickLeaves);
        model.addAttribute(PAGE, sickLeaves);

        int totalPages = sickLeaves.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("currentPage", currentPage);

        return "sickleavesActive";
    }

    @GetMapping("/vacations/closed")
    public String getClosedVacations(Model model, @RequestParam("page") Optional<Integer> page){

        int currentPage = page.orElse(1);


        model.addAttribute("colorActive", "black");
        model.addAttribute("colorClosed", "#632673");

        Page<VacationDto> vacations = vacationService.selectAllConfirmedVacations(PageRequest.of(currentPage-1, 6));

        model.addAttribute(VACATIONS, vacations);
        model.addAttribute(PAGE, vacations);

        int totalPages = vacations.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("currentPage", currentPage);
        return "vacationsClosed";
    }

    @GetMapping("/sickleaves/closed")
    public String getClosedSickleaves(Model model, @RequestParam("page") Optional<Integer> page){

        int currentPage = page.orElse(1);


        model.addAttribute("colorActive", "black");
        model.addAttribute("colorClosed", "#632673");

        Page<SickLeaveDto> sickleaves = sickLeavesService.selectAllCofirmedSickLeaves(PageRequest.of(currentPage-1, 6));

        model.addAttribute(SICKLEAVES, sickleaves);
        model.addAttribute(PAGE, sickleaves);

        int totalPages = sickleaves.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("currentPage", currentPage);
        return "sickleavesClosed";
    }
}
