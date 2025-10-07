package ru.voronezh.autoservice.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Employee {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private BigDecimal salary;
    private Integer experience;
}