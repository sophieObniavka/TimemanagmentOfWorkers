package obniavka.timemanagment.repository;


import obniavka.timemanagment.data.Report;
import obniavka.timemanagment.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ReportRepository  extends JpaRepository<Report, Long> {


    List<Report> findAllByUserOrderByWorkDayAsc(final User user);


}
