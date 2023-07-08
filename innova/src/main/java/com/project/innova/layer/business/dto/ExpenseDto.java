package com.project.innova.layer.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ExpenseDto {
    private Long expenseId;
    private String description;
    private double amount;
    private Timestamp date;
    private PersonDto person;

}
