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
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assignment_id", unique = true, nullable = false)
    private Long id;
    @Column
    private String name;
    @Column
    private String country;
    @Column
    private String city;
    @Column
    private LocalDate begin;
    @Column
    private LocalDate end;
    @Column
    private String description;

@ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.LAZY)
@JoinTable(
        name = "user_assignment",
        joinColumns = @JoinColumn(name = "assignment_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users  = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment)) return false;
        Assignment assignment = (Assignment) o;
        return Objects.equals(id, assignment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}