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
import static obniavka.timemanagment.controller.helper.ModelConstants.*;
import static obniavka.timemanagment.utils.Constants.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vacations")
public class VacationController {
    private final VacationService vacationService;
    private final UserService userService;


    @GetMapping("")
    public String getVacations( Model model, @RequestParam("page") Optional<Integer> page,  @ModelAttribute("current") UserDto user){
        Page<VacationDto> vacations = vacationService.findAllVacationsByUser(user, PageRequest.of(page.orElse(1)-1, 8));

        model.addAttribute("left", user.getVacationDays());
        model.addAttribute(VACATION, new VacationDto());
        paginationModel(model,VACATIONS, page, vacations, null);
        return VACATIONS;
    }

    @PostMapping("")
    public String saveVacation(@Valid @ModelAttribute("vacation") VacationDto vacation, @ModelAttribute("current") UserDto user){
        vacation.setUser(userService.findUserByEmail(user.getEmail()));
        vacationService.persistVacationInDB(vacation);
        return"redirect:/vacations";
    }

}
