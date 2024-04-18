package com.example.repository;

import com.example.model.GroupExpense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class GroupExpenseRepositoryImpl {

    @Autowired
    private EntityManager entityManager;

    public List<GroupExpense> findAllByExpenseGroup_GroupId(Long groupId) {
        TypedQuery<GroupExpense> query = entityManager.createQuery(
                "SELECT ge FROM GroupExpense ge WHERE ge.expenseGroup.groupId = :groupId",
                GroupExpense.class);
        query.setParameter("groupId", groupId);
        return query.getResultList();
    }
}
