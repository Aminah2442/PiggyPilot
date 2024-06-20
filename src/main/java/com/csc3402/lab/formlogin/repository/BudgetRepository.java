package com.csc3402.lab.formlogin.repository;



import com.csc3402.lab.formlogin.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUsersUserId(Long userId);
}
