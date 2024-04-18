package com.example.repository;

import java.util.List;
import java.util.Optional;
import com.example.model.GroupExpense;

public interface GroupExpenseRepository {

    /**
     * Save a group expense.
     *
     * @param expense the group expense to save
     * @return the saved group expense
     */
    GroupExpense save(GroupExpense expense);

    /**
     * Find a group expense by ID.
     *
     * @param id the expense ID
     * @return an Optional containing the found group expense, or empty if not found
     */
    Optional<GroupExpense> findById(Long id);

    /**
     * Find group expenses by group ID.
     *
     * @param groupId the group ID
     * @return a list of group expenses for the specified group ID
     */
    List<GroupExpense> findByGroupId(Long groupId);

    /**
     * Delete a group expense by ID.
     *
     * @param id the expense ID
     */
    void deleteById(Long id);

    /**
     * Delete group expenses by group ID.
     *
     * @param groupId the group ID
     */
    void deleteByGroupId(Long groupId);

    /**
     * Find all group expenses.
     *
     * @return a list of all group expenses
     */
    List<GroupExpense> findAll();
}
