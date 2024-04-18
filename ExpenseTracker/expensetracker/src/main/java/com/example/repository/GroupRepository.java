package com.example.repository;

import java.util.Optional;
import java.util.List;
import com.example.model.ExpenseGroup;
import com.example.model.GroupExpense;

public interface GroupRepository {

    /**
     * Save an expense group.
     *
     * @param group the expense group to save
     * @return the saved expense group
     */
    ExpenseGroup save(ExpenseGroup group);

    /**
     * Find an expense group by ID.
     *
     * @param groupId the group ID
     * @return an Optional containing the found expense group, or empty if not found
     */
    Optional<ExpenseGroup> findById(Long groupId);

    List<GroupExpense> findbyGroupId(Long groupId);

    List<ExpenseGroup> findByCreatorId(Long creatorId);
}
