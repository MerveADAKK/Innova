package com.project.innova.layer.business.service;

import com.project.innova.layer.business.dto.PersonDto;
import java.util.List;

public interface PersonService {
    PersonDto createPerson(PersonDto personDto);
    PersonDto getPersonById(Long personId);
    PersonDto getPersonByUsername(String username);
    List<PersonDto> getAllPersons();

    List<Long> getAllPersonIds();
    PersonDto updatePerson(Long personId, PersonDto personDto);
    void deletePerson(Long personId);
}
