package com.example.service;

import com.example.model.ExpenseGroup;
import com.example.model.GroupExpense;
import com.example.model.GroupMember;
import com.example.model.User;
import com.example.repository.ExpenseGroupRepository;
import com.example.repository.GroupExpenseRepository;
import com.example.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private ExpenseGroupRepository expenseGroupRepository;

    @Autowired
    private GroupExpenseRepository groupExpenseRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Transactional
    public ExpenseGroup createGroup(Long userId, String groupName) {
        ExpenseGroup group = new ExpenseGroup();
        group.setGroupName(groupName);
        // TODO: Fetch user from UserRepository
        // User user = userRepository.findById(userId).orElse(null);
        // group.setUser(user);
        return expenseGroupRepository.save(group);
    }

    @Transactional
    public void addMemberToGroup(Long groupId, Long userId) {
        Optional<ExpenseGroup> groupOptional = expenseGroupRepository.findById(groupId);
        // TODO: Fetch user from UserRepository
        // User user = userRepository.findById(userId).orElse(null);

        if (groupOptional.isPresent()) {
            ExpenseGroup group = groupOptional.get();
            GroupMember member = new GroupMember();
            member.setExpenseGroup(group);
            // member.setUser(user);
            groupMemberRepository.save(member);
        }
    }

    @Transactional
    public void deleteMemberFromGroup(Long memberId) {
        groupMemberRepository.deleteById(memberId);
    }

    @Transactional(readOnly = true)
    public List<User> getGroupMembers(Long groupId) {
        return groupMemberRepository.findAllByExpenseGroup_GroupId(groupId);
    }

    @Transactional(readOnly = true)
    public List<ExpenseGroup> getGroupsByUser(Long userId) {
        // TODO: Fetch user from UserRepository
        // User user = userRepository.findById(userId).orElse(null);
        // return expenseGroupRepository.findByUser(user);
        return expenseGroupRepository.findAll(); // Placeholder; replace with actual implementation
    }

    @Transactional
    public void quitGroup(Long groupId, Long userId) {
        // You might want to add additional logic to prevent the group creator from
        // quitting
        deleteMemberFromGroup(userId);
    }

    @Transactional
    public void addExpenseToGroup(Long groupId, GroupExpense expense) {
        Optional<ExpenseGroup> groupOptional = expenseGroupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            ExpenseGroup group = groupOptional.get();
            expense.setExpenseGroup(group);
            groupExpenseRepository.save(expense);
        }
    }

    @Transactional(readOnly = true)
    public List<GroupExpense> getGroupExpenses(Long groupId) {
        return groupExpenseRepository.findAllByExpenseGroup_GroupId(groupId);
    }

    @Transactional
    public void deleteExpenseFromGroup(Long expenseId) {
        groupExpenseRepository.deleteById(expenseId);
    }

    @Transactional
    public void editExpenseFromGroup(GroupExpense expense) {
        groupExpenseRepository.save(expense);
    }
}
