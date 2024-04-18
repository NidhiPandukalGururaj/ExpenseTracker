package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.repository.*;
import com.example.model.ExpenseGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.*;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupExpenseRepository groupExpenseRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public List<ExpenseGroup> getGroupsByUser(Long userId) {
        return groupRepository.findByCreatorId(userId);
    }

    public ExpenseGroup createGroup(Long creatorId, String groupName) {
        ExpenseGroup group = new ExpenseGroup();
        group.setUser(creatorId);
        group.setGroupName(groupName);
        groupRepository.save(group);

        // Adding creator to the group members
        groupMemberRepository.addMemberToGroup(group.getGroupId(), creatorId);

        return group;
    }

    public void quitGroup(Long groupId, Long userId) {
        groupMemberRepository.deleteByGroupIdAndUserId(groupId, userId);
    }

    public List<GroupExpense> getGroupExpenses(Long groupId) {
        return groupRepository.findbyGroupId(groupId);
    }

    public void addExpenseToGroup(Long groupId, GroupExpense expense) {
        expense.setId(groupId);
        groupExpenseRepository.save(expense);
    }

    // Other methods as needed...

    // Getters and setters for autowired repositories
    public GroupRepository getGroupRepository() {
        return groupRepository;
    }

    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public GroupMemberRepository getGroupMemberRepository() {
        return groupMemberRepository;
    }

    public void setGroupMemberRepository(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }

}
