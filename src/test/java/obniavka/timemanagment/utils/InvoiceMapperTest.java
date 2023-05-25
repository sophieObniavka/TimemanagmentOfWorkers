package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Invoice;
import obniavka.timemanagment.domain.InvoiceDto;
import obniavka.timemanagment.domain.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InvoiceMapperTest {

    @InjectMocks
    InvoiceMapper invoiceMapper = Mappers.getMapper(InvoiceMapper.class);

    @Test
    @DisplayName("Test mapping DTO")
    void testMappingDto(){
        InvoiceDto invoiceDto = new InvoiceDto();
        UserDto userDto = new UserDto();
        byte[] expected = {1,55,2};
        userDto.setId(1L);

        invoiceDto.setId(1L);
        invoiceDto.setPdfData(new byte[]{1,55,2});
        invoiceDto.setUser(userDto);

        userDto.setInvoices(Set.of(invoiceDto));

        Invoice invoice = invoiceMapper.map(invoiceDto);

        assertNotNull(invoice);
        assertNotNull(invoice.getUser());

        assertEquals(invoiceDto.getId(), invoice.getId());
        assertArrayEquals(invoiceDto.getPdfData(), invoice.getPdfData());
    }
}
