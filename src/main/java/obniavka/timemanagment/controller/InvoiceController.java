package obniavka.timemanagment.controller;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.InvoiceDto;
import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.helper.AmountOfDays;
import obniavka.timemanagment.helper.NumberToWordsConverter;
import obniavka.timemanagment.helper.Transliteration;
import obniavka.timemanagment.services.InvoiceService;
import obniavka.timemanagment.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

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
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class InvoiceController {
    private final UserService userService;
    private final InvoiceService invoiceService;
    private final TemplateEngine templateEngine;

    @PostMapping("/upload")
    public String persistInvoice(@Valid @ModelAttribute("invoice") InvoiceDto invoiceDto, Model model, @RequestParam("invoice") MultipartFile multipartFile, @ModelAttribute("current") UserDto user, HttpServletRequest request) throws IOException {

        invoiceDto.setPdfData(multipartFile.getBytes());
        model.addAttribute("invoice", new InvoiceDto());
        invoiceDto.setUser(userService.findUserByEmail(user.getEmail()));

        if(invoiceService.persistInvoiceInDb(invoiceDto) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("showToast", true);
        }

        return "redirect:" + request.getHeader("Referer");
    }


    @GetMapping("/pdf/expenses")
    public void convertHtmlToPdf(HttpServletResponse response) throws IOException {

        String fileName= "Invoice.pdf";

        try  (OutputStream outputStream = response.getOutputStream()){

            Context context = new Context();

            String htmlContent = templateEngine.process("expensesReceipt", context);


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
