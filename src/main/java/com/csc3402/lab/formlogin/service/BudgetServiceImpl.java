package com.csc3402.lab.formlogin.service;

import com.csc3402.lab.formlogin.model.Budget;
import com.csc3402.lab.formlogin.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public List<Budget> listGroupsByUserId(Long userId) {
        return budgetRepository.findByUsersUserId(userId);
    }

    @Override
    public List<Budget> listAllGroups() {
        return budgetRepository.findAll();
    }

    @Override
    public Budget addNewGroup(Budget group) {
        return budgetRepository.save(group);
    }

    @Override
    public Optional<Budget> findGroupById(Long budgetId) {
        return budgetRepository.findById(budgetId);
    }


    @Override
    public Budget updateGroup(Budget group) {
        return budgetRepository.save(group);
    }

    @Override
    public void deleteGroup(Budget group) {
        budgetRepository.delete(group);
    }



}
