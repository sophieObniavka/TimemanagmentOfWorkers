package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.AssignmentDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.services.AssignmentService;
import obniavka.timemanagment.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static obniavka.timemanagment.controller.helper.ModelConstants.paginationModel;
import static obniavka.timemanagment.utils.Constants.*;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final UserService userService;

    @GetMapping("/assignments")
    public String allAssignments(final Model model, @RequestParam("page") Optional<Integer> page,  @ModelAttribute("current") UserDto user){
        UserDto userDto = userService.findUserByEmail(user.getEmail());

        Page<AssignmentDto> assignments = assignmentService.fetchAllAssignmentsByUser(userDto,PageRequest.of(page.orElse(1) - 1, 10));
        paginationModel(model, ASSIGNMENTS, page, assignments, null);
        return ASSIGNMENTS;
    }
}
