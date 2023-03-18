package obniavka.timemanagment.data;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@AllArgsConstructor
@Entity
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(exclude="user")
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "report_id", unique = true, nullable = false)
    private Long id;

    @Column
    private LocalTime begin;

    @Column
    private  LocalTime end;

    @Column
    private  LocalTime pause;

    @Column
    private LocalDate workDay;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

}
