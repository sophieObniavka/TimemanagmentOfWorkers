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

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sickleaves")
public class SickLeaveController {
    private static final String SICKLEAVE = "sickleave";
    private static final String SICKLEAVES = "sickleaves";
    private static final String CURRENT = "current";

    private final SickLeavesService sickLeavesService;
    private final UserService userService;
    @GetMapping()
    public String getSickleaves(Model model, @RequestParam("page") Optional<Integer> page, @ModelAttribute("current") UserDto user){

        int currentPage = page.orElse(1);

        Page<SickLeaveDto> sickLeaves = sickLeavesService.findAllSickLLeavesByUser(user, PageRequest.of(currentPage-1, 8));

        model.addAttribute("left", user.getSickleaveDays());
        model.addAttribute(SICKLEAVES, user.getSickLeaves());
        model.addAttribute(SICKLEAVE, new SickLeaveDto());
        model.addAttribute("page", sickLeaves);

        int totalPages = sickLeaves.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("currentPage", currentPage);

        return SICKLEAVES;

    }

    @PostMapping("")
    public String saveSickLeave(@Valid @ModelAttribute(SICKLEAVE) SickLeaveDto sickLeave, @ModelAttribute("current") UserDto user){
       sickLeave.setUser(userService.findUserByEmail(user.getEmail()));
        sickLeavesService.persistSickLeaveInDB(sickLeave);

        return"redirect:/sickleaves";
    }

}
