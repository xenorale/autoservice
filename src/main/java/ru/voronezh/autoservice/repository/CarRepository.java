package ru.voronezh.autoservice.repository;

import ru.voronezh.autoservice.model.Car;
import java.util.List;

public interface CarRepository {
    List<Car> findAll();
}