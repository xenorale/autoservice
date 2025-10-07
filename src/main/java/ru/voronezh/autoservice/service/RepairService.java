package ru.voronezh.autoservice.service;

import ru.voronezh.autoservice.model.Repair;
import java.util.List;

public interface RepairService {
    void addRepair(Repair repair);
    List<Repair> getAllRepairs();
}