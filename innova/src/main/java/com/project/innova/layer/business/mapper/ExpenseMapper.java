package com.project.innova.layer.business.mapper;

import com.project.innova.layer.business.dto.ExpenseDto;
import com.project.innova.layer.data.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(source = "person.personId", target = "person.personId")
    ExpenseDto toDto(Expense expense);

    @Mapping(source = "person.personId", target = "person.personId")
    Expense toEntity(ExpenseDto expenseDto);

    List<ExpenseDto> toDtoList(List<Expense> expenses);
}
