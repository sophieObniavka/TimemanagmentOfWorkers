package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.Expense;
import obniavka.timemanagment.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("select e from Expense e")
    @Override
    Page<Expense> findAll(Pageable pageable);

    Page<Expense> findByUser(User user, Pageable pageable);

    @Query("SELECT e FROM Expense e where e.accepted is null")
    Page<Expense> findUnprocessedExpenses(Pageable pageable);
}
