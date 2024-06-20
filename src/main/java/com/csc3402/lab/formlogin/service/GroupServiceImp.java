package com.csc3402.lab.formlogin.service;

import com.csc3402.lab.formlogin.model.Group;
import com.csc3402.lab.formlogin.model.Transaction;
import com.csc3402.lab.formlogin.model.User;
import com.csc3402.lab.formlogin.repository.GroupRepository;
import com.csc3402.lab.formlogin.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImp implements GroupService{

    private final GroupRepository groupRepository;
    private final TransactionRepository transactionRepository;

    public GroupServiceImp(GroupRepository groupRepository, TransactionRepository transactionRepository) {
        this.groupRepository = groupRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public List<Group> listAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group addNewGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Optional<Group> findGroupbyId(Long budgetId) {
        return groupRepository.findById(budgetId);
    }


    @Override
    public Group updateGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void deleteGroup(Group group) {
        groupRepository.delete(group);
    }

//    @Override
//    public List<String> getDistinctCategories() {
//        return groupRepository.findCategories();
//    }

    @Override
    public List<Group> listGroupsByUserId(Long userId) {
        // Implementation to list groups by user ID
        return groupRepository.findByUsersUserId(userId);
    }

    @Override
    public Map<Long, Double> calculateBudgetLeft(Long userId) {
        List<Group> groups = listGroupsByUserId(userId);
        List<Transaction> transactions = transactionRepository.findAll();

        Map<Long, Double> budgetLeft = groups.stream()
                .collect(Collectors.toMap(
                        Group::getBudgetId,
                        Group::getBamount
                ));

        transactions.forEach(transaction -> {
            Long groupId = transaction.getGroup().getBudgetId();
            if (budgetLeft.containsKey(groupId)) {
                budgetLeft.put(groupId, budgetLeft.get(groupId) - transaction.getAmount());
            }
        });

        return budgetLeft;
    }

    @Override
    public List<Group> findByUser(User user) {
        return groupRepository.findByUsers(user);
    }
}
