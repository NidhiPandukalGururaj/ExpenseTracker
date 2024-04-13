package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.IncomeSourceRepository;
import com.example.model.IncomeSource;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeSourceService {

    private final IncomeSourceRepository incomeSourceRepository;

    @Autowired
    public IncomeSourceService(IncomeSourceRepository incomeSourceRepository) {
        this.incomeSourceRepository = incomeSourceRepository;
    }

    public List<IncomeSource> getAllIncomeSourcesByUserId(Long userId) {
        return incomeSourceRepository.findAllByUserId(userId);
    }

    public Optional<IncomeSource> getIncomeSourceById(Long incomeSourceId) {
        return incomeSourceRepository.findById(incomeSourceId);
    }

    public IncomeSource addIncomeSource(IncomeSource incomeSource) {
        return incomeSourceRepository.save(incomeSource);
    }

    public IncomeSource updateIncomeSource(IncomeSource incomeSource) {
        return incomeSourceRepository.save(incomeSource);
    }

    public void deleteIncomeSource(Long incomeSourceId) {
        incomeSourceRepository.deleteById(incomeSourceId);
    }
}
