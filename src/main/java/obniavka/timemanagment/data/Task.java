package obniavka.timemanagment.data;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id", unique = true, nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private LocalDate deadline;

    @Column
    private Boolean isDone;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
}
