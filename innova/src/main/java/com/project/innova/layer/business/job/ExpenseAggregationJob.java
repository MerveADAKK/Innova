package com.project.innova.layer.business.job;

import com.project.innova.layer.business.dto.ExpenseDto;
import com.project.innova.layer.business.service.ExpenseService;
import com.project.innova.layer.data.entity.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;



@Component
public class ExpenseAggregationJob {


    private final ExpenseService expenseService;

    @Autowired
    public ExpenseAggregationJob(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Scheduled(cron = "0 0 0 * * *") // Daily job at midnight
    public List<ExpenseDto> aggregateExpensesDaily() {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        List<Long> personIds = expenseService.getAllPersonIds(); // Tüm kullanıcıların ID'lerini al
        List<ExpenseDto> aggregatedExpenses = new ArrayList<>();

        for (Long personId : personIds) {
            double totalExpenses = expenseService.getTotalExpensesByPersonId(personId, currentDate);
            expenseService.updateTotalExpensesByPersonId(personId, totalExpenses, currentDate);
            List<ExpenseDto> expenses = expenseService.getExpensesByPersonId(personId);
            aggregatedExpenses.addAll(expenses);
        }
        return aggregatedExpenses;
    }



    @Scheduled(cron = "0 0 0 * * 1") // Weekly job on Monday at midnight
    public List<ExpenseDto> aggregateExpensesWeekly() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMonday = now.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDateTime nextMondayMidnight = LocalDateTime.of(nextMonday.toLocalDate(), LocalTime.MIDNIGHT);
        Timestamp currentDate = Timestamp.valueOf(nextMondayMidnight);

        List<Long> personIds = expenseService.getAllPersonIds(); // Tüm kullanıcıların ID'lerini al
        List<ExpenseDto> aggregatedExpenses = new ArrayList<>();

        for (Long personId : personIds) {
            double totalExpenses = expenseService.getTotalExpensesByPersonId(personId, currentDate);
            expenseService.updateTotalExpensesByPersonId(personId, totalExpenses, currentDate);
            List<ExpenseDto> expenses = expenseService.getExpensesByPersonId(personId);
            aggregatedExpenses.addAll(expenses);
        }
        return aggregatedExpenses;
    }


    @Scheduled(cron = "0 0 0 1 * *") // Monthly job on the 1st day of the month at midnight
    public List<ExpenseDto> aggregateExpensesMonthly() {
        LocalDate currentDate = LocalDate.now().withDayOfMonth(1);
        LocalDateTime midnight = LocalDateTime.of(currentDate, LocalTime.MIDNIGHT);
        Timestamp currentTimestamp = Timestamp.valueOf(midnight);
        List<Long> personIds = expenseService.getAllPersonIds();
        List<ExpenseDto> aggregatedExpenses = new ArrayList<>();


        for (Long personId : personIds) {
            double totalExpenses = expenseService.getTotalExpensesByPersonId(personId, currentTimestamp);
            expenseService.updateTotalExpensesByPersonId(personId, totalExpenses, currentTimestamp);
            List<ExpenseDto> expenses = expenseService.getExpensesByPersonId(personId);
            aggregatedExpenses.addAll(expenses);
        }
        return aggregatedExpenses;
    }



}

