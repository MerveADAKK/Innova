package com.project.innova.layer.business.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonDto {
    private Long PersonId;
    private String name;
    private String username;
    private String password;
    private Double totalExpenses;

}
