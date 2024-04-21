package com.example.repository;

import com.example.model.ExpenseGroup;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseGroupRepository extends JpaRepository<ExpenseGroup, Long> {
    List<ExpenseGroup> findByUser(User user);
}