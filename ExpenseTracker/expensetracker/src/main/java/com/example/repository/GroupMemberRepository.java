package com.example.repository;

import com.example.model.GroupMember;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    /**
     * Find users by group ID.
     *
     * @param groupId the group ID
     * @return the list of users in the group
     */
    List<User> findUsersByGroupId(Long groupId);

    /**
     * Delete a group member by group ID and user ID.
     *
     * @param groupId the group ID
     * @param userId  the user ID
     */
    void deleteByGroupIdAndUserId(Long groupId, Long userId);

    /**
     * Add a member to a group.
     *
     * @param groupId the group ID
     * @param userId  the user ID
     */
    void addMemberToGroup(Long groupId, Long userId);

    /**
     * Save a group member.
     *
     * @param groupMember the group member to save
     * @return the saved group member
     */
    GroupMember save(GroupMember groupMember);
}
