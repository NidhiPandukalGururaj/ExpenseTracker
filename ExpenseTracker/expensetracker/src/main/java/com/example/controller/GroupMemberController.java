package com.example.controller;

import com.example.model.ExpenseGroup;
import com.example.model.GroupExpense;
import com.example.model.User;
import com.example.repository.ExpenseGroupRepository;
import com.example.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupMemberController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/")
    public String showGroupPage() {
        return "GroupManagement";
    }

    @GetMapping("/creategroup")
    public String showCreateGroupPage() {
        return "creategroup";
    }

    @GetMapping("/view-groups/{userId}")
    public String getGroupsByUser(@PathVariable Long userId, Model model) {
        try {
            List<ExpenseGroup> groups = groupService.getGroupsByUser(userId);
            model.addAttribute("groups", groups);
            return "Viewgroups";
        } catch (Exception e) {
            // Log the exception
            return "error"; // Return an error page or message
        }
    }

    @GetMapping("/{groupId}/quit")
    public String showQuitPage() {
        return "quitgroup";
    }

    @PostMapping("/{groupId}/quit")
    public String quitGroup(@PathVariable Long groupId, @RequestParam("userId") Long userId) {
        System.out.println(groupId + " " + userId);
        try {
            groupService.quitGroup(groupId, userId);
            return "redirect:/groups/view-groups/" + userId; // Redirect to the user's group view page
        } catch (Exception e) {
            // Log the exception
            return "error"; // Return an error page or message
        }
    }

    @GetMapping("/{groupId}/view")
    public String viewGroup(@PathVariable Long groupId, Model model) {
        try {
            ExpenseGroup group = groupService.getGroupById(groupId);
            model.addAttribute("group", group);
            return "view-group";
        } catch (Exception e) {
            // Log the exception
            return "error"; // Return an error page or message
        }
    }

    @GetMapping("/{groupId}/members")
    public String getGroupMembers(@PathVariable Long groupId, Model model) {
        try {
            List<User> members = groupService.getGroupMembers(groupId);
            model.addAttribute("members", members);
            return "ViewMembers";
        } catch (Exception e) {
            // Log the exception
            return "error"; // Return an error page or message
        }
    }

    @GetMapping("/{groupId}/expenses")
    public String getGroupExpenses(@PathVariable Long groupId, Model model) {
        try {
            List<GroupExpense> expenses = groupService.getGroupExpenses(groupId);
            model.addAttribute("expenses", expenses);
            return "ViewExpenses";
        } catch (Exception e) {
            // Log the exception
            return "error"; // Return an error page or message
        }
    }

    @GetMapping("/{groupId}/expenses/add")
    public String showGroupExpensePage(@PathVariable Long groupId, Model model) {
        model.addAttribute("groupId", groupId); // Add groupId to the model
        return "addexpense";
    }

    @PostMapping("/{groupId}/add-expense")
    public String addExpenseToGroup(@PathVariable Long groupId, @ModelAttribute GroupExpense expense,
            @RequestParam("userId") Long userId) {
        System.out.println(groupId + " " + expense);
        System.out.println("User ID: " + userId);

        try {
            // Set the user property of the GroupExpense object
            User user = new User(); // Replace this with the actual way you retrieve the user from the database
                                    // using userId
            user.setUserId(userId); // Assuming User has setId method to set the user ID
            expense.setUser(user);

            groupService.addExpenseToGroup(groupId, expense);
            return "redirect:/groups/" + groupId + "/expenses"; // Redirect to the group expenses page
        } catch (Exception e) {
            // Log the exception
            return "error"; // Return an error page or message
        }
    }

    @PostMapping("/creategroup")
    public String createGroup(@RequestParam Long userId, @RequestParam String groupName) {
        try {
            ExpenseGroup group = groupService.createGroup(userId, groupName);
            return "GroupManagement"; // Redirect to view the created group
        } catch (Exception e) {
            // Log the exception
            return "error"; // Return an error page or message
        }
    }
}
