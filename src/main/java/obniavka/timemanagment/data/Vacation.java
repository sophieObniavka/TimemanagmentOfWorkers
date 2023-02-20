package obniavka.timemanagment.data;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    private Boolean archived;

    @Column
    private Boolean confirmed;

    @Column
    private Boolean atOwnExpense;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

}
