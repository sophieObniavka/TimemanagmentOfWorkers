package obniavka.timemanagment.data;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@NoArgsConstructor
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "receipt_id", unique = true, nullable = false)
    private Long id;

    @Lob
    private byte[] receipt_url;

    @Column
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "expense_id", referencedColumnName = "expense_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Expense expense;
}
