package com.csc3402.lab.formlogin.service;

import com.csc3402.lab.formlogin.model.Group;
import com.csc3402.lab.formlogin.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class GroupServiceImpl implements GroupService{

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
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
    public Optional<Group> findGroupById(Long budgetId) {
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

    @Override
    public List<Group> listGroupsByUserId(Long userId) {
        return groupRepository.findByUsersUserId(userId);
    }

}
