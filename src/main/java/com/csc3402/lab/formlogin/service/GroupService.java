package com.csc3402.lab.formlogin.service;

import com.csc3402.lab.formlogin.model.Group;
import com.csc3402.lab.formlogin.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GroupService {
    List<Group> listAllGroups();
    Group addNewGroup(Group group);
    Optional<Group> findGroupbyId(Long budgetId);
    Group updateGroup(Group group);
    void deleteGroup(Group group);
    List<Group> listGroupsByUserId(Long userId);
    Map<Long, Double> calculateBudgetLeft(Long userId);
    List<Group> findByUser(User user);
}
