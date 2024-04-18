package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "group_members")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private ExpenseGroup expenseGroup;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters
    public Long getMemberId() {
        return memberId;
    }

    public ExpenseGroup getExpenseGroup() {
        return expenseGroup;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setExpenseGroup(ExpenseGroup expenseGroup) {
        this.expenseGroup = expenseGroup;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Setter for groupId
    public void setGroupId(Long groupId) {
        if (this.expenseGroup == null) {
            this.expenseGroup = new ExpenseGroup();
        }
        this.expenseGroup.setGroupId(groupId);
    }

    // Setter for userId
    public void setUserId(Long userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setUserId(userId);
    }
}
