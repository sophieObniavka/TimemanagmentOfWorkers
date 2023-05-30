package obniavka.timemanagment.data;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String patronymic;
    @Column
    private LocalDate birth;
    @Column
    private LocalDate hired;

    @Column
    private String phoneNumber;
    @Column
    private String country;
    @Column
    private  String city;
    @Column
    private String street;
    @Column
    private String houseNumber;
    @Column
    private String postCode;
    @Column
    private String email;
    @Lob
    private byte[] imageUrl;
    @Column
    private String role;
    @Column
    private String position;
    @Column
    private String taxNumber;
    @Column
    private String account;
    @Column
    private String beneficiaryBank;
    @Column
    private String swiftCode;
    @Column
    private Currency currency;
    @Column
    private Integer vacationDays;
    @Column
    private Integer sickleaveDays;
    @Column
    private Integer salaryPerHour;
    @Column
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private Set<Vacation> vacations = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private Set<Report> reports = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private Set<SickLeave> sickLeaves = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Assignment> assignments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private Set<Expense> expenses = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
