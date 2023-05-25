package obniavka.timemanagment.controller.helper;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import static obniavka.timemanagment.utils.Constants.*;

public class ModelConstants {
    public static <T> void paginationModel(Model model, String object, Optional<Integer> page, Page<T> objects, String keyword){
        int currentPage = page.orElse(1);

        model.addAttribute(object, objects.getContent());
        model.addAttribute(PAGE, objects);

        model.addAttribute("keyword", keyword);
        int totalPages = objects.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute(PAGE_NUMBERS, pageNumbers);
        }

        model.addAttribute(CURRENT_PAGE, currentPage);
    }
}
