package obniavka.timemanagment.domain;

import lombok.*;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReceiptDto {
    private Long id;
    private byte[] receipt_url;
    private ExpenseDto expense;
    private String fileName;
}
