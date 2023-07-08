package com.project.innova.layer.business.service;

import com.project.innova.layer.business.dto.ExpenseDto;
import com.project.innova.layer.business.dto.PersonDto;
import com.project.innova.layer.business.mapper.ExpenseMapper;
import com.project.innova.layer.business.mapper.PersonMapper;
import com.project.innova.layer.data.entity.Expense;
import com.project.innova.layer.data.entity.Person;
import com.project.innova.layer.data.repository.ExpenseRepository;
import com.project.innova.layer.data.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final PersonRepository personRepository;
    private final ExpenseMapper expenseMapper;
    private final PersonMapper personMapper;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, PersonRepository personRepository,
                              ExpenseMapper expenseMapper, PersonMapper personMapper) {
        this.expenseRepository = expenseRepository;
        this.personRepository = personRepository;
        this.expenseMapper = expenseMapper;
        this.personMapper = personMapper;
    }

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        Expense expense = expenseMapper.toEntity(expenseDto);
        expense.setPerson(personRepository.findById(expenseDto.getPerson().getPersonId())
                .orElseThrow(() -> new RuntimeException("Person not found")));
        Expense savedExpense = expenseRepository.save(expense);
        return expenseMapper.toDto(savedExpense);
    }

    @Override
    public ExpenseDto getExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return expenseMapper.toDto(expense);
    }

    @Override
    public List<ExpenseDto> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenseMapper.toDtoList(expenses);
    }

    @Override
    public List<ExpenseDto> getExpensesByPersonId(Long personId) {
        List<Expense> expenses = expenseRepository.findByPerson_PersonId(personId);
        return expenseMapper.toDtoList(expenses);
    }

    @Override
    public ExpenseDto updateExpense(Long expenseId, ExpenseDto expenseDto) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        expense.setDescription(expenseDto.getDescription());
        expense.setAmount(expenseDto.getAmount());
        expense.setDate(expenseDto.getDate());
        Expense updatedExpense = expenseRepository.save(expense);
        return expenseMapper.toDto(updatedExpense);
    }

    @Override
    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    @Override
    public double getTotalExpensesByPersonId(Long personId) {
        return expenseRepository.findByPerson_PersonId(personId)
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }


    @Override
    public List<Long> getAllPersonIds() {
        return personRepository.getAllPersonIds();
    }

    @Override
    public double getTotalExpensesByPersonId(Long personId, Timestamp date) {
        List<Expense> expenses = expenseRepository.findByPersonIdAndDate(personId, date);
        double totalExpenses = 0.0;
        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }
        return totalExpenses;
    }

    @Override
    public void updateTotalExpensesByPersonId(Long personId, double totalExpenses, Timestamp date) {
        // İlgili kişinin belirli bir tarihteki toplam masraflarını güncelle
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.setTotalExpenses(totalExpenses);
        personRepository.save(person);
    }



}
