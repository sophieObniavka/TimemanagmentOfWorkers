package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.Assignment;
import obniavka.timemanagment.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findUserByEmail(String email);


    @Query("select u from User u")
    @Override
    Page<User> findAll(Pageable pageable);


    @Query("select u from User u where upper(u.firstName) like upper(concat('%', :keyword, '%')) or " +
            "upper(u.lastName) like upper(concat('%', :keyword, '%')) or " +
            "upper(u.email) like upper(concat('%', :keyword, '%')) or " +
            "upper(u.country) like upper(concat('%', :keyword, '%')) or " +
            "upper(u.city) like upper(concat('%', :keyword, '%')) or " +
            "upper(u.street) like upper(concat('%', :keyword, '%')) or " +
            "upper(u.position) like upper(concat('%', :keyword, '%'))")
    Page<User> findUserByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    void updateUserPassword(@Param(value = "password") String password, @Param(value = "id") Long id);

   @Transactional
    @Modifying
    @Query("update User u set u.assignments = :assignments where u.id = :userId")
    void updateAssignments(@Param("assignments") Set<Assignment> assignments, @Param("userId") Long userId);

   @Query("select u from User u where u.assignments.size > 0 AND u.expenses.size > 0")
   Page<User> findUsersWithExpensesToProcess(Pageable pageable);
}
