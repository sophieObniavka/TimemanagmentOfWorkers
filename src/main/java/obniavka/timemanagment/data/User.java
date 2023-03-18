package obniavka.timemanagment.data;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

import java.util.Set;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private LocalDate birth;
    @Column
    private LocalDate hired;
    @Column
    private String employeeId;
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
    private Integer vacationDays;
    @Column
    private String password;

    @Column(nullable = false)
    private LocalDate passwordExpires;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private Set<Vacation> vacations = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private Set<Report> reports = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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


}
