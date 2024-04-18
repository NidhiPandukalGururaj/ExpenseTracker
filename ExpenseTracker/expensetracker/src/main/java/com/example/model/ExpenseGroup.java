package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "expensegroups")
public class ExpenseGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters
    public Long getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Setter to accept a Long and set the User
    public void setUser(Long userId) {
        // Assuming you have a UserRepository to find the User by ID
        // UserRepository userRepository = ...; // Initialize and inject userRepository
        // this.user = userRepository.findById(userId).orElse(null);
    }
}
