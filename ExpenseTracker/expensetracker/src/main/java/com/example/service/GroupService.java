package com.example.service;

import com.example.model.ExpenseGroup;
import com.example.model.GroupExpense;
import com.example.model.GroupMember;
import com.example.model.User;
import com.example.repository.ExpenseGroupRepository;
import com.example.repository.GroupExpenseRepository;
import com.example.repository.GroupMemberRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private ExpenseGroupRepository expenseGroupRepository;

    @Autowired
    private GroupExpenseRepository groupExpenseRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ExpenseGroup> getAllGroups() {
        return expenseGroupRepository.findAll();
    }

    @Transactional
    public void addMemberToGroup(Long groupId, Long userId) {
        ExpenseGroup group = expenseGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        GroupMember member = new GroupMember();
        member.setExpenseGroup(group);
        member.setUser(user);
        groupMemberRepository.save(member);
    }

    @Transactional
    public void quitGroup(Long groupId, Long userId) {
        groupMemberRepository.deleteByExpenseGroup_GroupIdAndUserId(groupId, userId);
    }

    @Transactional(readOnly = true)
    public List<ExpenseGroup> getGroupsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        return expenseGroupRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<User> getGroupMembers(Long groupId) {
        return groupMemberRepository.findAllByExpenseGroup_GroupId(groupId);
    }

    @Transactional
    public void addMembersToGroup(Long groupId, List<Long> memberIds) {
        ExpenseGroup group = expenseGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + groupId));

        List<User> members = userRepository.findAllById(memberIds);

        for (User member : members) {
            GroupMember groupMember = new GroupMember();
            groupMember.setExpenseGroup(group);
            groupMember.setUser(member);
            groupMemberRepository.save(groupMember);
        }
        System.out.println(groupId);
    }

    @Transactional
    public void addUserToGroup(Long groupId, Long userId) {
        ExpenseGroup group = expenseGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        GroupMember member = new GroupMember();
        member.setExpenseGroup(group);
        member.setUser(user);
        groupMemberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<GroupExpense> getGroupExpenses(Long groupId) {
        return groupExpenseRepository.findAllByExpenseGroup_GroupId(groupId);
    }

    @Transactional
    public void addExpenseToGroup(Long groupId, GroupExpense expense) {
        try {
            ExpenseGroup group = expenseGroupRepository.findById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + groupId));

            expense.setExpenseGroup(group);

            // Save the expense to the database
            groupExpenseRepository.save(expense);

            // Log success
            System.out.println("Expense added successfully to group with ID: " + groupId);

        } catch (Exception e) {
            // Log the exception
            System.err.println("Error adding expense to group with ID: " + groupId);
            e.printStackTrace();
            throw new RuntimeException("Failed to add expense to group with ID: " + groupId, e);
        }
    }

    @Transactional(readOnly = true)
    public ExpenseGroup getGroupById(Long groupId) {
        return expenseGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + groupId));
    }

    @Transactional
    public ExpenseGroup createGroup(Long userId, String groupName) {
        // Retrieve the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Create a new expense group
        ExpenseGroup group = new ExpenseGroup();
        group.setGroupName(groupName);
        group.setUser(user);

        // Save the group to the database
        return expenseGroupRepository.save(group);
    }

}