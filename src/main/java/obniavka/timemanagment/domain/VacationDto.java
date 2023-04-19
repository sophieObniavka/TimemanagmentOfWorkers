package obniavka.timemanagment.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VacationDto {

    private Long id;

    @NotNull(message = "{VACATION.BEGIN.REQUIRED}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;

    @NotNull(message = "{VACATION.END.REQUIRED}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created;

    private Boolean confirmed;

    private boolean atOwnExpense;

    private UserDto user;

    private String checkedByUser;

    private String comment;

    private Integer amountOfDays;

}
