package obniavka.timemanagment.data;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vacation_id", unique = true, nullable = false)
    private Long id;

    @Column
    private LocalDate begin;

    @Column
    private LocalDate end;

    @Column
    private Boolean confirmed;

    @Column
    private LocalDateTime created;

    @Column
    private boolean atOwnExpense;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    private String checkedByUser;

    @Column
    private String comment;



}
