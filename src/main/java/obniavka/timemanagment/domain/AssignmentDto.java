package obniavka.timemanagment.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AssignmentDto {
    private Long id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  LocalDate end;
    private String country;
    private String city;
    private String description;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<UserDto> users = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ExpenseDto> expenses = new HashSet<>();

    public String allUsers(){
        return users.stream().map(u->u.fullName()).collect(Collectors.joining(", "));
    }

    public String allUsersId(){
        return users.stream().map(UserDto::getId).map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    public double getSumOfPrice(final UserDto userDto){
        return expenses.stream().filter(e -> e.getUser().equals(userDto)).mapToDouble(ExpenseDto::getPrice).sum();
    }


}
