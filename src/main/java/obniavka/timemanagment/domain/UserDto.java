package obniavka.timemanagment.domain;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
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
    @NotEmpty(message = "{USER.EMPLOYEEID.REQUIRED}")
    private String employeeId;
    @NotNull
    @NotEmpty(message = "{USER.PHONENUMBER.REQUIRED}")
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
    @NotEmpty(message = "{USER.EMAIL.REQUIRED}")
    private String email;
    @NotNull
    private byte[] imageUrl;
    @NotNull
    @NotEmpty(message = "{USER.ROLE.REQUIRED}")
    private String role;
    @NotNull
    @NotEmpty(message = "{USER.PASSWORD.REQUIRED}")
    private String password;

    @Column(length = 1000)
    private String about;
}
