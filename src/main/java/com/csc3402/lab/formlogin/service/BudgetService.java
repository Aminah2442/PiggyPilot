package com.csc3402.lab.formlogin.service;


import com.csc3402.lab.formlogin.model.Budget;
import java.util.List;
import java.util.Optional;

public interface BudgetService {
    List<Budget> listAllGroups();
    Budget addNewGroup(Budget group);

    Optional<Budget> findGroupById(Long budgetId);
    Budget updateGroup(Budget group);
    void deleteGroup(Budget group);

    List<Budget> listGroupsByUserId(Long userId);
}
