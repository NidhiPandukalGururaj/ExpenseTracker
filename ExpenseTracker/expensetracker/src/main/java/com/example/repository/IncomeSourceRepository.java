package com.example.repository;

import com.example.model.IncomeSource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeSourceRepository extends JpaRepository<IncomeSource, Long> {

    List<IncomeSource> findAllByUserId(Long userId);

    IncomeSource findByUserId(Long userId);

    @Query("SELECT SUM(i.annualIncome) FROM Income i WHERE i.userId = :userId")
    Double findTotalIncomeByUserId(Long userId);
}

