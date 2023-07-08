package com.project.innova.layer.data.repository;

import com.project.innova.layer.data.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query(value = "SELECT * FROM expenses WHERE person_id= :personId ;",nativeQuery = true)
    List<Expense> findByPerson_PersonId(@Param("personId") long personId);

    @Query(value = "SELECT * FROM expenses WHERE person_id = :personId AND date = :date", nativeQuery = true)
    List<Expense> findByPersonIdAndDate(@Param("personId") Long personId, @Param("date") Timestamp date);

}
