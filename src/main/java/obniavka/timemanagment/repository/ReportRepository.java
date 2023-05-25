package obniavka.timemanagment.repository;


import obniavka.timemanagment.data.Report;
import obniavka.timemanagment.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository  extends JpaRepository<Report, Long> {

    List<Report> findAllByUserOrderByWorkDayAsc(final User user);

    @Query("select r from Report r")
    @Override
    Page<Report> findAll(Pageable pageable);

    Page<Report> findByWorkDayIsBetweenOrderByWorkDayAsc(LocalDate start, LocalDate end, Pageable pageable);

    List<Report> findByWorkDayIsBetweenAndUserOrderByWorkDayAsc(LocalDate start, LocalDate end, final User user);
}
