package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}