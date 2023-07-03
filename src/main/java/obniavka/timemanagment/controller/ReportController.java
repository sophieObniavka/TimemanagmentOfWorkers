package obniavka.timemanagment.controller;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.helper.AmountOfDays;
import obniavka.timemanagment.helper.NumberToWordsConverter;
import obniavka.timemanagment.helper.Transliteration;
import obniavka.timemanagment.services.ExcelGenerator;
import obniavka.timemanagment.services.ReportService;
import obniavka.timemanagment.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import static obniavka.timemanagment.utils.Constants.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;
    private final TemplateEngine templateEngine;


    @GetMapping("/report")
    public String report(final Model model, HttpSession session, @ModelAttribute("current") UserDto user, @ModelAttribute("selected") String selectedDate) {
        UserDto userDto = userService.findUserByEmail(user.getEmail());
        List<ReportDto> reports = reportService.fetchAllReportsByDateAndUserFromDb(selectedDate, userDto);
        model.addAttribute(REPORTS,reports);
        model.addAttribute(REPORTNEW, new ReportDto());
        model.addAttribute(REPORTONCHANGE, new ReportDto());
        model.addAttribute(TASKS, userDto.getTasks());
        model.addAttribute("total", AmountOfDays.getSalaryPerMonth(reports));
        Object showToast = session.getAttribute("showToast");
        boolean showToastValue = (showToast != null && (boolean) showToast) ? true : false;
        model.addAttribute("showToast", showToastValue);
        session.setAttribute("showToast", false);


        return REPORT;
    }

    @GetMapping("/report/selectedDate/{month}")
    public String pageM(final Model model, @PathVariable("month") String month, @ModelAttribute("current") UserDto user) {
        UserDto userDto = userService.findUserByEmail(user.getEmail());
        model.addAttribute("successMessage", "");
        List<ReportDto> reports = reportService.fetchAllReportsByDateAndUserFromDb(month, userDto);
        model.addAttribute(REPORTONCHANGE, new ReportDto());
        model.addAttribute(REPORTS, reports);
        model.addAttribute(REPORTNEW, new ReportDto());
        model.addAttribute("selected", month);
        model.addAttribute(TASKS, userDto.getTasks());
        model.addAttribute("total", AmountOfDays.getSalaryPerMonth(reports));
        model.addAttribute("showToast", false);

        return  REPORT;
    }

    @PostMapping("/report")
    public String persistReport(@Valid @ModelAttribute("report") ReportDto report,Model model, @ModelAttribute("current") UserDto user, HttpServletRequest request) {

        report.setUser(userService.findUserByEmail(user.getEmail()));
        reportService.persistReportInDb(report);

        model.addAttribute(REPORTONCHANGE, new ReportDto());
        model.addAttribute(REPORTNEW, new ReportDto());

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/report/edit")
    public String popUpSave(@ModelAttribute(REPORTONCHANGE) ReportDto report, @ModelAttribute("current") UserDto user, HttpServletRequest request) {
        ReportDto found = reportService.findReportById(report.getId());

        found.setWorkDay(report.getWorkDay());
        found.setBegin(report.getBegin());
        found.setEnd(report.getEnd());
        found.setPause(report.getPause());
        found.setUser(userService.findUserByEmail(user.getEmail()));
        found.setDescription(report.getDescription());

        reportService.persistReportInDb(found);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/report/delete/{id}")
    public String removeReport(final @PathVariable("id") String id, HttpServletRequest request) {

        reportService.dropReportFromDb(Long.parseLong(id));

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/export-to-excel/{date}")
    public void exportIntoExcelFile(HttpServletResponse response,HttpServletRequest request, @PathVariable("date") String selectedDate, @ModelAttribute("current") UserDto user) throws IOException {
        Locale userLocale = Locale.US;
        Locale.setDefault(userLocale);

        UserDto userDto = userService.findUserByEmail(user.getEmail());
        response.setContentType("application/octet-stream");
        LocalDate date = LocalDate.parse(selectedDate + "-01");
        String monthName = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Report." + userDto.getLastName() + "." + monthName + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List <ReportDto> reportDtos = reportService.fetchAllReportsByDateAndUserFromDb(selectedDate, userDto);
        ExcelGenerator generator = new ExcelGenerator(reportDtos);
        generator.generateExcelFile(response);
    }

    @GetMapping("/convert/{date}")
    public void convertHtmlToPdf(HttpServletResponse response, @PathVariable("date") String selectedDate, @ModelAttribute("current") UserDto user) throws IOException {
        response.setLocale(new Locale("uk"));
        UserDto userDto = userService.findUserByEmail(user.getEmail());
        LocalDate date = LocalDate.parse(selectedDate + "-01");
        String monthName = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = currentDate.format(formatter);

        List<ReportDto> reports = reportService.fetchAllReportsByDateAndUserFromDb(selectedDate, userDto);

        DateTimeFormatter formatterUkr = DateTimeFormatter.ofPattern("d MMMM yyyy 'р.'", new Locale("uk"));
        String formattedDateUkr = currentDate.format(formatterUkr);

        String fileName= "Invoice." + userDto.getLastName()+ "." + monthName + "." + selectedDate.substring(0,4) + ".pdf";

        try  (OutputStream outputStream = response.getOutputStream()){

            Context context = new Context();

            context.setVariable("user",userDto);
            context.setVariable("currentEnglishDate", formattedDate);
            context.setVariable("currentUkrainianDate", formattedDateUkr);
            context.setVariable("total", new DecimalFormat("0.00").format(AmountOfDays.getSalaryPerMonth(reports)));
            context.setVariable("hours", reports.stream().mapToInt(r-> r.getAmountOfHours().toSecondOfDay()/ 3600).sum());
            context.setVariable("cents", AmountOfDays.getFractionalPart(AmountOfDays.getSalaryPerMonth(reports)));
            context.setVariable("totalWordsEN", NumberToWordsConverter.convertNumberToWordsEN(AmountOfDays.getSalaryPerMonth(reports).intValue()));
            context.setVariable("totalWordsUK", NumberToWordsConverter.convertToUkrainian(AmountOfDays.getSalaryPerMonth(reports).intValue()));
            context.setVariable("fractional", userDto.getCurrency().getFractional());

            String htmlContent = templateEngine.process("invoice", context);


            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont (
                    "src/main/resources/Arial Unicode MS.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            renderer.createPDF(byteArrayOutputStream);
            byte[] pdfBytes = byteArrayOutputStream.toByteArray();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");

            response.setContentLength(pdfBytes.length);
            outputStream.write(pdfBytes);
            outputStream.flush();

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
