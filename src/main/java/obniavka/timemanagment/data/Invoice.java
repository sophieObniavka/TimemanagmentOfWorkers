package obniavka.timemanagment.data;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "invoice_id", unique = true, nullable = false)
    private Long id;

    @Lob
    private byte[] pdfData;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
}
