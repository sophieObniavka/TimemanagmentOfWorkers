package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.data.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {

    @Query("SELECT v from Vacation v WHERE v.confirmed = NULL ORDER BY v.created ASC ")
    Page<Vacation> findAllVacationByBeingNotConfirmedYet(Pageable pageable);


    @Query("SELECT v from Vacation v WHERE v.confirmed != NULL ORDER BY v.created ASC ")
    Page<Vacation> findAllVacationByBeingConfirmed(Pageable pageable);

    Page<Vacation> findAllVacationByUserOrderByCreatedDesc(final User user, Pageable pageable);
}
