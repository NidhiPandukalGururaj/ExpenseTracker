package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.model.*;
import com.example.repository.*;

@Service
public class GroupMemberService {
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private GroupRepository expenseGroupRepository;

    public GroupMember addMemberToGroup(Long groupId, Long memberId) {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        groupMember.setUserId(memberId); // Assuming setter for userId in GroupMember class
        return groupMemberRepository.save(groupMember);
    }

    public void deleteMemberFromGroup(Long groupId, Long memberId) {
        groupMemberRepository.deleteByGroupIdAndUserId(groupId, memberId);
    }

    public List<User> getMembersByGroup(Long groupId) {
        return groupMemberRepository.findUsersByGroupId(groupId);
    }

    public ExpenseGroup createGroup(Long creatorId, String groupName) {
        ExpenseGroup group = new ExpenseGroup();
        group.setUser(creatorId); // Assuming setter for user in ExpenseGroup class
        group.setGroupName(groupName);
        expenseGroupRepository.save(group);

        // Adding creator to the group members
        addMemberToGroup(group.getGroupId(), creatorId);

        return group;
    }

    // Getters and setters for autowired repositories
    public GroupMemberRepository getGroupMemberRepository() {
        return groupMemberRepository;
    }

    public void setGroupMemberRepository(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }

    public GroupRepository getExpenseGroupRepository() {
        return expenseGroupRepository;
    }

    public void setExpenseGroupRepository(GroupRepository expenseGroupRepository) {
        this.expenseGroupRepository = expenseGroupRepository;
    }
}