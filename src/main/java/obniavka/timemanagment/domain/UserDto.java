package obniavka.timemanagment.domain;

import lombok.*;
import obniavka.timemanagment.validation.DateConstraint;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {

    private Long id;

    @NotNull
    @NotEmpty(message = "{USER.NAME.REQUIRED}")
    private String firstName;

    @NotNull
    @NotEmpty(message = "{USER.LASTNAME.REQUIRED}")
    private String lastName;

    @NotNull
    //@NotEmpty(message = "{USER.BIRTHDATE.REQUIRED}")
    @DateConstraint
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @NotNull
   // @NotEmpty(message = "{USER.HIRED.REQUIRED}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hired;

    @NotNull
    @NotEmpty(message = "{USER.EMPLOYEEID.REQUIRED}")
    private String employeeId;

    @NotNull
    @NotEmpty(message = "{USER.PHONENUMBER.REQUIRED}")
    @Pattern(regexp = "^\\d{10}$", message = "{USER.PHONENUMBER.BADINPUT}")
    private String phoneNumber;

    @NotNull
    @NotEmpty(message = "{USER.COUNTRY.REQUIRED}")
    private String country;

    @NotNull
    @NotEmpty(message = "{USER.CITY.REQUIRED}")
    private  String city;
    @NotNull
    @NotEmpty(message = "{USER.STREET.REQUIRED}")
    private String street;
    @NotNull
    @NotEmpty(message = "{USER.HOUSENUMBER.REQUIRED}")
    private String houseNumber;
    @NotNull
    @NotEmpty(message = "{USER.POSTCODE.REQUIRED}")
    private String postCode;

    @NotNull
    @NotEmpty(message = "{USER.VACATIONDAYS.REQUIRED}")
    private Integer vacationDays;
    @NotNull
    @NotEmpty(message = "{USER.EMAIL.REQUIRED}")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "{USER.EMAIL.PATERN}")
    private String email;
    @NotNull
    private byte[] imageUrl;

    @NotNull
    @NotEmpty(message = "{USER.ROLE.REQUIRED}")
    private String role;
    @NotNull
    @NotEmpty(message = "{USER.PASSWORD.REQUIRED}")
    private String password;

    private boolean passwordExpired;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<VacationDto> vacations = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ReportDto> reports = new HashSet<>();

}
