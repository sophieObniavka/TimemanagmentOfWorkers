package obniavka.timemanagment.domain;

import lombok.*;
import obniavka.timemanagment.data.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TaskDto {
    private Long id;

    @NotNull
    @NotEmpty(message = "{TASK.NAME.REQUIRED}")
    private String name;

    @NotNull
    @NotEmpty(message = "{TASK.DESCRIPTION.REQUIRED}")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    private Boolean isDone;

    private UserDto user;

}
