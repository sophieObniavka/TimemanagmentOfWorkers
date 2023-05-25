package obniavka.timemanagment.data;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Entity
@Data
@Builder(toBuilder = true)
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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToOne(optional = true)
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Task task;

}
