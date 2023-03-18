package obniavka.timemanagment.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReportDto {
    private Long id;


    private LocalTime begin;

    private  LocalTime end;

    private  LocalTime pause;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workDay;

    private String description;

    private UserDto user;
}
