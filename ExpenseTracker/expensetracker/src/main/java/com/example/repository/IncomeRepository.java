package com.example.repository;

import com.example.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    Income findByUserId(Long userId);

    @Query("SELECT SUM(i.annualIncome) FROM Income i WHERE i.userId = :userId")
    Double findTotalIncomeByUserId(Long userId);
}
