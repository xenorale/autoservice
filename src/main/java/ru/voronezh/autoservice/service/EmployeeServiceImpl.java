package ru.voronezh.autoservice.service;

import ru.voronezh.autoservice.model.Employee;
import ru.voronezh.autoservice.repository.EmployeeRepository;
import ru.voronezh.autoservice.repository.EmployeeRepositoryImpl;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository = new EmployeeRepositoryImpl();

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }
}