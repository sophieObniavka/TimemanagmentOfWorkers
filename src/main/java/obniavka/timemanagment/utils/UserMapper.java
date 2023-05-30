package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.*;
import obniavka.timemanagment.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto map(final User user);
    User map(final UserDto userDto);
    List<UserDto> map(final List<User> users);

    @Mapping(target = "user", ignore = true)
    VacationDto map(final Vacation vacation);

    @Mapping(target = "user", ignore = true)
    Vacation map(final VacationDto vacation);

    @Mapping(target = "user", ignore = true)
    ReportDto map(final Report report);

    @Mapping(target = "user", ignore = true)
    Report map(final ReportDto report);

    @Mapping(target = "user", ignore = true)
    SickLeaveDto map(final SickLeave sickLeave);

    @Mapping(target = "user", ignore = true)
    SickLeave map(final SickLeaveDto sickLeaveDto);

    @Mapping(target = "user", ignore = true)
    TaskDto map(final Task task);

    @Mapping(target = "user", ignore = true)
    Task map(final TaskDto taskDto);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    AssignmentDto map(final Assignment assignment);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    Assignment map(final AssignmentDto assignmentDto);

    @Mapping(target = "user", ignore = true)
    InvoiceDto map(final Invoice invoice);

    @Mapping(target = "user", ignore = true)
    Invoice map(final InvoiceDto invoiceDto);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "receipts", ignore = true)
    ExpenseDto map(final Expense expense);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "receipts", ignore = true)
    Expense map(final ExpenseDto expenseDto);

}
