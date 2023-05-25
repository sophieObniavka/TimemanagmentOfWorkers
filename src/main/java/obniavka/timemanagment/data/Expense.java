package obniavka.timemanagment.data;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "expense_id", unique = true, nullable = false)
    private Long id;

    @Column
    private ExpenseType expenseType;
    @Column
    private Double price;
    @Lob
    private byte[] receipt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;


}
