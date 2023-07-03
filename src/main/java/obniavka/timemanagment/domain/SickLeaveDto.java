package obniavka.timemanagment.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SickLeaveDto {
    private Long id;
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

    private String description;

    private String comment;

    private Integer amountOfDays;

    private byte[] certificate_url;

    public String fullCreatedTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return created.format(formatter);
    }
}
