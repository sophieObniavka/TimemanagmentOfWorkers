package obniavka.timemanagment.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Currency currency;
    @Column
    private Double price;
    @Column
    private Boolean accepted;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "assignment_id", referencedColumnName = "assignment_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Assignment assignment;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnoreProperties("expense")
    private Set<Receipt> receipts = new HashSet<>();
}
