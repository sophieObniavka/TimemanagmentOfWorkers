package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Currency;
import obniavka.timemanagment.data.Expense;
import obniavka.timemanagment.domain.AssignmentDto;
import obniavka.timemanagment.domain.ExpenseDto;
import obniavka.timemanagment.domain.ReceiptDto;
import obniavka.timemanagment.domain.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ExpenseMapperTest {
    @InjectMocks
    ExpenseMapper expenseMapper = Mappers.getMapper(ExpenseMapper.class);

    @Test
    @DisplayName("Test mapping DTO")
    void testMappingDto() {
        AssignmentDto assignmentDto = new AssignmentDto();
        ExpenseDto expenseDto = new ExpenseDto();
        UserDto userDto = new UserDto();
        ReceiptDto receiptDto = new ReceiptDto();

        receiptDto.setId(1L);
        receiptDto.setReceipt_url(new byte[]{1, 22, 4});
        receiptDto.setExpense(expenseDto);

        userDto.setId(1L);
        userDto.setFirstName("Lala");
        userDto.setLastName("Last");
        userDto.setExpenses(Set.of(expenseDto));

        assignmentDto.setId(1L);
        assignmentDto.setName("Business Trip to Ukraine");
        assignmentDto.setBegin(LocalDate.of(2023, Month.JUNE, 11));
        assignmentDto.setEnd(LocalDate.of(2023, Month.JUNE, 21));
        assignmentDto.setCountry("Ukraine");
        assignmentDto.setCity("Lviv");
        assignmentDto.setDescription("Project where you have to be placed");
        assignmentDto.setUsers(Set.of(userDto));
        assignmentDto.setExpenses(Set.of(expenseDto));

        expenseDto.setId(1L);
        expenseDto.setUser(userDto);
        expenseDto.setAssignment(assignmentDto);
        expenseDto.setReceipts(Set.of(receiptDto));
        expenseDto.setAccepted(true);
        expenseDto.setCurrency(Currency.EURO);

        Expense expense = expenseMapper.map(expenseDto);

        assertNotNull(expense);
        assertNotNull(expense.getUser());
        assertNotNull(expense.getAssignment());
        assertEquals(1, expense.getReceipts().size());
    }
}
