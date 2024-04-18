package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List; // Import List class
import com.example.model.*;

@Repository
public class GroupMemberRepositoryImpl implements GroupMemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Override
    public List<User> findUsersByGroupId(Long groupId) {
        return entityManager.createQuery("SELECT gm.user FROM GroupMember gm WHERE gm.group.id = :groupId", User.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }

    @Override
    public void deleteByGroupIdAndUserId(Long groupId, Long userId) {
        entityManager.createQuery("DELETE FROM GroupMember gm WHERE gm.group.id = :groupId AND gm.user.id = :userId")
                .setParameter("groupId", groupId)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public void addMemberToGroup(Long groupId, Long userId) {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        groupMember.setUserId(userId); // Assuming setter for userId in GroupMember class
        entityManager.persist(groupMember);
    }

    @Override
    public GroupMember save(GroupMember groupMember) {
        return groupMemberRepository.save(groupMember);
    }

}
