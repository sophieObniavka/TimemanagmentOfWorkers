package obniavka.timemanagment.domain;

import lombok.*;
import obniavka.timemanagment.data.Currency;
import obniavka.timemanagment.data.ExpenseType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ExpenseDto {
    private Long id;
    private ExpenseType expenseType;
    private Currency currency;
    private Double price;
    private UserDto user;
    private AssignmentDto assignment;
    private Boolean accepted;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ReceiptDto> receipts = new HashSet<>();

    public String getFileNames(){
        return receipts.stream().map(ReceiptDto::getFileName)
                .collect(Collectors.joining(", "));
    }
}
