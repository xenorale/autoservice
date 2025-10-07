package ru.voronezh.autoservice.service;

import ru.voronezh.autoservice.model.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
}