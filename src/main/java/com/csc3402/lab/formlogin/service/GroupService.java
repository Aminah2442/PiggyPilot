package com.csc3402.lab.formlogin.service;

import com.csc3402.lab.formlogin.model.Group;
import com.csc3402.lab.formlogin.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GroupService {
    List<Group> listAllGroups();
    List<Group> listGroupsByUserId(Long userId);
    Group addNewGroup(Group group);
    void updateGroup(Long id, Group updatedGroup, User user);
    Optional<Group> findGroupById(Long budgetId);
    void deleteGroup(Long budgetId);

    Map<Long, Double> calculateBudgetLeft(Long userId);
    List<Group> findByUser(User user);
}
