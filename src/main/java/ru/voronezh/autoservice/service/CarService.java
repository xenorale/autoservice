package ru.voronezh.autoservice.service;

import ru.voronezh.autoservice.model.Car;
import java.util.List;

public interface CarService {
    List<Car> getAllCars();
}