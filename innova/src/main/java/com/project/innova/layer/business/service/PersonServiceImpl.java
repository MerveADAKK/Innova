package com.project.innova.layer.business.service;

import com.project.innova.layer.business.dto.PersonDto;
import com.project.innova.layer.business.mapper.PersonMapper;
import com.project.innova.layer.data.entity.Person;
import com.project.innova.layer.data.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @Override
    public PersonDto createPerson(PersonDto personDto) {
        Person person = personMapper.toEntity(personDto);
        Person savedPerson = personRepository.save(person);
        return personMapper.toDto(savedPerson);
    }

    @Override
    public PersonDto getPersonById(Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return personMapper.toDto(person);
    }

    @Override
    public PersonDto getPersonByUsername(String username) {
        Person person = personRepository.findByUsername(username);
        if (person == null) {
            throw new RuntimeException("Person not found");
        }
        return personMapper.toDto(person);
    }

    @Override
    public List<PersonDto> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        return personMapper.toDtoList(persons);
    }

    @Override
    public List<Long> getAllPersonIds() {
        return personRepository.getAllPersonIds();
    }

    @Override
    public PersonDto updatePerson(Long personId, PersonDto personDto) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.setName(personDto.getName());
        person.setUsername(personDto.getUsername());
        person.setPassword(personDto.getPassword());
        Person updatedPerson = personRepository.save(person);
        return personMapper.toDto(updatedPerson);
    }

    @Override
    public void deletePerson(Long personId) {
        personRepository.deleteById(personId);
    }
}
