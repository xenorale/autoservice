package ru.voronezh.autoservice.repository;

import ru.voronezh.autoservice.model.Repair;
import java.util.List;

public interface RepairRepository {
    void create(Repair repair);
    void delete(int id);
    List<Repair> findAll();
}