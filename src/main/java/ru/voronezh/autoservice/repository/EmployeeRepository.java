package ru.voronezh.autoservice.repository;

import ru.voronezh.autoservice.model.Employee;
import java.util.List;

public interface EmployeeRepository {
    List<Employee> findAll();
    void create(Employee employee);
}