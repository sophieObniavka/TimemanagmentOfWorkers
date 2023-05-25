package obniavka.timemanagment.domain;

import lombok.*;
import obniavka.timemanagment.data.User;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class InvoiceDto {
    private Long id;

    private byte[] pdfData;

    private UserDto user;
}
