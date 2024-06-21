package com.csc3402.lab.formlogin.service;

import com.csc3402.lab.formlogin.model.Group;
import com.csc3402.lab.formlogin.model.Transaction;
import com.csc3402.lab.formlogin.model.User;
import com.csc3402.lab.formlogin.repository.GroupRepository;
import com.csc3402.lab.formlogin.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService{

    private final GroupRepository groupRepository;
    private final TransactionRepository transactionRepository;

    public GroupServiceImpl(GroupRepository groupRepository, TransactionRepository transactionRepository) {
        this.groupRepository = groupRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Group> listAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public List<Group> listGroupsByUserId(Long userId) {
        return groupRepository.findByUsersUserId(userId);
    }

    @Override
    public Group addNewGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void updateGroup(Long id, Group updatedGroup, User user) {
        Group existingGroup = findGroupById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid budget Id:" + id));

        // Copy updated values to the existing group
        existingGroup.setCategory(updatedGroup.getCategory());
        existingGroup.setStartDate(updatedGroup.getStartDate());
        existingGroup.setEndDate(updatedGroup.getEndDate());
        existingGroup.setBamount(updatedGroup.getBamount());
        existingGroup.setUser(user);

        groupRepository.save(existingGroup);
    }

    @Override
    public Optional<Group> findGroupById(Long budgetId) {
        return groupRepository.findById(budgetId);
    }

    @Override
    public void deleteGroup(Long budgetId) {
        groupRepository.deleteById(budgetId);
    }

//    @Override
//    public Map<Long, Double> calculateBudgetLeft(Long userId) {
//        Map<Long, Double> budgetLeft = new HashMap<>();
//        List<Group> groups = groupRepository.findByUsersUserId(userId);
//
//        for (Group group : groups) {
//            List<Transaction> transactions = transactionRepository.findByGroupBudgetId(group.getBudgetId());
//            double totalSpent = transactions.stream().mapToDouble(Transaction::getAmount).sum();
//            double budgetAmount = group.getBamount();
//            double remainingBudget = budgetAmount - totalSpent;
//            budgetLeft.put(group.getBudgetId(), remainingBudget);
//        }
//
//        return budgetLeft;
//    }

    @Override
    public List<Group> findByUser(User user) {
        return groupRepository.findByUsers(user);
    }
}

