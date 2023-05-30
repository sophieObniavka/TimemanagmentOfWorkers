package obniavka.timemanagment.services;

import obniavka.timemanagment.domain.ReceiptDto;
import obniavka.timemanagment.repository.ReceiptRepository;
import obniavka.timemanagment.utils.ReceiptMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {
    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    ReceiptMapper receiptMapper = Mappers.getMapper(ReceiptMapper.class);

    public ReceiptDto getReceiptById(Long id){
        return receiptMapper.map(receiptRepository.findById(id).get());
    }
}
