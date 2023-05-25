package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;;
import obniavka.timemanagment.domain.SickLeaveDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.services.SickLeavesService;
import obniavka.timemanagment.services.UserService;
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
@RequestMapping("/sickleaves")
public class SickLeaveController {

    private final SickLeavesService sickLeavesService;
    private final UserService userService;
    @GetMapping()
    public String getSickleaves(Model model, @RequestParam("page") Optional<Integer> page, @ModelAttribute("current") UserDto user){

        Page<SickLeaveDto> sickLeaves = sickLeavesService.findAllSickLLeavesByUser(user, PageRequest.of(page.orElse(1)-1, 8));

        model.addAttribute("left", user.getSickleaveDays());
        model.addAttribute(SICKLEAVE, new SickLeaveDto());

       paginationModel(model,SICKLEAVES, page, sickLeaves,null);

        return SICKLEAVES;

    }

    @PostMapping("")
    public String saveSickLeave(@Valid @ModelAttribute(SICKLEAVE) SickLeaveDto sickLeave, @ModelAttribute("current") UserDto user){
       sickLeave.setUser(userService.findUserByEmail(user.getEmail()));
        sickLeavesService.persistSickLeaveInDB(sickLeave);

        return"redirect:/sickleaves";
    }

}
