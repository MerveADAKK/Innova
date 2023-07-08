package com.project.innova.layer.data.repository;

import com.project.innova.layer.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByUsername(String username);


    @Query("SELECT p.personId FROM Person p")
    List<Long> getAllPersonIds();

}
