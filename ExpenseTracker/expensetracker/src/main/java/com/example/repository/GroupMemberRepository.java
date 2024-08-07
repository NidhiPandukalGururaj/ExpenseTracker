package com.example.repository;

import com.example.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.model.User;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<User> findAllByExpenseGroup_GroupId(Long groupId);

    @Transactional
    void deleteByExpenseGroup_GroupIdAndUserId(Long groupId, Long userId);
}
