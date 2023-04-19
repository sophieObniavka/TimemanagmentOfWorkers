package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.services.ReportService;
import obniavka.timemanagment.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class ReportController {
    private static final String REPORT = "report";
    private static final String REPORTS = "reports";
    private static final String REPORTONCHANGE = "reportOnChange";
    private static final String REPORTNEW = "reportNew";

    private final ReportService reportService;
    private final UserService userService;



    @GetMapping("/report")
    public String report(final Model model, @ModelAttribute("current") UserDto user, @ModelAttribute("selected") String selectedDate) {

        model.addAttribute(REPORTS, reportService.fetchAllReportsByUserFromDb(user).stream().filter(x -> x.getWorkDay().getMonthValue() == Integer.parseInt(selectedDate.substring(5)) && x.getWorkDay().getYear() == Integer.parseInt(selectedDate.substring(0, 4))).toList());
        model.addAttribute(REPORTNEW, new ReportDto());
        model.addAttribute(REPORTONCHANGE, new ReportDto());


        return REPORT;
    }

    @GetMapping("/report/selectedDate/{month}")
    public String pageM(final Model model, @PathVariable("month") String month, @ModelAttribute("current") UserDto user) {

        model.addAttribute(REPORTONCHANGE, new ReportDto());
        model.addAttribute("someBean", month);
        model.addAttribute(REPORTS, reportService.fetchAllReportsByUserFromDb(user).stream().filter(x -> x.getWorkDay().getMonthValue() == Integer.parseInt(month.substring(5)) && x.getWorkDay().getYear() == Integer.parseInt(month.substring(0, 4))).toList());
        model.addAttribute(REPORTNEW, new ReportDto());
        model.addAttribute("selected", month);


        return  REPORT;
    }

    @PostMapping("/report")
    public String persistReport(@Valid @ModelAttribute("report") ReportDto report,Model model, @ModelAttribute("current") UserDto user) {
        report.setUser(userService.findUserByEmail(user.getEmail()));
        reportService.persistReportInDb(report);

        model.addAttribute(REPORTONCHANGE, new ReportDto());
        model.addAttribute(REPORTNEW, new ReportDto());

        return "redirect:/" + REPORT;
    }

    @PostMapping("/report/edit")
    public String popUpSave(final Model model, @ModelAttribute(REPORTONCHANGE) ReportDto report, @ModelAttribute("current") UserDto user) {
        ReportDto found = reportService.findReportById(report.getId());

        found.setWorkDay(report.getWorkDay());
        found.setBegin(report.getBegin());
        found.setEnd(report.getEnd());
        found.setPause(report.getPause());
        found.setUser(userService.findUserByEmail(user.getEmail()));
        found.setDescription(report.getDescription());

        reportService.persistReportInDb(found);

        return "redirect:/report";
    }

    @GetMapping("/report/delete/{id}")
    public String removeReport(final @PathVariable("id") String id, Model model) {
        reportService.dropReportFromDb(Long.parseLong(id));

        return "redirect:/" + REPORT;
    }
}
