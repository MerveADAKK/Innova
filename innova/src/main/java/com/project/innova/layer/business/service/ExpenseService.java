package com.project.innova.layer.business.service;

import com.project.innova.layer.business.dto.ExpenseDto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    ExpenseDto createExpense(ExpenseDto expenseDto);
    ExpenseDto getExpenseById(Long expenseId);
    List<ExpenseDto> getAllExpenses();
    List<ExpenseDto> getExpensesByPersonId(Long personId);
    ExpenseDto updateExpense(Long expenseId, ExpenseDto expenseDto);
    void deleteExpense(Long expenseId);
    double getTotalExpensesByPersonId(Long personId);


        // Diğer yöntemler burada...

    List<Long> getAllPersonIds();

    double getTotalExpensesByPersonId(Long personId, Timestamp date);

    void updateTotalExpensesByPersonId(Long personId, double totalExpenses, Timestamp date);


}
