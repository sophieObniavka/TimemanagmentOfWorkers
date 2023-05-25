package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("select i from Invoice i")
    @Override
    Page<Invoice> findAll(Pageable pageable);

}
