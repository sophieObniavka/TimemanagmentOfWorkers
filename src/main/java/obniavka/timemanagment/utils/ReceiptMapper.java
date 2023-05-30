package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Expense;
import obniavka.timemanagment.data.Receipt;
import obniavka.timemanagment.domain.ExpenseDto;
import obniavka.timemanagment.domain.ReceiptDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReceiptMapper {
    ReceiptMapper MAPPER = Mappers.getMapper(ReceiptMapper.class);

    ReceiptDto map(final Receipt receipt);
    Receipt map(final ReceiptDto receiptDto);

    List<ReceiptDto> map(final List<Receipt> receipts);

    @Mapping(target = "receipts", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    ExpenseDto map(final Expense expense);

    @Mapping(target = "receipts", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    Expense map(final ExpenseDto expenseDto);

}
