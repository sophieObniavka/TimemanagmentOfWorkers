package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.Task;
import obniavka.timemanagment.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select t from Task t")
    @Override
    Page<Task> findAll(Pageable pageable);

    @Query("select t from Task t where upper(t.name) like upper(concat('%', :keyword, '%')) or " +
            "upper(t.description) like upper(concat('%', :keyword, '%')) or " +
            "upper(t.user.firstName) like upper(concat('%', :keyword, '%')) or " +
            "upper(t.user.lastName) like upper(concat('%', :keyword, '%'))")
    Page<Task> findTaskByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("select t from Task t where t.user = :user and upper(t.name) like upper(concat('%', :keyword, '%')) or " +
            "upper(t.description) like upper(concat('%', :keyword, '%')) or " +
            "upper(t.user.firstName) like upper(concat('%', :keyword, '%')) or " +
            "upper(t.user.lastName) like upper(concat('%', :keyword, '%')) ")
    Page<Task> findByUser(@Param("user") User user, @Param("keyword") String keyword, Pageable pageable);

    Page<Task> findByUser(final User user, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.deadline BETWEEN ?1 AND ?2 AND t.user = ?3 AND (t.isDone IS null or t.isDone = false)")
    List<Task> findByDeadlineBetweenAndUser(LocalDate yesterday, LocalDate tomorrow, User user);
}
