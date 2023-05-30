package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Assignment;
import obniavka.timemanagment.data.Expense;
import obniavka.timemanagment.data.Receipt;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.AssignmentDto;
import obniavka.timemanagment.domain.ExpenseDto;
import obniavka.timemanagment.domain.ReceiptDto;
import obniavka.timemanagment.domain.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    ExpenseMapper MAPPER = Mappers.getMapper(ExpenseMapper.class);

    ExpenseDto map(final Expense expense);

    Expense map(final ExpenseDto expenseDto);

    List<ExpenseDto> map(final List<Expense> expenses);

    @Mapping(target = "vacations", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "sickLeaves", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    UserDto map(final User user);

    @Mapping(target = "vacations", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "sickLeaves", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    User map(UserDto userDto);

    @Mapping(target = "expense", ignore = true)
    ReceiptDto map(final Receipt receipt);

    @Mapping(target = "expense", ignore = true)
    Receipt map(final ReceiptDto receiptDto);


    @Mapping(target = "users", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    AssignmentDto map(final Assignment assignment);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    Assignment map(final AssignmentDto assignmentDto);
}
