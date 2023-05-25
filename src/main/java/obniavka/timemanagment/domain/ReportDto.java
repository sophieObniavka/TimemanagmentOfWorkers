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
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime begin;
    @DateTimeFormat(pattern = "HH:mm")
    private  LocalTime end;
    @DateTimeFormat(pattern = "HH:mm")
    private  LocalTime pause;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workDay;

    private String description;

    private UserDto user;
    private TaskDto task;
    private LocalTime amountOfHours;
    private Double totalSalary;
}
