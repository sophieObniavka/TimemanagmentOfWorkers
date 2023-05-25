package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.*;
import obniavka.timemanagment.helper.AmountOfDays;
import obniavka.timemanagment.services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

import static obniavka.timemanagment.utils.Constants.*;
import static obniavka.timemanagment.controller.helper.ModelConstants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;
    private final VacationService vacationService;
    private final SickLeavesService sickLeavesService;
    private final TaskService taskService;
    private final ReportService reportService;
    private final AssignmentService assignmentService;
    private final InvoiceService invoiceService;

    @GetMapping("/report")
    public String homeAdmin(Model model, @RequestParam("page") Optional<Integer> page, @ModelAttribute("selected") String selectedDate) {
        Page<ReportDto> reportDtos = reportService.fetchAllReportsFromDBByDate(selectedDate, PageRequest.of(page.orElse(1)-1, 10));
        paginationModel(model, REPORTS, page, reportDtos,null);
        model.addAttribute("link", "/admin/report");
        return REPORTS;
    }

    @GetMapping("/report/selectedDate/{month}")
    public String pageM(final Model model,@RequestParam("page") Optional<Integer> page, @PathVariable("month") String selectedDate, @ModelAttribute("current") UserDto user) {
        Page<ReportDto> reportDtos = reportService.fetchAllReportsFromDBByDate(selectedDate, PageRequest.of(page.orElse(1)-1, 10));
        model.addAttribute("selected",selectedDate);
        paginationModel(model, REPORTS, page, reportDtos,null);
        model.addAttribute("link", "/admin/report/selectedDate/" + selectedDate);
        return  REPORTS;
    }

    @GetMapping("/bad")
    public String admin(Principal principal) {
        return "bad";
    }

    @GetMapping("/users")
    public String usersAll(final Model model, @RequestParam("page") Optional<Integer> page, @RequestParam(required = false) String keyword) {
        int currentPage = page.orElse(1);

        Page<UserDto> usersPage = userService.fetchAllUserFromDb(keyword, PageRequest.of(currentPage - 1, 10));

        paginationModel(model, USERS,page, usersPage, keyword);
        return USERS;
    }

    @GetMapping("/users/{id}")
    public String user(final Model model, final @PathVariable("id") String id) {
        model.addAttribute(CURRENCIES, currencies);

        if (id == null && model.getAttribute(USER) == null) {
            model.addAttribute(USER, new UserDto());
        } else {
            model.addAttribute(USER, userService.findUserById(Long.parseLong(id)));
            model.addAttribute("reports", userService.findUserById(Long.parseLong(id)).getReports());
        }


        return "user";
    }

    @GetMapping("/users/profile/{id}")
    public String profileOfUser(final Model model, final @PathVariable("id") String id) {
        model.addAttribute(USER, userService.findUserByIdForProfile(Long.parseLong(id)));
        model.addAttribute("taskChange", new TaskDto());
        model.addAttribute("users", List.of(userService.findUserByIdForProfile(Long.parseLong(id))));
        return "profile";
    }

    @GetMapping("/users/create")
    public String createUser(final Model model) {

        model.addAttribute(CURRENCIES, currencies);
        if (model.getAttribute(USER) == null) {
            model.addAttribute(USER, new UserDto());
        }

        return "user";
    }

    @PostMapping("/users")
    public String persistUser(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        model.addAttribute(CURRENCIES, currencies);
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

        return "redirect:/admin/" + USERS;
    }

    @GetMapping("/users/delete/{id}")
    public String removeUser(final @PathVariable("id") String id) {
        userService.dropUserFromDb(Long.parseLong(id));

        return "redirect:/admin/" + USERS;
    }


    @GetMapping("/profile")
    public String page(final Model model) {
        return "profile";
    }


    @PostMapping("/vacation/{id}")
    public String confirmVacation(@PathVariable("id") String id) {
        VacationDto vacation = vacationService.findVacationById(Long.parseLong(id));
        UserDto user = vacation.getUser();

        if (!vacation.isAtOwnExpense()) {
            user.setVacationDays(user.getVacationDays() - AmountOfDays.getWorkingDaysBetweenTwoDates(vacation.getBegin(), vacation.getEnd()));
        }

        vacation.setConfirmed(true);
        vacation.setCheckedByUser(user.getFirstName() + " " + user.getLastName());

        vacationService.persistVacationInDB(vacation);


        return "redirect:/admin/vacations/active";
    }

    @PostMapping("/sickleave/{id}")
    public String confirmSickleave(@PathVariable("id") String id) {
        SickLeaveDto sickLeave = sickLeavesService.findSickLeaveById(Long.parseLong(id));
        UserDto user = sickLeave.getUser();
        if (!sickLeave.isAtOwnExpense()) {
            user.setVacationDays(user.getVacationDays() - AmountOfDays.getWorkingDaysBetweenTwoDates(sickLeave.getBegin(), sickLeave.getEnd()));
        }

        sickLeave.setConfirmed(true);
        sickLeave.setCheckedByUser(user.getFirstName() + " " + user.getLastName());

        sickLeavesService.persistSickLeaveInDB(sickLeave);


        return "redirect:/admin/sickleaves/active";
    }


    @PostMapping("/vacation/reject/{id}")
    public String rejectVacation(@ModelAttribute("comment") String comment, @PathVariable("id") String id, @ModelAttribute("current") UserDto user) {

        VacationDto vacation = vacationService.findVacationById(Long.parseLong(id));

        vacation.setConfirmed(false);
        vacation.setComment(comment);
        vacation.setCheckedByUser(user.getFirstName() + " " + user.getLastName());

        vacationService.persistVacationInDB(vacation);


        return "redirect:/admin/vacations/active";
    }

    @PostMapping("/sickleave/reject/{id}")
    public String rejectSickLeave(@ModelAttribute("comment") String comment, @PathVariable("id") String id, @ModelAttribute("current") UserDto user) {

        SickLeaveDto sickLeave = sickLeavesService.findSickLeaveById(Long.parseLong(id));

        sickLeave.setConfirmed(false);
        sickLeave.setComment(comment);
        sickLeave.setCheckedByUser(user.getFirstName() + " " + user.getLastName());

        sickLeavesService.persistSickLeaveInDB(sickLeave);


        return "redirect:/admin/sickleaves/active";
    }

    @GetMapping("/vacations/active")
    public String getActiveVacations(Model model, @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);

        Page<VacationDto> vacations = vacationService.selectAllUnconfirmedVacations(PageRequest.of(currentPage - 1, 5));

        paginationModel(model, VACATIONS,page, vacations, null);

        return "vacationsActive";
    }

    @GetMapping("/sickleaves/active")
    public String getActiveSickleaves(Model model, @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);

        Page<SickLeaveDto> sickLeaves = sickLeavesService.selectAllUncofirmedSickLeaves(PageRequest.of(currentPage - 1, 5));
        paginationModel(model, SICKLEAVES, page, sickLeaves, null);

        return "sickleavesActive";
    }

    @GetMapping("/vacations/closed")
    public String getClosedVacations(Model model, @RequestParam("page") Optional<Integer> page) {

        int currentPage = page.orElse(1);

        Page<VacationDto> vacations = vacationService.selectAllConfirmedVacations(PageRequest.of(currentPage - 1, 5));

        paginationModel(model, VACATIONS, page, vacations, null);
        return "vacationsClosed";
    }

    @GetMapping("/sickleaves/closed")
    public String getClosedSickleaves(Model model, @RequestParam("page") Optional<Integer> page) {

        Page<SickLeaveDto> sickleaves = sickLeavesService.selectAllCofirmedSickLeaves(PageRequest.of(page.orElse(1) - 1, 5));
        paginationModel(model, SICKLEAVES, page,sickleaves,null);

        return "sickleavesClosed";
    }

    @GetMapping("/tasks")
    public String getAllTasks(final Model model, @RequestParam("page") Optional<Integer> page, @RequestParam(required = false) String keyword) {

        model.addAttribute("editedTask", new TaskDto());
        model.addAttribute(USERS, userService.fetchAllUsersFromDb());

        Page<TaskDto> tasks = taskService.fetchAllTasksFromDB(keyword, PageRequest.of(page.orElse(1) - 1, 10));

        model.addAttribute("taskChange", new TaskDto());
        paginationModel(model, TASKS, page,tasks, keyword);
        return "adminTasks";
    }

    @PostMapping("/tasks")
    public String persistReport(@Valid @ModelAttribute("task") TaskDto task, Model model, HttpServletRequest request) {

        taskService.persistTaskInDb(task);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/assignments")
    public String getAllAssignments(final Model model, @RequestParam("page") Optional<Integer> page, @RequestParam(required = false) String keyword) {
        Page<AssignmentDto> assignments = assignmentService.fetchAllAssignmentsFromDB(keyword, PageRequest.of(page.orElse(1) - 1, 10));
        model.addAttribute("assignmentOnChange", new AssignmentDto());
        model.addAttribute(USERS, userService.fetchAllUsersFromDb());
        paginationModel(model, ASSIGNMENTS, page,assignments, keyword);
        return "adminAssignments";
    }

    @PostMapping("/assignments")
    public String persistAssignment(@Valid @ModelAttribute("assignment")AssignmentDto assignmentDto,@RequestParam("selectedUserIds") String selectedUserIds, HttpServletRequest request  ){
       assignmentService.persistAsignment(assignmentDto, userService.getAllUsersByIds(selectedUserIds));

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/assignments/delete/{id}")
    public String removeAssignment(final @PathVariable("id") String id) {
        assignmentService.dropAssignmentFromDB(Long.parseLong(id));

        return "redirect:/admin/" + ASSIGNMENTS;
    }

    @GetMapping("/invoices")
    public String getAllInvoices(final Model model, @RequestParam("page") Optional<Integer> page) {
        Page<InvoiceDto> invoices = invoiceService.fetchAllInvoicesFromDB(PageRequest.of(page.orElse(1) - 1, 10));
        paginationModel(model, "invoices", page,invoices, null);
        return "invoices";
    }

    @GetMapping("/download/{id}")
    public void downloadPdf(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        InvoiceDto invoiceDto = invoiceService.getInvoiceById(id);
        byte[] pdfData = invoiceDto.getPdfData();

        String fileName = invoiceDto.getUser().getFirstName() + "."+ invoiceDto.getUser().getLastName() + ".pdf";

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        response.getOutputStream().write(pdfData);
        response.getOutputStream().flush();
    }

    @GetMapping("/delete/invoice/{id}")
    public String removeInvoice(@PathVariable("id") Long id){
        invoiceService.dropInvoiceFromDb(id);
        return "redirect:/admin/" + "invoices";
    }
}
