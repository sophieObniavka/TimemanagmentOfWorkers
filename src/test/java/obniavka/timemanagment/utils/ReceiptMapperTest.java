package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Currency;
import obniavka.timemanagment.data.Receipt;
import obniavka.timemanagment.domain.ExpenseDto;
import obniavka.timemanagment.domain.ReceiptDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ReceiptMapperTest {
    @InjectMocks
    ReceiptMapper receiptMapper = Mappers.getMapper(ReceiptMapper.class);

    @Test
    @DisplayName("Test mapping DTO")
    void testMappingDto() {
        ReceiptDto receiptDto = new ReceiptDto().toBuilder()
                .id(1L)
                .receipt_url(new byte[]{1,12,33})
                .build();

        ExpenseDto expenseDto = new ExpenseDto().toBuilder()
                .id(1L)
                .currency(Currency.GBP)
                .receipts(Set.of(receiptDto))
                .build();

        receiptDto.setExpense(expenseDto);

        Receipt receipt = receiptMapper.map(receiptDto);

        assertNotNull(receipt);
        assertNotNull(receipt.getExpense());
    }
}
