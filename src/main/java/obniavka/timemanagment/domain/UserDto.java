package obniavka.timemanagment.domain;

import lombok.*;
import obniavka.timemanagment.data.Currency;
import obniavka.timemanagment.helper.Transliteration;
import obniavka.timemanagment.validation.DateConstraint;
import obniavka.timemanagment.validation.PasswordConstraint;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@PasswordConstraint
public class UserDto implements UserDetails {

    private Long id;

    @NotNull
    @NotEmpty(message = "{USER.NAME.REQUIRED}")
    private String firstName;

    @NotNull
    @NotEmpty(message = "{USER.LASTNAME.REQUIRED}")
    private String lastName;

    @NotNull
    @NotEmpty(message = "{USER.PATRONYMIC.REQUIRED}")
    private String patronymic;

    @NotNull
    @NotEmpty(message = "{USER.TAXNUMBER.REQUIRED}")
    private String taxNumber;

    @NotNull
    @NotEmpty(message = "{USER.ACCOUNT.REQUIRED}")
    private String account;

    @NotNull
    @NotEmpty(message = "{USER.BENEFICIARYBANK.REQUIRED}")
    private String beneficiaryBank;

    @NotNull
    @NotEmpty(message = "{USER.SWIFTCODE.REQUIRED}")
    private String swiftCode;

    @NotNull(message = "{USER.CURRENCY.REQUIRED}")
    private Currency currency;

    @NotNull
    @DateConstraint
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hired;

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

    @NotNull(message = "{USER.VACATIONDAYS.REQUIRED}")
    private Integer vacationDays;

    @NotNull(message = "{USER.SICKLEAVEDAYS.REQUIRED}")
    private Integer sickleaveDays;

    @NotNull(message = "{USER.SALARYPERHOUR.REQUIRED}")
    private Integer salaryPerHour;

    @NotNull
    @NotEmpty(message = "{USER.EMAIL.REQUIRED}")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "{USER.EMAIL.PATERN}")
    private String email;
    @NotNull
    private byte[] imageUrl;

    @NotNull
    @NotEmpty(message = "{USER.ROLE.REQUIRED}")
    private String role;

    private String password;

    private String convertedImage;

    private Integer takenVacations;

    private Integer vacationsAtOwnExpense;

    private Integer takenSickLeaves;

    private Integer sickLeavesAtOwnExpense;

    @NotNull
    @NotEmpty(message = "{USER.POSITION.REQUIRED}")
    private String position;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<VacationDto> vacations = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ReportDto> reports = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<SickLeaveDto> sickLeaves = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<TaskDto> tasks = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<AssignmentDto> assignments = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<InvoiceDto> invoices = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ExpenseDto> expenses = new HashSet<>();

    public String fullName(){
       return this.firstName + " " + this.lastName;
    }

    public String fullAddress(){
        return this.country + ", " + this.city;
    }

    public UserDto translate(){
        return new UserDto().toBuilder()
                .firstName(Transliteration.transliterate(firstName))
                .lastName(Transliteration.transliterate(lastName))
                .patronymic(Transliteration.transliterate(patronymic))
                .city(Transliteration.transliterate(city))
                .street(Transliteration.transliterate(street)).build();

    }
}
