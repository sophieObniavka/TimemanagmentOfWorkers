package obniavka.timemanagment.services;

import obniavka.timemanagment.data.Invoice;
import obniavka.timemanagment.data.Report;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.InvoiceDto;
import obniavka.timemanagment.repository.InvoiceRepository;
import obniavka.timemanagment.utils.InvoiceMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository){this.invoiceRepository = invoiceRepository;}

    InvoiceMapper mapper = Mappers.getMapper(InvoiceMapper.class);

    public InvoiceDto persistInvoiceInDb(final InvoiceDto invoiceDto){
        return mapper.map(invoiceRepository.save(mapper.map(invoiceDto)));
    }

    public void dropInvoiceFromDb(final Long id){
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        User user = invoice.getUser();
        user.getInvoices().remove(invoice);
        invoiceRepository.deleteById(id);

    }

    public Page<InvoiceDto> fetchAllInvoicesFromDB(Pageable pageable){
        return invoiceRepository.findAll(pageable).map(mapper::map);
    }

    public InvoiceDto getInvoiceById(Long id){
        return mapper.map(invoiceRepository.findById(id).orElse(null));
    }
}
