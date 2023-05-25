package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.Assignment;
import obniavka.timemanagment.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Page<Assignment> findAssignmentByUsersContains(final User user, Pageable pageable);

    @Query("select a from Assignment a")
    @Override
    Page<Assignment> findAll(Pageable pageable);

    @Query("select a from Assignment a join a.users u where upper(a.name) like upper(concat('%', :keyword, '%')) or " +
            "upper(a.country) like upper(concat('%', :keyword, '%')) or " +
            "upper(a.city) like upper(concat('%', :keyword, '%')) or " +
            "upper(u.firstName) like upper(concat('%', :keyword, '%')) or " +
            "upper(u.lastName) like upper(concat('%', :keyword, '%')) or " +
            "upper(a.description) like upper(concat('%', :keyword, '%'))")
    Page<Assignment> findAssignmentByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
