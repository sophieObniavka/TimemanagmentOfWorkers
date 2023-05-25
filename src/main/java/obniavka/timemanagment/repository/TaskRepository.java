package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.Task;
import obniavka.timemanagment.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
