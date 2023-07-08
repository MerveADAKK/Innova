package com.project.innova.layer.presentation.rest;

import com.project.innova.layer.business.dto.ExpenseDto;
import com.project.innova.layer.business.job.ExpenseAggregationJob;
import com.project.innova.layer.business.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final ExpenseAggregationJob expenseAggregationJob;

    @Autowired
    public ExpenseController(ExpenseService expenseService, ExpenseAggregationJob expenseAggregationJob) {
        this.expenseService = expenseService;
        this.expenseAggregationJob = expenseAggregationJob;
    }


    @GetMapping("/getExpensesByPersonId/{personId}")
    @PreAuthorize("hasRole('USER')")
    public List<ExpenseDto> getExpensesByPersonId(@PathVariable Long personId) {
        return expenseService.getExpensesByPersonId(personId);
    }


    @PostMapping("/createExpense")
    @PreAuthorize("hasRole('USER')")
    public ExpenseDto createExpense(@RequestBody ExpenseDto expenseDto) {
        return expenseService.createExpense(expenseDto);
    }

    @GetMapping("/getExpense/{expenseId}")
    @PreAuthorize("hasRole('USER')")
    public ExpenseDto getExpenseById(@PathVariable Long expenseId) {
        return expenseService.getExpenseById(expenseId);
    }

    @PutMapping("/updateExpense/{expenseId}")
    @PreAuthorize("hasRole('USER')")
    public ExpenseDto updateExpense(@PathVariable Long expenseId, @RequestBody ExpenseDto expenseDto) {
        return expenseService.updateExpense(expenseId, expenseDto);
    }

    @DeleteMapping("/deleteExpense/{expenseId}")
    @PreAuthorize("hasRole('USER')")
    public void deleteExpense(@PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId);
    }

    @GetMapping("/getTotalExpenses/{personId}")
    @PreAuthorize("hasRole('USER')")
    public double getTotalExpensesByPersonId(@PathVariable Long personId) {
        return expenseService.getTotalExpensesByPersonId(personId);
    }

    @PostMapping("/aggregateExpenses/daily")
    @PreAuthorize("hasRole('USER')")
    public String aggregateExpensesDaily() {
       // expenseAggregationJob.aggregateExpensesDaily();
        return expenseAggregationJob.aggregateExpensesDaily().toString();
    }

    @PostMapping("/aggregateExpenses/weekly")
    @PreAuthorize("hasRole('USER')")
    public String aggregateExpensesWeekly() {
        //expenseAggregationJob.aggregateExpensesWeekly();
        return expenseAggregationJob.aggregateExpensesWeekly().toString();
    }

    @PostMapping("/aggregateExpenses/monthly")
    @PreAuthorize("hasRole('USER')")
    public String aggregateExpensesMonthly() {
        //expenseAggregationJob.aggregateExpensesMonthly();
        return expenseAggregationJob.aggregateExpensesMonthly().toString();
    }
}






