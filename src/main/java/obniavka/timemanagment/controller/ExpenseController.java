package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.data.Receipt;
import obniavka.timemanagment.domain.*;
import obniavka.timemanagment.services.AssignmentService;
import obniavka.timemanagment.services.ExpenseService;
import obniavka.timemanagment.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static obniavka.timemanagment.controller.helper.ModelConstants.paginationModel;
import static obniavka.timemanagment.utils.Constants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expenses")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final AssignmentService assignmentService;
    private final UserService userService;
    @GetMapping("")
    public String allExpenses(final Model model, @RequestParam("page") Optional<Integer> page, @ModelAttribute("current") UserDto user){

        int currentPage = page.orElse(1);


        UserDto userDto = userService.findUserByEmail(user.getEmail());

        model.addAttribute(EXPENSE, new ExpenseDto());
        model.addAttribute(EXPENSES,expenseService.getAllExpensesByUser(userDto,  PageRequest.of(currentPage - 1, 10)).getContent());
        Page<AssignmentDto> assignments = assignmentService.fetchAllAssignmentsByUser(userDto,PageRequest.of(page.orElse(1) - 1, 10));
        paginationModel(model, ASSIGNMENTS, page, assignments, null);
        return EXPENSES;
    }

    @PostMapping("")
    public String persistExpense(@Valid @ModelAttribute("expense") ExpenseDto expenseDto, @RequestPart("files") MultipartFile[] files,@ModelAttribute("current") UserDto userDto) throws IOException {

        expenseDto.setUser(userService.findUserByEmail(userDto.getEmail()));

       List<ReceiptDto> receips = new ArrayList<>();
        for(MultipartFile file:files){
            receips.add(new ReceiptDto().toBuilder().receipt_url(file.getBytes()).fileName(file.getOriginalFilename()).build());
        }

       expenseService.persistExpenseInDb(expenseDto, receips);
        return "redirect:/expenses";
    }

    @GetMapping("/delete/{id}")
    public String removeReport(final @PathVariable("id") String id) {

        expenseService.deleteExpense(Long.parseLong(id));

        return "redirect:/expenses";
    }
}
