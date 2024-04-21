package com.example.repository;

import com.example.model.GroupMember;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class GroupMemberRepositoryImpl {

    @Autowired
    private EntityManager entityManager;

    public List<User> findAllByExpenseGroup_GroupId(Long groupId) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT gm.user FROM GroupMember gm WHERE gm.expenseGroup.groupId = :groupId",
                User.class);
        query.setParameter("groupId", groupId);
        return query.getResultList();
    }
}