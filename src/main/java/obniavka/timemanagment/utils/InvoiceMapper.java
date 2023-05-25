package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Invoice;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.InvoiceDto;
import obniavka.timemanagment.domain.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceMapper MAPPER = Mappers.getMapper(InvoiceMapper.class);
    InvoiceDto map(final Invoice invoice);
    Invoice map(final InvoiceDto invoiceDto);

    List<InvoiceDto> map(final List<Invoice> invoices);

    @Mapping(target = "vacations", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "sickLeaves", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    UserDto map(final User user);

    @Mapping(target = "vacations", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "sickLeaves", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    User map(UserDto userDto);



}
