package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.SickLeave;
import obniavka.timemanagment.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SickLeaveRepository extends JpaRepository<SickLeave, Long> {


    @Query("SELECT s from SickLeave s WHERE s.confirmed = NULL ORDER BY s.created DESC ")
    Page<SickLeave> findAllSickLeaveByBeingNotConfirmedYet(Pageable pageable);

    @Query("SELECT s FROM SickLeave s WHERE s.confirmed != NULL ORDER BY s.created DESC ")
    Page<SickLeave> findAllSickLeaveByBeingConfirmed(Pageable pageable);

    Page<SickLeave> findAllSickLeaveByUserOrderByCreated(final User user, Pageable pageable);

}
