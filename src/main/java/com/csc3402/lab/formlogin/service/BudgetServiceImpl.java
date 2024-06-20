package com.csc3402.lab.formlogin.service;

import com.csc3402.lab.formlogin.model.Group;
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
    public List<Group> listGroupsByUserId(Long userId) {
        return budgetRepository.findByUsersUserId(userId);
    }

    @Override
    public List<Group> listAllGroups() {
        return budgetRepository.findAll();
    }

    @Override
    public Group addNewGroup(Group group) {
        return budgetRepository.save(group);
    }

    @Override
    public Optional<Group> findGroupById(Long budgetId) {
        return budgetRepository.findById(budgetId);
    }


    @Override
    public Group updateGroup(Group group) {
        return budgetRepository.save(group);
    }

    @Override
    public void deleteGroup(Group group) {
        budgetRepository.delete(group);
    }



}
