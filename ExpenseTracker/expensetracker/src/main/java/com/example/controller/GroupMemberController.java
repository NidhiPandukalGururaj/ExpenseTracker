package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.service.GroupMemberService;
import com.example.service.GroupService;
import com.example.model.*;

@Controller
@RequestMapping("/groups")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/{userId}/my-groups")
    public String getGroupsByUser(@PathVariable Long userId, Model model) {
        List<ExpenseGroup> groups = groupService.getGroupsByUser(userId);
        model.addAttribute("groups", groups);
        return "my-groups";
    }

    @PostMapping("/create")
    public String createGroup(@ModelAttribute ExpenseGroup group, @RequestParam Long userId) {
        ExpenseGroup createdGroup = groupService.createGroup(userId, group.getGroupName());
        groupMemberService.addMemberToGroup(createdGroup.getGroupId(), userId); // Adding creator to the group members
        return "redirect:/groups/" + userId + "/my-groups";
    }

    @GetMapping("/{groupId}/members")
    public String getGroupMembers(@PathVariable Long groupId, Model model) {
        List<User> members = groupMemberService.getMembersByGroup(groupId);
        model.addAttribute("members", members);
        return "group-members";
    }

    @GetMapping("/{groupId}/expenses")
    public String getGroupExpenses(@PathVariable Long groupId, Model model) {
        List<GroupExpense> expenses = groupService.getGroupExpenses(groupId);
        model.addAttribute("expenses", expenses);
        return "group-expenses";
    }

    @PostMapping("/{groupId}/add-expense")
    public String addExpenseToGroup(@PathVariable Long groupId, @ModelAttribute GroupExpense expense) {
        groupService.addExpenseToGroup(groupId, expense);
        return "redirect:/groups/" + groupId + "/expenses";
    }

    @PostMapping("/{groupId}/quit")
    public String quitGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        groupService.quitGroup(groupId, userId);
        return "redirect:/groups/" + userId + "/my-groups";
    }
}