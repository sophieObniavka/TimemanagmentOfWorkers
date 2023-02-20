package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
}
