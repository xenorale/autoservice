package ru.voronezh.autoservice.service;

import ru.voronezh.autoservice.model.Repair;
import ru.voronezh.autoservice.repository.RepairRepository;
import ru.voronezh.autoservice.repository.RepairRepositoryImpl;

import java.util.List;

public class RepairServiceImpl implements RepairService {
    private final RepairRepository repository = new RepairRepositoryImpl();

    @Override
    public void addRepair(Repair repair) {
        if (repair.getMalfunctionDescription() == null || repair.getMalfunctionDescription().isEmpty()) {
            throw new IllegalArgumentException("Описание обязательно!");
        }
        repository.create(repair);
    }

    @Override
    public List<Repair> getAllRepairs() {
        return repository.findAll();
    }
}