package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.domain.VacationDto;
import obniavka.timemanagment.services.UserService;
import obniavka.timemanagment.services.VacationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vacations")
public class VacationController {
    private static final String VACATION = "vacation";
    private static final String VACATIONS = "vacations";

    private final VacationService vacationService;
    private final UserService userService;


    @GetMapping("")
    public String getVacations( Model model, @RequestParam("page") Optional<Integer> page,  @ModelAttribute("current") UserDto user){
        int currentPage = page.orElse(1);

        Page<VacationDto> vacations = vacationService.findAllVacationsByUser(user, PageRequest.of(currentPage-1, 8));

        model.addAttribute("left", user.getVacationDays());
        model.addAttribute(VACATIONS, vacations);
        model.addAttribute(VACATION, new VacationDto());
        model.addAttribute("page", vacations);

        int totalPages = vacations.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("currentPage", currentPage);

        return VACATIONS;
    }

    @PostMapping("")
    public String saveVacation(@Valid @ModelAttribute("vacation") VacationDto vacation, @ModelAttribute("current") UserDto user){
        vacation.setUser(userService.findUserByEmail(user.getEmail()));
        vacationService.persistVacationInDB(vacation);
        return"redirect:/vacations";
    }

}
