package com.project.innova.layer.business.mapper;

import com.project.innova.layer.business.dto.PersonDto;
import com.project.innova.layer.data.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "expenseList", target = "expenseList", ignore = true)
    PersonDto toDto(Person person);

    @Mapping(target = "expenseList", ignore = true)
    Person toEntity(PersonDto personDto);

    List<PersonDto> toDtoList(List<Person> persons);
}
