package obniavka.timemanagment.domain;

import lombok.*;
import obniavka.timemanagment.data.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
public class VacationDto {

    private Long id;

    @NotNull(message = "{VACATION.BEGIN.REQUIRED}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;

    @NotNull(message = "{VACATION.END.REQUIRED}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

    private Boolean archived;

    private Boolean confirmed;

    private Boolean atOwnExpense;

    private UserDto user;
}
