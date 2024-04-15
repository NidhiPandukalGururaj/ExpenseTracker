package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "income_sources")
public class IncomeSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_source_id")
    private Integer incomeSourceId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "source_name", nullable = false)
    private String sourceName;

    @Column(name = "annual_income", nullable = false, precision = 15, scale = 2)
    private BigDecimal annualIncome;

    // Constructors, getters, setters, and other methods

    public IncomeSource() {
    }

    public IncomeSource(Long userId, String sourceName, BigDecimal annualIncome) {
        this.userId = userId;
        this.sourceName = sourceName;
        this.annualIncome = annualIncome;
    }

    public Integer getIncomeSourceId() {
        return incomeSourceId;
    }

    public void setIncomeSourceId(Integer incomeSourceId) {
        this.incomeSourceId = incomeSourceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(BigDecimal double1) {
        this.annualIncome = double1;
    }

    @Override
    public String toString() {
        return "Income{" +
                "incomeSourceId=" + incomeSourceId +
                ", userId=" + userId +
                ", sourceName='" + sourceName + '\'' +
                ", annualIncome=" + annualIncome +
                '}';
    }
}
