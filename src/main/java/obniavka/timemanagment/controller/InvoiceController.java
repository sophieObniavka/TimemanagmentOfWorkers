package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.InvoiceDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.services.InvoiceService;
import obniavka.timemanagment.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class InvoiceController {
    private final UserService userService;
    private final InvoiceService invoiceService;

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



}
