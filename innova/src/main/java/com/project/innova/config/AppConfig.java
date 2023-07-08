package com.project.innova.config;

import com.project.innova.layer.business.dto.ExpenseDto;
import com.project.innova.layer.business.dto.PersonDto;
import com.project.innova.layer.business.mapper.ExpenseMapper;
import com.project.innova.layer.business.mapper.PersonMapper;
import com.project.innova.layer.data.entity.Expense;
import com.project.innova.layer.data.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public PersonMapper personMapper(){
        return new PersonMapper() {
            @Override
            public PersonDto toDto(Person person) {
                return new PersonDto(person.getPersonId(),
                        person.getName(),
                        person.getUsername(),
                        person.getPassword(),
                        person.getTotalExpenses());
            }

            @Override
            public Person toEntity(PersonDto personDto) {
                return new Person(
                        personDto.getPersonId(),
                        personDto.getName(),
                        personDto.getUsername(),
                        personDto.getPassword(),
                        personDto.getTotalExpenses());
            }

            @Override
            public List<PersonDto> toDtoList(List<Person> persons) {
                List<PersonDto> personDtos = new ArrayList<>();
                for (Person person : persons) {
                    personDtos.add(toDto(person));
                }
                return personDtos;
            }
        };
    }

    @Bean
    public ExpenseMapper expenseMapper(PersonMapper personMapper){
        return new ExpenseMapper() {
            @Override
            public ExpenseDto toDto(Expense expense) {
                return new ExpenseDto(
                        expense.getExpenseId(),
                        expense.getDescription(),
                        expense.getAmount(),
                        expense.getDate(),
                        personMapper.toDto(expense.getPerson()));
            }

            @Override
            public Expense toEntity(ExpenseDto expenseDto) {
                return new Expense(expenseDto.getExpenseId(),
                        expenseDto.getDescription(),
                        expenseDto.getAmount(),
                        expenseDto.getDate(),
                        personMapper.toEntity(expenseDto.getPerson()));
            }

            @Override
            public List<ExpenseDto> toDtoList(List<Expense> expenses) {
                List<ExpenseDto> dtoList = new ArrayList<>();
                for (Expense expens : expenses) {
                    dtoList.add(new ExpenseDto(
                            expens.getExpenseId(),
                            expens.getDescription(),
                            expens.getAmount(),
                            expens.getDate(),
                            personMapper.toDto(expens.getPerson()))
                    );
                }
                return dtoList;
            }
        };
    }

}
